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

	isThisFieldCellEmpty = (index) => {
		return (null === this.props.self.field[index]);
	}

	isThisFieldCellAssignedToPreviousSummon = (index) => {
			//return this.props.cellsOfSummonedMinionsThisTurn[index];
	}

	assignFieldCellToSummon = (index) => {
		this.props.cellsOfSummonedMinionsThisTurn[index] = true;
	}

	assignAttaque = (index) => {
		this.props.cellsOfAttaquingMinion[index] = true;
		this.props.changeAttackerSelected();
	}
	
	handleSelectMinion = (index, indexPlayer) => {

		if(true === this.props.belongsToSelf){
			let isEmpty = this.isThisFieldCellEmpty(index);
				let isAssigned = this.isThisFieldCellAssignedToPreviousSummon(index);

			if(true === isEmpty && false === isAssigned){
				this.assignFieldCellToSummon(index);
			}else if(false === isEmpty){
				this.assignAttaque(index);
			}


				this.setState({ selectedMinionIndex: index })
				this.props.callBackSelectedMinion(index);
			}
		
	}

	render(){
		const props = (this.props.players[this.props.playerIndex].field);
		let fieldMinions = this.props.players[this.props.playerIndex].field.map(function(minion, index){
		    return (
		     <Minion
		       	key={"fieldMinion" + index}
		     	active = {true === this.props.belongsToSelf ? this.props.cellsOfSummonedMinionsThisTurn[index] : false}
		     	activeAtacker = {true === this.props.belongsToSelf ? this.props.cellsOfAttaquingMinion[index] : false}
		     attackerSelected = {false === this.props.belongsToSelf ? this.props.attackerSelecter : false}	
		     onClick={() => this.handleSelectMinion(index, this.props.playerIndex)} {...minion}{...props}/>
		    )
		   }, this)
		return <div> {fieldMinions} </div>;
	}
}
