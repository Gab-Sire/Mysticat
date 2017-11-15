package com.multitiers.domaine.entity;

public class UserStatus {
	private String userId;
	private String userName;
	private Boolean isConnected;
	
	public UserStatus(User user) {
		this.userId = user.getId();
		this.userName = user.getUsername();
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Boolean getIsConnected() {
		return isConnected;
	}
	public void setIsConnected(Boolean isConnected) {
		this.isConnected = isConnected;
	}
	
}
