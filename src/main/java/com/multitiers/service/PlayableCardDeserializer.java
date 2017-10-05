package com.multitiers.service;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.multitiers.domaine.ingame.Game;
import com.multitiers.domaine.ingame.PlayableCard;
import com.multitiers.domaine.ingame.PlayableMinionCard;

public class PlayableCardDeserializer implements JsonDeserializer<PlayableCard>{

	@Override
	public PlayableCard deserialize(JsonElement json, Type arg1, JsonDeserializationContext arg2)
			throws JsonParseException {
		JsonObject jsonObj = json.getAsJsonObject();
		

		if(jsonObj.has("initialPower")) {
			int power = jsonObj.get("initialPower").getAsInt();
			int manaCost = jsonObj.get("manaCost").getAsInt();
			int health = jsonObj.get("initialHealth").getAsInt();
			int speed = jsonObj.get("initialSpeed").getAsInt();
			String name = jsonObj.get("name").getAsString();
			String description = jsonObj.get("description").getAsString();
			
			PlayableMinionCard minionCard = new PlayableMinionCard();
			
			minionCard.setDescription(description);
			minionCard.setInitialHealth(health);
			minionCard.setInitialPower(power);
			minionCard.setInitialSpeed(speed);
			minionCard.setManaCost(manaCost);
			minionCard.setName(name);
			return minionCard;
		}
			
			
		return null;
	}

}
