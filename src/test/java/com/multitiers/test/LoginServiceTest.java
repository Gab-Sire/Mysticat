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
import com.multitiers.service.LoginService;
import com.multitiers.util.ConnectionUtils;
import com.multitiers.util.Constantes;

@RunWith(MockitoJUnitRunner.class)
public class LoginServiceTest {
	
	@Mock
	CardRepository cardRepositoryMock;
	
	@Mock
	UserRepository userRepositoryMock;
	
	@InjectMocks
	LoginService loginService;
	
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
		
		//initie la liste des joueurs connectés
		loginService.initDataLists();
		
		//setup des comportements des mocks pour les situations générales
		when(cardRepositoryMock.findAll()).thenReturn((ArrayList<Card>) listeCartes);
		when(userRepositoryMock.findById(user.getId())).thenReturn(user);
	}
	
	@After
	public void tearDown() {
		listeCartes = null;
		user = null;
	}
	
	@Test
	public void testCreateUser() {
		
		User createdUser01 = loginService.createUser("TestChat", "Power1", HeroPortrait.warriorHero);
		
		//cas valide pour créer un utilisateur
		assertThat(createdUser01).isNotNull();
		
		//cas invalide: mot de passe ne contient pas de chiffre
		assertThatThrownBy(() -> loginService.createUser("TestChat2", "Power", HeroPortrait.warriorHero)).isInstanceOf(BadPasswordFormatException.class);
		
		//cas invalide: nom d'usager n'a pas la longueur appropriée (5 à 30)
		assertThatThrownBy(() -> loginService.createUser("Fail", "Power1", HeroPortrait.warriorHero)).isInstanceOf(BadUsernameFormatException.class);
		
		verify(cardRepositoryMock, times(1)).findAll();
	}
	
	@Test
	public void testAssignStarterDeck() {
		
		loginService.assignStarterDeck(user);
		
		assertThat(user.getDecks().size()).isEqualTo(1);
		verify(cardRepositoryMock, times(1)).findAll();
	}
	
	
	@Test
	public void testCreateStarterDeck() {
		
		Deck deck = loginService.createStarterDeck(user);
		
		assertThat(deck.getCardList().size()).isEqualTo(Constantes.CONSTRUCTED_DECK_MAX_SIZE);
		assertThat(deck.getName()).isEqualTo("Starter deck : " + user.getUsername());
		verify(cardRepositoryMock, times(1)).findAll();
	}
	
	@Test
	public void testGetUserFromCredentials() {
		
		when(userRepositoryMock.findByUsername(anyString())).thenReturn(null);
		when(userRepositoryMock.findByUsername(user.getUsername())).thenReturn(user);
		
		//cas valide : informations de l'utilisateur dans le repository
		UserCredentials validCredentials = new UserCredentials();
		validCredentials.setUsername(user.getUsername());	validCredentials.setPassword("Password");
		
		assertThat(loginService.getUserFromCredentials(validCredentials)).isEqualTo(user);
		
		//cas invalide : informations que le repository ne trouve pas
		UserCredentials credentials = new UserCredentials();
		credentials.setUsername("popo");	credentials.setPassword("lolol");
		
		assertThatThrownBy(() -> loginService.getUserFromCredentials(credentials)).isInstanceOf(BadCredentialsLoginException.class);	
		
		verify(userRepositoryMock, atLeast(2)).findByUsername(anyString());
	}
	
	@Test
	public void testAddUserToConnectedUsers() {
		
		loginService.addUserToConnectedUsers(user);
		
		//cas valide
		assertThat(loginService.getConnectedUsers().get(user.getId())).isNotNull();
		//cas invalide, tente d'ajouter le même utilisateur deux fois
		assertThatThrownBy(() -> loginService.addUserToConnectedUsers(user)).isInstanceOf(UserAlreadyConnectedException.class);
		
		verify(userRepositoryMock, atLeast(2)).findById(user.getId());
	}
	
	@Test
	public void testIsThisUserConnected() {
		
		User user02 = loginService.createUser("Username2", "Password2", HeroPortrait.zorroHero);
		loginService.addUserToConnectedUsers(user);
		
		//cas utilisateur ajouté
		assertThat(loginService.isThisUserConnected(user)).isTrue();
		assertThat(loginService.isThisUserConnected(user.getId())).isTrue();
		
		//cas utilisateur non ajouté
		assertThat(loginService.isThisUserConnected(user02)).isFalse();
		assertThat(loginService.isThisUserConnected(user02.getId())).isFalse();
		
		verify(userRepositoryMock, times(1)).findById(user.getId());
	}
	
	@Test
	public void testRemoveUserFromConnectedUsers() {
		
		loginService.addUserToConnectedUsers(user);
		
		loginService.removeUserFromConnectedUsers(user.getId());
		
		//cas utilisateur enlevé
		assertThat(loginService.getConnectedUsers().get(user.getId())).isNull();
		verify(userRepositoryMock, times(1)).findById(user.getId());
	}
}
