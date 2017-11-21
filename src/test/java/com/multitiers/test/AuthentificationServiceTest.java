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

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.multitiers.domaine.entity.Card;
import com.multitiers.domaine.entity.Deck;
import com.multitiers.domaine.entity.HeroPortrait;
import com.multitiers.domaine.entity.User;
import com.multitiers.domaine.entity.UserCredentials;
import com.multitiers.exception.BadCredentialsLoginException;
import com.multitiers.exception.BadPasswordFormatException;
import com.multitiers.exception.BadUsernameFormatException;
import com.multitiers.exception.UserAlreadyConnectedException;
import com.multitiers.repository.CardRepository;
import com.multitiers.repository.UserRepository;
import com.multitiers.service.AuthentificationService;
import com.multitiers.util.ConnectionUtils;
import com.multitiers.util.Constantes;

@RunWith(MockitoJUnitRunner.class)
public class AuthentificationServiceTest {
	
	@Mock
	CardRepository cardRepositoryMock;
	
	@Mock
	UserRepository userRepositoryMock;
	
	@InjectMocks
	AuthentificationService authService;
	
	List<Card> listeCartes;
	User user;
	
	@Before
	public void setUp() {
		
		//construit une liste de cartes vides
		listeCartes = new ArrayList<>();
		for(int i = 0; i < 40; i++) {
			Card carte = null;
			listeCartes.add(carte);
		}
		
		//construit un user avec un salt
		String salt = ConnectionUtils.generateSalt();
		user = new User("Username", ConnectionUtils.hashPassword("Password", salt), salt);
		
		authService.initDataLists();
		when(cardRepositoryMock.findAll()).thenReturn((ArrayList<Card>) listeCartes);
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
	
	
	@Test
	public void testCreateStarterDeck() {
		
		when(cardRepositoryMock.findAll()).thenReturn((ArrayList<Card>) listeCartes);
		
		Deck deck = authService.createStarterDeck(user);
		
		assertThat(deck.getCardList().size()).isEqualTo(Constantes.CONSTRUCTED_DECK_MAX_SIZE);
		assertThat(deck.getName()).isEqualTo("Starter deck : " + user.getUsername());
		verify(cardRepositoryMock, times(1)).findAll();
	}
	
	@Test
	public void testGetUserFromCredentials() {
		
		when(userRepositoryMock.findByUsername(anyString())).thenReturn(null);
		when(userRepositoryMock.findByUsername(user.getUsername())).thenReturn(user);
		
		//cas valide : informations de l'utilisateur
		UserCredentials validCredentials = new UserCredentials();
		validCredentials.setUsername(user.getUsername());	validCredentials.setPassword("Password");
		
		assertThat(authService.getUserFromCredentials(validCredentials)).isEqualTo(user);
		
		//cas invalide : informations que le repository ne trouve pas
		UserCredentials credentials = new UserCredentials();
		credentials.setUsername("popo");	credentials.setPassword("lolol");
		
		assertThatThrownBy(() -> authService.getUserFromCredentials(credentials)).isInstanceOf(BadCredentialsLoginException.class);	
		
		verify(userRepositoryMock, atLeast(2)).findByUsername(anyString());
	}
	
	@Test
	public void testAddUserToConnectedUsers() {
		
		when(userRepositoryMock.findById(user.getId())).thenReturn(user);
		
		authService.addUserToConnectedUsers(user);
		
		//cas valide
		assertThat(authService.getConnectedUsers().get(user.getId())).isNotNull();
		//cas invalide, tente d'ajouter le même utilisateur deux fois
		assertThatThrownBy(() -> authService.addUserToConnectedUsers(user)).isInstanceOf(UserAlreadyConnectedException.class);
		
		verify(userRepositoryMock, atLeast(2)).findById(user.getId());
	}
	
	@Test
	public void testIsThisUserConnected() {
		
		when(userRepositoryMock.findById(user.getId())).thenReturn(user);
		
		User user02 = authService.createUser("Username2", "Password2", HeroPortrait.zorroHero);
		authService.addUserToConnectedUsers(user);
		
		//cas utilisateur ajouté
		assertThat(authService.isThisUserConnected(user)).isTrue();
		assertThat(authService.isThisUserConnected(user.getId())).isTrue();
		
		//cas utilisateur non ajouté
		assertThat(authService.isThisUserConnected(user02)).isFalse();
		assertThat(authService.isThisUserConnected(user02.getId())).isFalse();
		
		verify(userRepositoryMock, times(1)).findById(user.getId());
	}
	
	@Test
	public void testRemoveUserFromConnectedUsers() {
		
		when(userRepositoryMock.findById(user.getId())).thenReturn(user);
		authService.addUserToConnectedUsers(user);
		
		authService.removeUserFromConnectedUsers(user.getId());
		
		assertThat(authService.getConnectedUsers().get(user.getId())).isNull();
		verify(userRepositoryMock, times(1)).findById(user.getId());
	}
}
