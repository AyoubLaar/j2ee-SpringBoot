package com.example.projetj2E.controllers;

import com.example.projetj2E.services.VerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/verification")
public class VerificationController {

    @Autowired
    private VerificationService verificationService;

    @GetMapping("/verifysession")
    public ResponseEntity<String> verifySession(@RequestHeader("token") String sessionId) {
        return verificationService.verifyConnected(sessionId);
    }
}
