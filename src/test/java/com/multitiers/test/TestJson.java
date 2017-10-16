package com.multitiers.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.JsonSyntaxException;
import com.multitiers.domaine.entity.UserCredentials;
import com.multitiers.util.JsonUtils;

public class TestJson {


	String invalidJson;
	String userCredentialsJson;
	UserCredentials userCredentials;
	
	@Before
	public void setUp() throws Exception{
		userCredentialsJson= "{username: 'allo', password:'myboy'}";
		invalidJson = "uiouiasiolasd//";
		userCredentials = new UserCredentials();
		userCredentials.setUsername("allo");
		userCredentials.setPassword("myboy");
	}
	
	@After
	public void tearDown() throws Exception{
		userCredentialsJson = null;
	}
	
	
	
	@Test
	public void deserializingValidUserCredentialsJsonReturnsAValidUserCredentialsObject() {
		UserCredentials userCredentialsFromJson = JsonUtils.deserializeUserCredentialsFromJson(userCredentialsJson);
		assertTrue(userCredentials.getUsername().equals(userCredentialsFromJson.getUsername()) 
				&& userCredentials.getPassword().equals(userCredentialsFromJson.getPassword()));
	}
	
	@Test(expected=JsonSyntaxException.class)
	public void deserializingInvalidUserCredentialsThrowsJsonException() {
		UserCredentials userCredentialsFromJson = JsonUtils.deserializeUserCredentialsFromJson(invalidJson);
	}

}
