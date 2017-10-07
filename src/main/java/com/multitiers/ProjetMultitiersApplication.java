package com.multitiers;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.multitiers.service.GameService;
import com.multitiers.service.InscriptionService;

@SpringBootApplication
public class ProjetMultitiersApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjetMultitiersApplication.class, args);
	}
	
	@Bean
    public CommandLineRunner peuplement(InscriptionService inscriptionService, GameService gameService) {
        return (args) -> {
            inscriptionService.peuplement();
        };
    }
}
