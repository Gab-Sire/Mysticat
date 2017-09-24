package com.multitiers.domaine;

import java.util.List;

import javax.annotation.Generated;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name ="mys_deck_dec")
public class Deck {
	
	@Id
    @Column(name = "dec_id", nullable = false, updatable = false)
	private String deckId;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name="mys_card_deck",
				joinColumns = {@JoinColumn(name="dec_id")},
				inverseJoinColumns = {@JoinColumn(name="car_id")})
	private List<Card> cardList;

	public String getDeckId() {
		return deckId;
	}

	public void setDeckId(String deckId) {
		this.deckId = deckId;
	}

	public List<Card> getCardList() {
		return cardList;
	}

	public void setCardList(List<Card> cardList) {
		this.cardList = cardList;
	}

	@Override
	public String toString() {
		return "Deck [deckId=" + deckId + ", cardList=" + cardList + "]";
	}

}
