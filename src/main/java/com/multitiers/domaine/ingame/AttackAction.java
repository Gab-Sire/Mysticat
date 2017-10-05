package com.multitiers.domaine.ingame;

public class AttackAction extends Action{
	
	private Minion minion;
	private PlayableCharacter target;
	
	public AttackAction() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Minion getMinion() {
		return minion;
	}

	public void setMinion(Minion minion) {
		this.minion = minion;
	}

	public PlayableCharacter getTarget() {
		return target;
	}

	public void setTarget(PlayableCharacter target) {
		this.target = target;
	}

	@Override
	void resolve() {
		this.target.setHealth(this.target.getHealth()-this.minion.getPower());
	}
}
