package com.multitiers.domaine;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

//@Component
//@Scope("session")
@Entity
@Table(name="mys_user_usr")
public class User {
    @Column(name = "usr_id", nullable = false, updatable = false)
    @Id
	protected String id;
	
	@Column(name="usr_username", unique=true)
	protected String username;
	
	@Column(name="usr_password")
	protected String passwordHash;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name="id_user")
	protected Set<Deck> decks;
	
	@Column(name="usr_salt", nullable = false, updatable = false)
	protected String hashedSalt;

	public User(String username, String passwordHash, String hashedSalt) {
		super();
		this.username = username;
		this.hashedSalt = hashedSalt;
		this.passwordHash = passwordHash;
	}
	
	public User() {}
	
	
	public String getHashedSalt() {
		return hashedSalt;
	}

	public void setHashedSalt(String hashedSalt) {
		this.hashedSalt = hashedSalt;
	}

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

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", passwordHash=" + passwordHash + ", decks=" + decks
				+ ", hashedSalt=" + hashedSalt + "]";
	}
	
	
}