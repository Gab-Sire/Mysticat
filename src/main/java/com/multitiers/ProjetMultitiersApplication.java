package com.multitiers;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.multitiers.service.GameService;
import com.multitiers.service.AuthentificationService;
import com.multitiers.service.CardCreationService;

@SpringBootApplication
public class ProjetMultitiersApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjetMultitiersApplication.class, args);
	}
	
	
	@Bean
    public CommandLineRunner peuplement(AuthentificationService authService, GameService gameService, CardCreationService cardCreationService) {
        return (args) -> {
        	cardCreationService.initBasicCardSet();
        	authService.initDataLists();
            authService.bootStrapUsersAndAdmin();
            gameService.initDataLists();
        };
    }
	
}

