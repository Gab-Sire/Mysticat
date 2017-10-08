import React, {Component} from 'react';

export default class Card extends Component{
	constructor(props){
		super(props);
		this.state={faceUp: true};
	}
	
	render(){
		let isFaceUp = this.state.faceUp;
		isFaceUp = (this.props.name!=null) ? true : false;
		if(isFaceUp){
			return (<div className="cardTile minion" title={this.props.description}>
				<div className="cardName">{this.props.name}</div>
				<div title="The amount of damage this minion deals" className="cardPower">Power: {this.props.power}</div>
				<div title="The amount of damage this minion can take" className="cardHealth">Health: {this.props.health}</div>
				<div title="Speed dictates the order in which attacks resolve" className="cardSpeed">Speed: {this.props.speed}</div>
			</div>);
		}
		else{
			return (<div className="cardTile"></div>);
		}	
	}
}