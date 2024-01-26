package com.example.petfinder.service;

import com.example.petfinder.dto.user.response.AuthenticationResponse;
import com.example.petfinder.dto.user.request.SignInRequest;
import com.example.petfinder.dto.user.request.SignUpRequest;
import com.example.petfinder.dto.user.response.MessageResponse;
import com.example.petfinder.model.entity.User;

public interface AuthService {
    MessageResponse registerUser(SignUpRequest signUpRequest);

    AuthenticationResponse signIn(SignInRequest signInRequest);

    AuthenticationResponse confirmEmail(String confirmToken);

    AuthenticationResponse authenticate(User user);

    MessageResponse sendResetPassword(String email);

    AuthenticationResponse resetPassword(String token, String newPassword);

}
