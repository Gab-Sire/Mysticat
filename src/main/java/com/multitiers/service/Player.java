package com.multitiers.service;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.multitiers.util.Constantes;
public class Player {
	private Hero hero;
	private List<PlayableCard> graveyard;
	private Minion[] field;
	private List<PlayableCard> deck;
	private List<PlayableCard> hand;
	private Integer remainingMana;
	private Integer fatigueDamage;
	
	public Player() {
		this.hero = new Hero();
		this.graveyard = new ArrayList<PlayableCard>();
		this.field = new Minion[Constantes.MAX_FIELD_SIZE];
		this.hand = new ArrayList<PlayableCard>();
		this.deck = new ArrayList<PlayableCard>(); 
		this.fatigueDamage = Constantes.STARTING_FATIGUE_DAMAGE;
		this.remainingMana = Constantes.STARTING_MANA; //Probablement assigned par le jeu

		Collections.shuffle(deck);
		for(int i=0; i<Constantes.STARTING_HAND_SIZE; i++) {
			drawCard();
		}
	}
	
	public void drawCard() {
		if(this.deck.isEmpty()) {
			takeFatigueDamage();
			return;
		}
		PlayableCard cardDrawn = deck.get(0);
		deck.remove(0);
		if(hand.size()<Constantes.MAX_HAND_SIZE) {
			hand.add(cardDrawn);
		}
	}
	
	public void takeFatigueDamage() {
		this.hero.health-= fatigueDamage;
		fatigueDamage++;
	}

	public Hero getHero() {
		return hero;
	}

	public void setHero(Hero hero) {
		this.hero = hero;
	}

	public List<PlayableCard> getGraveyard() {
		return graveyard;
	}

	public void setGraveyard(List<PlayableCard> graveyard) {
		this.graveyard = graveyard;
	}

	public Minion[] getField() {
		return field;
	}

	public void setField(Minion[] field) {
		this.field = field;
	}

	public List<PlayableCard> getDeck() {
		return deck;
	}

	public void setDeck(List<PlayableCard> deck) {
		this.deck = deck;
	}

	public List<PlayableCard> getHand() {
		return hand;
	}

	public void setHand(List<PlayableCard> hand) {
		this.hand = hand;
	}

	public Integer getRemainingMana() {
		return remainingMana;
	}

	public void setRemainingMana(Integer remainingMana) {
		this.remainingMana = remainingMana;
	}

	public Integer getFatigueDamage() {
		return fatigueDamage;
	}

	public void setFatigueDamage(Integer fatigueDamage) {
		this.fatigueDamage = fatigueDamage;
	}
	
	
	
}
