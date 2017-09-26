package com.multitiers.service;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler;

import java.util.ArrayList;
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
		this.deck = new ArrayList<PlayableCard>(); //Doit etre remplit
		this.remainingMana = Constantes.STARTING_MANA; //Probablement assigned par le jeu
		this.fatigueDamage = Constantes.STARTING_FATIGUE_DAMAGE;
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
}
