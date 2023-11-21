package com.example.projetj2E.services;

import org.springframework.http.ResponseEntity;

public interface VerificationServiceInterface {
    public ResponseEntity<String> verifyConnected(String sessionId);
}
