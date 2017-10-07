package com.multitiers.service;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.multitiers.domaine.ingame.Action;

public class ActionDeserializer implements JsonDeserializer<Action>{

	@Override
	public Action deserialize(JsonElement json, Type arg1, JsonDeserializationContext arg2) throws JsonParseException {
		JsonObject jsonObj = json.getAsJsonObject();
		
		if(jsonObj.has("speed")) {
			int speed = jsonObj.get("speed").getAsInt();
			int attackingMinionIndex = jsonObj.get("attackingMinionIndex").getAsInt();
			int targetIndex = jsonObj.get("targetIndex").getAsInt();
			int playerIndex = jsonObj.get("playerIndex").getAsInt();
		}
		
		return null;
	}

}
