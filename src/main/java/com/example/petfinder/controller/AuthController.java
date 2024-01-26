package com.example.petfinder.controller;

import com.example.petfinder.dto.user.response.AuthenticationResponse;
import com.example.petfinder.dto.user.request.SignInRequest;
import com.example.petfinder.dto.user.request.SignUpRequest;
import com.example.petfinder.dto.user.response.MessageResponse;
import com.example.petfinder.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/sign-up")
    public ResponseEntity<MessageResponse> registerUser(@Valid @RequestBody SignUpRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                authService.registerUser(request)
        );
    }

    @GetMapping("/sign-in")
    public ResponseEntity<AuthenticationResponse> signIn(@RequestBody @Valid SignInRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                authService.signIn(request)
        );
    }

    @GetMapping("/confirm-email/{token}")
    public ResponseEntity<AuthenticationResponse> confirmEmail(@PathVariable("token") String token) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                authService.confirmEmail(token));

    }

    @PostMapping("password/reset")
    public ResponseEntity<MessageResponse> sendResetPasswordEmail(@RequestParam("email") String email) {
        return ResponseEntity.status(HttpStatus.OK).body(
                authService.sendResetPassword(email)
        );
    }

    @PostMapping("password/reset/{token}")
    public ResponseEntity<AuthenticationResponse> resetPasswordEmail(@PathVariable("token") String token,
                                                                     @RequestParam("password") String password) {
        return ResponseEntity.status(HttpStatus.OK).body(
                authService.resetPassword(token, password)
        );
    }
}
