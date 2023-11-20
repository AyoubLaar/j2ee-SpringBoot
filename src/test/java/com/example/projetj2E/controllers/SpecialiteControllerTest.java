package com.example.projetj2E.controllers;

import com.example.projetj2E.entites.Specialite;
import com.example.projetj2E.services.SpecialiteService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@WebMvcTest(SpecialiteController.class)
class SpecialiteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SpecialiteService specialiteService;

    @Test
    void getAllSpecialites() throws Exception {
        List<String> specialites = List.of("chirurgien", "oncologue");

        Mockito.when(specialiteService.getAllSpecialites()).thenReturn(specialites);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/specialites")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("[\"chirurgien\", \"oncologue\"]"));
    }
}