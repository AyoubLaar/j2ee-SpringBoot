package com.example.projetj2E.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RdvToAcceptOrReject {
    private Long rdvId;
    private String statusRdv;
}
