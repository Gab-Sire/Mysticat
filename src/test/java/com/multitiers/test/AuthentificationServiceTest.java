package com.multitiers.test;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.multitiers.domaine.entity.Card;
import com.multitiers.domaine.entity.HeroPortrait;
import com.multitiers.domaine.entity.User;
import com.multitiers.exception.BadPasswordFormatException;
import com.multitiers.exception.BadUsernameFormatException;
import com.multitiers.repository.CardRepository;
import com.multitiers.service.AuthentificationService;

@RunWith(MockitoJUnitRunner.class)
public class AuthentificationServiceTest {
	
	@Mock
	CardRepository cardRepositoryMock;
	
	CardRepository cardRepository;
	
	@Mock
	User userMock;
	
	@InjectMocks
	AuthentificationService authService;
	
	List<Card> listeCartes;
	User user;
	
	@Before
	public void setUp() {
		listeCartes = new ArrayList<>();
		for(int i = 0; i < 40; i++) {
			Card carte = null;
			listeCartes.add(carte);
		}
		
		user = new User("Username", "PasswordHash", "HashedSalt");
	}
	
	@After
	public void tearDown() {
		listeCartes = null;
		user = null;
	}
	
	@Test
	public void testCreateUser() {
		
		when(cardRepositoryMock.findAll()).thenReturn((ArrayList<Card>) listeCartes);
		
		User createdUser01 = authService.createUser("TestChat", "Power1", HeroPortrait.warriorHero);
		
		//cas valide pour créer un utilisateur
		assertThat(createdUser01).isNotNull();
		
		//cas invalide: mot de passe ne contient pas de chiffre
		assertThatThrownBy(() -> authService.createUser("TestChat2", "Power", HeroPortrait.warriorHero)).isInstanceOf(BadPasswordFormatException.class);
		
		//cas invalide: nom d'usager n'a pas la longueur appropriée (5 à 30)
		assertThatThrownBy(() -> authService.createUser("Fail", "Power1", HeroPortrait.warriorHero)).isInstanceOf(BadUsernameFormatException.class);
		
		verify(cardRepositoryMock, times(1)).findAll();
	}
	
	@Test
	public void testAssignStarterDeck() {
		
		when(cardRepositoryMock.findAll()).thenReturn((ArrayList<Card>) listeCartes);
		
		authService.assignStarterDeck(user);
		
		assertThat(user.getDecks().size()).isEqualTo(1);
		verify(cardRepositoryMock, times(1)).findAll();
	}
	

}
