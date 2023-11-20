package com.example.projetj2E.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RdvModel {

    private Long medecinId;
    private LocalDate dateRdv;
    private LocalTime heureRdv;
}
