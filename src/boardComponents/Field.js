import React, {Component} from 'react';
import Minion from '../cardComponents/Minion.js';

export default class Field extends Component{

	constructor(props){
		super(props);
		this.state={
			selectedMinionIndex : null,
		}
	}

	render(){
		const props = (this.props.players[this.props.playerIndex].field);
		let fieldMinions = this.props.players[this.props.playerIndex].field.map(function(minion, index){
				return (
				 <Minion
						key={"fieldMinion" + index}
					active = {true === this.props.belongsToSelf ? this.props.cellsOfSummonedMinionsThisTurn[index] : false}
					onClick={() => this.handleSelectMinion(index, this.props.playerIndex)} {...minion}{...props}/>
				)
			 }, this)
		return <div> {fieldMinions} </div>;
	}

	handleSelectMinion = (index, indexPlayer) => {
		if(true === this.props.belongsToSelf){
			let isEmpty = this.isThisFieldCellEmpty(index);

				this.setState({ selectedMinionIndex: index })
				this.props.callBackSelectedMinion(index);
			}
	}

	isThisFieldCellEmpty = (index) => {
		return (null === this.props.self.field[index]);
	}
}
