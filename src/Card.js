import React, {Component} from 'react';

export default class Card extends Component{
	constructor(props){
		super(props);
		this.state={faceUp: true};
	}
	
	render(){
		let isFaceUp = this.state.faceUp;
		
		if(isFaceUp){
			return (<div className="card" title={this.state.description}}>
			<div className="cardName">{this.props.name}op</div>
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
