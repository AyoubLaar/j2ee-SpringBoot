package com.example.projetj2E.repository;

import com.example.projetj2E.entites.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin,Long> {
    Optional<Admin> findBylogin(String s);
}
