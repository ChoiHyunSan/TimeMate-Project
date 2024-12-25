package com.ll.timemateproject.api.v1.controller;

import com.ll.timemateproject.api.v1.dto.Result;
import com.ll.timemateproject.api.v1.dto.request.login.LoginRequest;
import com.ll.timemateproject.api.v1.dto.response.login.TokenResponse;
import com.ll.timemateproject.config.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public Result<TokenResponse> login(@RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        String token = jwtProvider.createToken(request.getUsername(), "ROLE_USER");
        return Result.success(new TokenResponse(token));
    }
}