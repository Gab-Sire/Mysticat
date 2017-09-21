package com.multitiers.domaine;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="mys_user_use")
public class User {
	private String id;
	private String username;
	private String passwordHash;
	private Set<Deck> decks;
}
