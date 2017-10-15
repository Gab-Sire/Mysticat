package com.multitiers.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.multitiers.domaine.entity.UserCredentials;
import com.multitiers.domaine.ingame.Action;
import com.multitiers.domaine.ingame.ActionList;
import com.multitiers.domaine.ingame.Game;
import com.multitiers.domaine.ingame.PlayableCard;

public class JsonUtils {
	
    public static UserCredentials deserializeUserCredentialsFromJson(String json) {
    	GsonBuilder builder = new GsonBuilder();
    	Gson gson = builder.create();
    	UserCredentials userCredentials = gson.fromJson(json, UserCredentials.class);
    	
    	return userCredentials;
    }
	
	public static Game deserializeGameFromJson(String json) {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(PlayableCard.class, new PlayableCardDeserializer()).create();
		Gson gson = gsonBuilder.create();

		Game gameFromJson = gson.fromJson(json, Game.class);

		return gameFromJson;
	}

	
	public static ActionList deserializeActionListFromJson(String json) {
		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.registerTypeAdapter(PlayableCard.class, new PlayableCardDeserializer())
				.registerTypeAdapter(Action.class, new ActionDeserializer()).create();
		ActionList list = gson.fromJson(json, ActionList.class);

		return list;
	}

	public static String deserializeStringFromJson(String json) {
		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.create();

		String username = gson.fromJson(json, String.class);

		return username;

	}
}
