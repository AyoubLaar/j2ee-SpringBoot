package com.example.projetj2E.controllers;

import com.example.projetj2E.entites.Specialite;
import com.example.projetj2E.repository.SpecialiteRepository;
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

    @BeforeEach
    void setUp() {
        // Ajouter des données de test dans la base de données
        Specialite specialite1 = Specialite.builder()
                .nomDuSpecialite("chirurgien")
                .build();
        Specialite specialite2 = Specialite.builder()
                .nomDuSpecialite("oncologue")
                .build();
        specialiteRepository.saveAll(List.of(specialite1, specialite2));
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
        assertEquals(2, specialites.size());
        assertTrue(specialites.contains("chirurgien"));
        assertTrue(specialites.contains("oncologue"));
    }
}
