import React, {Component} from 'react';
import Minion from '../cardComponents/Minion.js';

export default class Field extends Component{

	constructor(props){
		super(props);
		this.state={
			selectedMinionIndex : null,
		}
	}

	isThisFieldCellEmpty = (index) => {
		return (null === this.props.self.field[index]);
	}

	isThisFieldCellAssignedToPreviousSummon = (index) => {

	//	return (true === this.props.cellsOfSummonedMinionsThisTurn[index]);
	}

	assignFieldCellToSummon = (index) => {
		this.props.cellsOfSummonedMinionsThisTurn[index] = true;
	}

	handleSelectMinion = (index, indexPlayer) => {

		if(true === this.props.active){
			let isEmpty = this.isThisFieldCellEmpty(index);
			let isAssigned = this.isThisFieldCellAssignedToPreviousSummon(index);

			if(true === isEmpty && false === isAssigned){
				this.assignFieldCellToSummon(index);
			}

			if(index === this.state.selectedMinionIndex){
				this.setState({ selectedMinionIndex: null })
			}
			else{
				this.setState({ selectedMinionIndex: index })
				this.props.callBackSelectedMinion(index);
			}
		}
	}

	render(){
		const props = (this.props.players[this.props.playerIndex].field);
		let fieldMinions = this.props.players[this.props.playerIndex].field.map(function(minion, index){
		    return (
		     <Minion
		       	key={"fieldMinion" + index}
		     	active = {true === this.props.active ? this.props.cellsOfSummonedMinionsThisTurn[index] : false}
		     	onClick={() => this.handleSelectMinion(index, this.props.playerIndex)} {...minion}{...props}/>
		    )
		   }, this)
		return <div> {fieldMinions} </div>;
	}
}
