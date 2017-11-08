package com.multitiers.util;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.multitiers.domaine.ingame.Action;
import com.multitiers.domaine.ingame.AttackAction;
import com.multitiers.domaine.ingame.SummonAction;
import com.multitiers.domaine.ingame.SurrenderAction;

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
			
			return attackAction;
		}
		else if(jsonObj.has("fieldCellWhereTheMinionIsBeingSummoned")) {
			int playerIndex = jsonObj.get("playerIndex").getAsInt();
			int fieldCellWhereTheMinionIsBeingSummoned = jsonObj.get("fieldCellWhereTheMinionIsBeingSummoned").getAsInt();
			int indexOfCardInHand = jsonObj.get("indexOfCardInHand").getAsInt();
			//JsonObject jsonMinionCard = jsonObj.get("minionCard").getAsJsonObject();
			//MinionCard minionCard = context.deserialize(jsonMinionCard, PlayableCard.class);
			SummonAction summonAction = new SummonAction();
			summonAction.setPlayerIndex(playerIndex);
			summonAction.setFieldCellWhereTheMinionIsBeingSummoned(fieldCellWhereTheMinionIsBeingSummoned);
			summonAction.setIndexOfCardInHand(indexOfCardInHand);
			
			return summonAction;
		}
		else if(jsonObj.has("field"))  {
			int playerIndex = jsonObj.get("playerIndex").getAsInt();
			String field = jsonObj.get("field").getAsString();
			SurrenderAction surrenderAction = new SurrenderAction();
			surrenderAction.setPlayerIndex(playerIndex);
			surrenderAction.setField(field);
			
			return surrenderAction;
		}
		
		return null;
	}

}
