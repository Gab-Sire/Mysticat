package com.multitiers.domaine.ingame;

public class SurrenderAction extends Action{
	private static final int PLAYER_ONE_INDEX = 0;
	private static final int PLAYER_TWO_INDEX = 1;
	
	private String field;
	
	public SurrenderAction() {
		super();
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	@Override
	public void resolve(Game game) {
		game.setEndedWithSurrender(true);
		Integer playerDeclaringSurrenderIndex = getPlayerIndex();
		Integer opponentPlayerIndex = (playerDeclaringSurrenderIndex == PLAYER_ONE_INDEX) ? PLAYER_TWO_INDEX
				: PLAYER_ONE_INDEX;
		
		if(game.getWinnerPlayerIndex()==playerDeclaringSurrenderIndex) {
			game.setWinnerPlayerIndex(-1);
		}
		else {
			game.setWinnerPlayerIndex(opponentPlayerIndex);
		}
		
	}


}
