package com.example.projetj2E;

import com.example.projetj2E.entites.Specialite;
import com.example.projetj2E.services.AdminService;
import com.example.projetj2E.services.SpecialiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.util.List;

@SpringBootApplication
public class Projetj2EApplication {
	@Autowired
	private SpecialiteService specialiteService;

	@Autowired
	private AdminService adminService;

	public static void main(String[] args) {

		SpringApplication.run(Projetj2EApplication.class, args);

	}


	@EventListener(ApplicationReadyEvent.class)
	public void save(){
          specialiteService.saveAlltheSpecialities(List.of("Medecin Généraliste","Pédiatre","psychiatre",
				  "Gynécologue" ,"Dermatologue","Chirurgien","Oncologue","Therapeute","Allergologue",
		         "Ophtalmologue","Rhumatologues"));
		  adminService.saveAdmin("hopitalamin@gmail.com","hoptil2023");
	}


}
