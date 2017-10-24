package com.multitiers;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.multitiers.service.GameService;
import com.multitiers.service.SubscriptionService;

@SpringBootApplication
public class ProjetMultitiersApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjetMultitiersApplication.class, args);
	}
	
	@Bean
    public CommandLineRunner peuplement(SubscriptionService inscriptionService, GameService gameService) {
        return (args) -> {
        	inscriptionService.initDataLists();
            inscriptionService.bootStrapTwoUsersAndTestCardSet();
            gameService.initDataLists();
        };
    }
}
