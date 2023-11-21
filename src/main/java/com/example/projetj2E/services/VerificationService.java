package com.example.projetj2E.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class VerificationService implements VerificationServiceInterface {

    @Autowired
    private AuthentificationService authentificationService;

    @Override
    public ResponseEntity<String> verifyConnected(String sessionId) {
        String role = authentificationService.getRole(sessionId);
        if(role.equals("UNAUTHORIZED")) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(role);
        return ResponseEntity.ok(role);
    }
}
