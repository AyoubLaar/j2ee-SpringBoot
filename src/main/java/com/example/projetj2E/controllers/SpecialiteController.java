package com.example.projetj2E.controllers;


import com.example.projetj2E.services.SpecialiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class SpecialiteController {

         @Autowired
         private SpecialiteService specialiteService;

         @GetMapping("/specialites")
         public List<String> getAllSpecialites(){
             List<String> specialites=specialiteService.getAllSpecialites();
             return specialites;
         }
}
