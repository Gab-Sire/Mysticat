package com.multitiers.domaine;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="mys_user_usr")
public class User {
	
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "usr_id", nullable = false, updatable = false)
    @Id
	protected String id;
	
	@Column(name="usr_username")
	protected String username;
	
	@Column(name="usr_password")
	protected String passwordHash;
	
	@OneToMany(mappedBy = "user")
	protected Set<Deck> decks;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPasswordHash() {
		return passwordHash;
	}
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
	public Set<Deck> getDecks() {
		return decks;
	}
	public void setDecks(Set<Deck> decks) {
		this.decks = decks;
	}
}
