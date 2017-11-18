package com.multitiers.domaine.ingame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.multitiers.domaine.entity.Card;
import com.multitiers.domaine.entity.HeroPortrait;
import com.multitiers.domaine.entity.MinionCard;
import com.multitiers.domaine.entity.User;
import com.multitiers.util.Constantes;
public class Player {
	private String playerId;
	private String name;
	private Hero hero;
	private List<PlayableCard> graveyard;
	private Minion[] field;
	private List<PlayableCard> deck;
	private List<PlayableCard> hand;
	private Integer remainingMana;
	private Integer fatigueDamage;
	private HeroPortrait heroPortrait;

	public Player() {
		super();
	}

	public Player(User user) {
		this.playerId = user.getId();
		this.name = user.getUsername();
		convertUserDeckToPlayerDeck(user);
		this.hero = new Hero();
		this.graveyard = new ArrayList<PlayableCard>();
		this.field = new Minion[Constantes.MAX_FIELD_SIZE];
		this.hand = new ArrayList<PlayableCard>();
		this.fatigueDamage = Constantes.STARTING_FATIGUE_DAMAGE;
		this.remainingMana = Constantes.STARTING_MANA;
		this.heroPortrait = user.getHeroPortrait();

		Collections.shuffle(deck);
		drawStartingHand();
	}

	private void convertUserDeckToPlayerDeck(User user) {
		this.deck = new ArrayList<PlayableCard>();
		List<Card> entityCardList = user.getDecks().get(user.getFavoriteDeck()).getCardList();
		for(int i=0; i<entityCardList.size(); i++) {
			this.deck.add(new PlayableMinionCard((MinionCard) entityCardList.get(i)));
		}
	}

	private void drawStartingHand() {
		for(int i=0; i<Constantes.STARTING_HAND_SIZE; i++) {
			drawCard();
		}
	}
	
	public void drawCard() {
		if(this.deck.isEmpty()) {
			takeFatigueDamage();
		}
		else {		
			PlayableCard cardDrawn = deck.get(0);
			deck.remove(0);
			if(hand.size()<Constantes.MAX_HAND_SIZE) {
				hand.add(cardDrawn);
			}
		}
	}
	
	public void takeFatigueDamage() {
		this.hero.health-= fatigueDamage;
		fatigueDamage++;
	}

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getPlayerId() {
		return playerId;
	}

	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}
	
	public void addMinion(Minion minion, int index) {
		this.field[index] = minion;
	}
	
	public void sendCardToGraveyard(PlayableCard card) {
		this.graveyard.add(card);
	}
	
	public void removeCardFromHand(int indexInHand) {
		this.hand.remove(indexInHand);
	}

	public HeroPortrait getHeroPortrait() {
		return heroPortrait;
	}

	public void setHeroPortrait(HeroPortrait heroPortrait) {
		this.heroPortrait = heroPortrait;
	}
}
