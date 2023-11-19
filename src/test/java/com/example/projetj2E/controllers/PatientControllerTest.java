package com.example.projetj2E.controllers;

import com.example.projetj2E.entites.Patient;
import com.example.projetj2E.entites.Sexe;
import com.example.projetj2E.models.PatientModel;
import com.example.projetj2E.services.PatientServices;
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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(PatientController.class)
class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private PatientServices patientServices;


    private Patient patient;

    @BeforeEach
    void setUp() {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            patient=Patient.builder()
                    .patientId(1L)
                    .patientLogin("cissepapewaly@gmail.com")
                    .nom("cisse")
                    .prenom("pape waly")
                    .dateDeNaissance(LocalDate.parse("23/08/2005",formatter))
                    .sexe(Sexe.valueOf("Homme"))
                    .telephone("0505084899")
                    .password("cissepape")
                    .build();
    }

    @SneakyThrows
    @Test
    void savePatient() {
      PatientModel  patientModelinput =PatientModel.builder()
                .patientLogin("cissepapewaly@gmail.com")
                .nom("cisse")
                .prenom("pape waly")
                .dateDeNaissance("23/08/2005")
                .sexe(Sexe.Homme
                )
                .telephone("0505084899")
                .password("cissepape")
                .confirmedPassword("cissepape")
                .build();
                Mockito.when(patientServices.savePatient(patientModelinput)).thenReturn(patient);

                mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/patients").
                        contentType(MediaType.APPLICATION_JSON).content("{\n" +
                                "    \"patientLogin\":\"cissepapewaly@gmail.com\",\n" +
                                "    \"password\":\"cissepape\",\n" +
                                "    \"confirmedPassword\":\"cissepape\",\n" +
                                "    \"nom\":\"cisse\",\n" +
                                "    \"prenom\":\"pape waly\",\n" +
                                "    \"dateDeNaissance\":\"23/08/2005\",\n" +
                                "    \"telephone\":\"0505084899\",\n" +
                                "    \"sexe\":\"Homme\"\n" +
                                "\n" +
                                "}")).andExpect(MockMvcResultMatchers.status().isOk());


    }
}