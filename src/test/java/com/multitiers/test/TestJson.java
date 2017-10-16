package com.multitiers.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.multitiers.domaine.entity.UserCredentials;
import com.multitiers.domaine.ingame.AttackAction;
import com.multitiers.domaine.ingame.SummonAction;
import com.multitiers.util.JsonUtils;

public class TestJson {
	String invalidJson;
	
	AttackAction attackAction;	
	String attackActionJson;
	SummonAction summonAction;
	String summonActionJson;
	
	String userCredentialsJson;
	UserCredentials userCredentials;


	@Before
	public void setUp() throws Exception {
		invalidJson = "uiouiasiolasd//";
		
		summonActionJson = "{\"playerIndex:\"\"0\",\"attackingMinionIndex:\"\"0\""
				+ "\"targetIndex:\"\"0\",\"speed:\"\"0\"}";
		attackActionJson ="{\"playerIndex:\"\"0\",\"attackingMinionIndex:\"\"0\""
				+ "\"targetIndex:\"\"0\",\"speed:\"\"0\"}";
		attackAction = new AttackAction();
		attackAction.setAttackingMinionIndex(0);
		attackAction.setPlayerIndex(0);
		attackAction.setSpeed(0);
		attackAction.setTargetIndex(0);
		
		userCredentialsJson = "{\"username\":\"allo\",\"password\":\"myboy\"}";
		userCredentials = new UserCredentials();
		userCredentials.setUsername("allo");
		userCredentials.setPassword("myboy");
	}

	@After
	public void tearDown() throws Exception {
		userCredentialsJson = null;
	}

	@Test
	public void deserializingValidUserCredentialsJsonReturnsAValidUserCredentialsObject() {
		UserCredentials userCredentialsFromJson = JsonUtils.deserializeUserCredentialsFromJson(userCredentialsJson);
		assertTrue(userCredentials.getUsername().equals(userCredentialsFromJson.getUsername())
				&& userCredentials.getPassword().equals(userCredentialsFromJson.getPassword()));
	}

	@Test(expected = JsonSyntaxException.class)
	public void deserializingInvalidUserCredentialsThrowsJsonException() {
		JsonUtils.deserializeUserCredentialsFromJson(invalidJson);
	}
	
	@Test
	public void serializingUserCredentialsAndDeserializingItBackEqualsTheSameObject() {
		String userCredentialsConvertedToJson = new Gson().toJson(userCredentials);
		UserCredentials backFromJson = JsonUtils.deserializeUserCredentialsFromJson(userCredentialsConvertedToJson);
		assertTrue(userCredentials.getUsername().equals(backFromJson.getUsername())
				&& userCredentials.getPassword().equals(backFromJson.getPassword()));
	}
	
	@Test
	public void deserializingValidAttackActionReturnsAValidAttackAction() {
		AttackAction attackActionFromJson;
	}

}
