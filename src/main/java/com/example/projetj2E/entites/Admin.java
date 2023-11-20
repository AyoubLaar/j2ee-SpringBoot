package com.example.projetj2E.entites;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Admin {

    @Id
    @SequenceGenerator(
            name = "admin_login",
            sequenceName = "admin_login",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "admin_login")
    private String login;
    @Column(
            nullable = false
    )
    private String password;

    private String sessionId;
}
