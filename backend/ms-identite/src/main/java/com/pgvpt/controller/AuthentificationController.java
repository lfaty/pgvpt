package com.pgvpt.controller;

import com.pgvpt.api.*;
import com.pgvpt.dto.AuthResponse;
import com.pgvpt.dto.LoginRequest;
import com.pgvpt.dto.RefreshTokenRequest;
import com.pgvpt.dto.RegisterRequest;
import org.springframework.http.ResponseEntity;

public class AuthentificationController implements AuthentificationApi{

    @Override
    public ResponseEntity<AuthResponse> login(LoginRequest loginRequest) {
        return null;
    }

    @Override
    public ResponseEntity<Void> logout() {
        return null;
    }

    @Override
    public ResponseEntity<AuthResponse> refreshToken(RefreshTokenRequest refreshTokenRequest) {
        return null;
    }

    @Override
    public ResponseEntity<AuthResponse> register(RegisterRequest registerRequest) {
        return null;
    }
}
