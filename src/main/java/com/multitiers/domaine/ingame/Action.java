package com.multitiers.domaine.ingame;

public abstract class Action implements Comparable<Action>{
	protected Integer playerIndex;
	
	public abstract Integer getPlayerIndex();
	public abstract void setPlayerIndex(Integer playerIndex);
	
	@Override
	public int compareTo(Action a2) {
		if(this instanceof SummonAction) {
			if(a2 instanceof SummonAction) {
				return 0;
			}
			else {
				return -1;
			}
		}
		else if(this instanceof AttackAction) {
			if(a2 instanceof SummonAction) {
				return 1;
			}
			else if(a2 instanceof AttackAction) {
				if(((AttackAction)this).getSpeed() > ((AttackAction)a2).getSpeed()) {
					return -1;
				}
				else if(((AttackAction)this).getSpeed() < ((AttackAction)a2).getSpeed()) {
					return 1;
				}
				else {
					return 0;
				}
			}
		}
		return 0;
	}
	
}
