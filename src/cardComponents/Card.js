import React, {Component} from 'react';

export default class Card extends Component{
	constructor(props){
		super(props);
		this.state={faceUp: true, selected:false};
	}
	
	handleClick = () => {
		this.setState(prevState =>({ selected: !this.state.selected }));
	};
	
	render(){
		let isSelected = "";
		if(this.props.faceUp != null && this.props.faceUp != undefined){
			this.state.faceUp = this.props.faceUp;
		}
		if(true == this.state.selected){
			isSelected = "selected";
		}

		if(true == this.state.faceUp){
			return (<div className={"card " + isSelected} title={this.state.description} onClick={this.handleClick}>
				<div className="cardName">{this.props.name}</div>
				<div title="The amount of mana crystals consumed when summoning this minion" className="cardManaCost">Cost: {this.props.manaCost}</div>
				<div title="The amount of damage this minion deals" className="cardPower">Power: {this.props.initialPower}</div>
				<div title="The amount of damage this minion can take" className="cardHealth">Health: {this.props.initialHealth}</div>
				<div title="Speed dictates the order in which attacks resolve" className="cardSpeed">Speed: {this.props.initialSpeed}</div>
			</div>);
		}
		else{
			return (<div className="cardFacedDown"></div>);
		}	
	}
}
