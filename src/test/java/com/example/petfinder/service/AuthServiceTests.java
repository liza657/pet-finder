package com.example.petfinder.service;

import com.example.petfinder.dto.user.request.SignInRequest;
import com.example.petfinder.dto.user.request.SignUpRequest;
import com.example.petfinder.dto.user.response.AuthenticationResponse;
import com.example.petfinder.dto.user.response.MessageResponse;
import com.example.petfinder.model.entity.User;
import com.example.petfinder.model.enums.Sex;
import com.example.petfinder.service.impl.AuthServiceImpl;
import com.example.petfinder.token.AccessTokenRepository;
import com.example.petfinder.token.ConfirmationTokenRepository;
import com.example.petfinder.token.ResetPasswordTokenRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTests {

    @Mock
    private UserService userService;
    @Mock
    private EmailService emailService;
    @Mock
    private JwtService jwtService;
    @Mock
    private ConfirmationTokenRepository confirmationTokenRepository;
    @Mock
    private AccessTokenRepository accessTokenRepository;
    @Mock
    private ResetPasswordTokenRepository resetPasswordTokenRepository;
    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private PasswordEncoder passwordEncoder;


    @InjectMocks
    private AuthServiceImpl authService;


    @Test
    public void AuthService_SignUp_ReturnUser() {
        SignUpRequest signUpRequest = createSignUpRequest();

        MessageResponse created = authService.registerUser(signUpRequest);

        Assertions.assertThat(created.username()).isSameAs(signUpRequest.email());

    }

    @Test
    public void AuthService_SignIn_ReturnUser() {
        SignInRequest signInRequest = createSignInRequest();

        User user = new User();

        when(userService.getByUsername(signInRequest.email())).thenReturn(user);
        when(jwtService.generateToken(any())).thenReturn("token");

        AuthenticationResponse response = authService.signIn(signInRequest);

        Assertions.assertThat(response.token()).isNotNull();
        assertEquals("token", response.token());
    }

    private SignUpRequest createSignUpRequest() {
        return SignUpRequest.builder()
                .email("elizabeth@gmail.com")
                .firstName("liza")
                .lastName("dzhuha")
                .phoneNumber("0951110428")
                .biography("fef")
                .sex(Sex.MALE)
                .password(passwordEncoder.encode("Liza567)"))
                .build();

    }

    private SignInRequest createSignInRequest() {
        return SignInRequest.builder()
                .email("kyrylo@gmail.com")
                .password("Liza))23")
                .build();

    }


}
