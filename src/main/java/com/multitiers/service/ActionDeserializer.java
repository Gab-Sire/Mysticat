package com.multitiers.service;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.multitiers.domaine.ingame.Action;
import com.multitiers.domaine.ingame.AttackAction;
import com.multitiers.domaine.ingame.PlayableCard;

public class ActionDeserializer implements JsonDeserializer<Action>{

	@Override
	public Action deserialize(JsonElement json, Type arg1, JsonDeserializationContext context) throws JsonParseException {
		JsonObject jsonObj = json.getAsJsonObject();
		
		if(jsonObj.has("speed")) {
			int playerIndex = jsonObj.get("playerIndex").getAsInt();
			int speed = jsonObj.get("speed").getAsInt();
			int attackingMinionIndex = jsonObj.get("attackingMinionIndex").getAsInt();
			int targetIndex = jsonObj.get("targetIndex").getAsInt();
			AttackAction attackAction = new AttackAction();
			
			attackAction.setSpeed(speed);
			attackAction.setAttackingMinionIndex(attackingMinionIndex);
			attackAction.setPlayerIndex(playerIndex);
			attackAction.setTargetIndex(targetIndex);
		}
		if(jsonObj.has("fieldCellWhereTheMinionIsBeingSummoned")) {
			int playerIndex = jsonObj.get("playerIndex").getAsInt();
			int fieldCellWhereTheMinionIsBeingSummoned = jsonObj.get("fieldCellWhereTheMinionIsBeingSummoned").getAsInt();
			JsonObject jsonMinionCard = jsonObj.get("minionCard").getAsJsonObject();
			context.deserialize(jsonMinionCard, PlayableCard.class);
		}
		
		return null;
	}

}
