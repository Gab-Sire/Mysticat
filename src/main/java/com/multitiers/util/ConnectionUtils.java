package com.multitiers.util;

import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;

public class ConnectionUtils {
	public static final String SALT = "sha1myboy";
	
	public static String hashPassword(String password){
		return DigestUtils.sha1Hex(password+SALT);
	}
	
	public static UUID generateUUID() {
		return UUID.randomUUID();
	}
}
