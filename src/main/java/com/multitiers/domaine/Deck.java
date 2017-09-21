package com.multitiers.domaine;

import java.util.List;

import javax.annotation.Generated;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name ="mys_deck")
public class Deck {
	@Id
	private String deckId;
	
	@OneToMany(mappedBy = "deck")
	private List<Card> cardList;
	
	@ManyToOne
	private User owner;
}
