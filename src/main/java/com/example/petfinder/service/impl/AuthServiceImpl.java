package com.example.petfinder.service.impl;

import com.example.petfinder.dto.user.request.SignInRequest;
import com.example.petfinder.dto.user.request.SignUpRequest;
import com.example.petfinder.dto.user.response.AuthenticationResponse;
import com.example.petfinder.dto.user.response.MessageResponse;
import com.example.petfinder.exceptions.EntityNotExistsException;
import com.example.petfinder.exceptions.InvalidTokenException;
import com.example.petfinder.model.entity.User;
import com.example.petfinder.model.enums.Role;
import com.example.petfinder.repository.UserRepository;
import com.example.petfinder.service.AuthService;
import com.example.petfinder.service.EmailService;
import com.example.petfinder.service.JwtService;
import com.example.petfinder.service.UserService;
import com.example.petfinder.token.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    //    @Value(value = "${jwt.confirm_expiration}")
//    private long confirmTokenExpiration;
//
//    @Value(value = "${jwt.access_expiration}")
//    private long accessTokenExpiration;
//    @Value(value = "${jwt.reset_password_expiration}")
//    private long resetPasswordTokenExpiration;
    private final JwtService jwtService;
    private final UserService userService;
    private final EmailService emailService;
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final AccessTokenRepository accessTokenRepository;
    private final ResetPasswordTokenRepository resetPasswordTokenRepository;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    @Override
    public MessageResponse registerUser(SignUpRequest signUpRequest) {
        return register(signUpRequest);
    }

    private MessageResponse register(SignUpRequest signUpRequest) {
        var user = User.builder()
                .firstName(signUpRequest.firstName())
                .lastName(signUpRequest.lastName())
                .email(signUpRequest.email())
                .phoneNumber(signUpRequest.phoneNumber())
                .biography(signUpRequest.biography())
                .birtDate(signUpRequest.birthDate())
                .sex(signUpRequest.sex())
                .password(passwordEncoder.encode(signUpRequest.password()))
                .role(Role.USER)
                .isLocked(false)
                .isEnabled(false)
                .isExpired(false)
                .isCredentialsExpired(false)
                .build();

        userService.createUser(user);

        String confirmationToken = jwtService.generateToken(user);

        saveUserConfirmationToken(user, confirmationToken);

        sendEmail(user.getEmail(), confirmationToken);

        System.out.println("confirmation token" + confirmationToken);
//        return user;

        return new MessageResponse("Please check your email for confirmation.", user.getUsername());

    }

    @Override
    @Transactional
    public AuthenticationResponse signIn(SignInRequest signInRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                signInRequest.email(),
                signInRequest.password()
        ));

        var user = userService
                .getByUsername(signInRequest.email());
        revokeAllAccessToken(user);
        var jwt = jwtService.generateToken(user);
        saveUserAccessToken(user, jwt);
        return new AuthenticationResponse(jwt);
    }

    @Override
    public AuthenticationResponse confirmEmail(String confirmToken) {
        ConfirmationToken token = confirmationTokenRepository.findByToken(confirmToken);
        User user = userRepository.findUserByEmail(token.getUser().getEmail())
                .orElseThrow(() -> new EntityNotExistsException("User not found"));
        revokeAllConfirmationToken(user);
        user.setIsEnabled(true);
        return authenticate(user);
    }

    @Transactional
    @Override
    public AuthenticationResponse authenticate(User user) {
        String accessToken = jwtService.generateToken(user);
        saveUserAccessToken(user, accessToken);
        return new AuthenticationResponse(accessToken);
    }

    @Override
    public MessageResponse sendResetPassword(String email) {
        var user = userService.getByUsername(email);
        var jwt = jwtService.generateToken(user);
        saveResetPasswordToken(user, jwt);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("Complete registration!");
        mailMessage.setText("To confirm your account, please click here : "
                + "localhost:8080/auth/password/reset" + jwt);
        emailService.sendEmail(mailMessage);
        System.out.println(jwt);
        return new MessageResponse("Please check your email for reset password.", user.getUsername());
    }

    @Override
    public AuthenticationResponse resetPassword(String token, String newPassword) {
        ResetPasswordToken resetPasswordToken = resetPasswordTokenRepository.findByToken(token)
                .orElseThrow(() -> new EntityNotExistsException("Token not found"));

        if (resetPasswordToken.getIsExpired() || resetPasswordToken.getIsRevoked()) {
            throw new InvalidTokenException("Reset password token is expired or revoked");
        }
        User user = resetPasswordToken.getUser();
        updatePassword(user, newPassword);
        revokeAllResetPasswordToken(user);
        return signIn(new SignInRequest(user.getUsername(), newPassword));
    }

    private void updatePassword(UserDetails userDetails, String newPassword) {
        User user = userService.getByUsername(userDetails.getUsername());
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    private void sendEmail(String email, String confirmationToken) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("Complete registration!");
        mailMessage.setText("To confirm your account, please click here : "
                + "localhost:8080/auth/confirm-account/" + confirmationToken);
        emailService.sendEmail(mailMessage);
    }

    private void saveUserConfirmationToken(User user, String token) {
        ConfirmationToken confirmationToken = new ConfirmationToken(token, false, false, user);
        confirmationTokenRepository.save(confirmationToken);
    }

    private void saveUserAccessToken(User user, String token) {
        AccessToken accessToken = new AccessToken(token, false, false, user);
        accessTokenRepository.save(accessToken);
    }

    private void saveResetPasswordToken(User user, String token) {
        ResetPasswordToken resetPasswordToken = new ResetPasswordToken(token, false, false, user);
        resetPasswordTokenRepository.save(resetPasswordToken);
    }


    private void revokeAllAccessToken(User user) {
        var validUserTokens = accessTokenRepository.findAllValidTokensForUser(user.getEmail());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setIsRevoked(true);
            token.setIsExpired(true);
        });
        accessTokenRepository.saveAll(validUserTokens);
    }

    private void revokeAllConfirmationToken(User user) {
        var validUserTokens = confirmationTokenRepository.findAllValidTokensForUser(user.getEmail());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setIsRevoked(true);
            token.setIsExpired(true);
        });
        confirmationTokenRepository.saveAll(validUserTokens);
    }

    private void revokeAllResetPasswordToken(User user) {
        var validUserTokens = resetPasswordTokenRepository.findAllValidTokensForUser(user.getEmail());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setIsRevoked(true);
            token.setIsExpired(true);
        });
        resetPasswordTokenRepository.saveAll(validUserTokens);
    }


}
