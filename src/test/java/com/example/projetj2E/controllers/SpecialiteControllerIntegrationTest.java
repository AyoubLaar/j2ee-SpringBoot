package com.example.projetj2E.controllers;

import com.example.projetj2E.entites.Specialite;
import com.example.projetj2E.repository.SpecialiteRepository;
import com.example.projetj2E.services.SpecialiteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SpecialiteControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private SpecialiteRepository specialiteRepository;

    @Autowired
    private SpecialiteService specialiteService;

    @BeforeEach
    void setUp() {
        // Ajouter des données de test dans la base de données
        specialiteService.saveAlltheSpecialities(List.of("Medecin Généraliste", "Pédiatre", "psychiatre",
                "Gynécologue", "Dermatologue", "Chirurgien", "Oncologue", "Therapeute", "Allergologue",
                "Ophtalmologue", "Rhumatologues"));
    }

    @Test
    void getAllSpecialites() {
        ResponseEntity<List<String>> response = restTemplate.exchange(
                "/api/v1/specialites",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );

        // Vérifier le statut de la réponse
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Vérifier les données retournées
        List<String> specialites = response.getBody();
        assertNotNull(specialites);
        assertEquals(11, specialites.size()); // Vérifier le nombre total de spécialités ajoutées
        assertTrue(specialites.contains("Medecin Généraliste"));
        assertTrue(specialites.contains("Chirurgien"));
        // Assurez-vous que toutes les spécialités ajoutées sont présentes dans la réponse
        assertTrue(specialites.containsAll(List.of("Pédiatre", "psychiatre", "Gynécologue", "Dermatologue",
                "Oncologue", "Therapeute", "Allergologue", "Ophtalmologue", "Rhumatologues")));
    }
}