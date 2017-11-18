package com.multitiers.domaine.entity;

import java.util.List;

public class UserDeck {
	private List<String> cardIds;
	private String userId;
	private Integer deckIndex;
	private String deckName;
	private Boolean isNewFavorite;
	
	public String getDeckName() {
		return deckName;
	}
	public void setDeckName(String deckName) {
		this.deckName = deckName;
	}
	public List<String> getCardIds() {
		return cardIds;
	}
	public void setCardIds(List<String> cardIds) {
		this.cardIds = cardIds;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Integer getDeckIndex() {
		return deckIndex;
	}
	public void setDeckIndex(Integer deckIndex) {
		this.deckIndex = deckIndex;
	}
	public Boolean getIsNewFavorite() {
		return isNewFavorite;
	}
	public void setIsNewFavorite(Boolean isNewFavorite) {
		this.isNewFavorite = isNewFavorite;
	}
	
}
