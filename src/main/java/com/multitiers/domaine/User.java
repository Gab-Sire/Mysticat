package com.multitiers.domaine;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.multitiers.util.ConnectionUtils;

@Entity
@Table(name="mys_user_usr")
public class User {

	public User(String username, String passwordHash) {
		super();
		this.username = username;
		this.passwordHash = passwordHash;
		this.decks = new HashSet<Deck>();
		this.id = ConnectionUtils.generateUUID().toString();
	}
	
	public User() {}
	
    @Column(name = "usr_id", nullable = false, updatable = false)
    @Id
	protected String id;
	
	@Column(name="usr_username", unique=true)
	protected String username;
	
	@Column(name="usr_password")
	protected String passwordHash;
	
	@OneToMany
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
