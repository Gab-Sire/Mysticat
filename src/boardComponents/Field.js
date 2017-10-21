import React, {Component} from 'react';
import Minion from '../cardComponents/Minion.js';

export default class Field extends Component{

	constructor(props){
		super(props);
		this.state={
			selectedMinionIndex : null,
			isAttacking: null,
			attackClass: ""
		}
	}

	render(){
		const props = (this.props.players[this.props.playerIndex].field);
		let fieldMinions = this.props.players[this.props.playerIndex].field.map(function(minion, index){
		    return (
		     <Minion
		       	key={"fieldMinion" + index}
		     	active = {true === this.props.belongsToSelf ? this.props.cellsOfSummonedMinionsThisTurn[index] : false}
		     	activeAtacker = {true === this.props.belongsToSelf ? this.props.cellsOfAttackingMinion[index] : false}
		     	attackerSelected = {false === this.props.belongsToSelf ? this.props.attackerSelected!==null : false}

		     onClick={() => this.handleSelectMinion(index, this.props.playerIndex)} {...minion}{...props}/>
		    )
		   }, this)
		return <div> {fieldMinions} </div>;
	}

	assignFieldCellToSummon = (index) => {
		this.props.cellsOfSummonedMinionsThisTurn[index] = true;
	}


	isThisFieldCellEmpty = (index) => { 
		return (null === this.props.self.field[index]); 
	} 
	  
	assignAttaque = (index) => {
		this.props.cellsOfAttackingMinion[index] = true;
		this.props.changeAttackerSelected(index);
	}

	handleSelectMinion = (index, indexPlayer) => {
		if(true === this.props.belongsToSelf){
			let isEmpty = this.isThisFieldCellEmpty(index); 
			if(true === isEmpty){ 
				this.setState({ selectedMinionIndex: index })
				this.props.callBackSelectedMinion(index);
			}else{
				this.assignAttaque(index);
			}
		}
	}
}
