import React, {Component} from 'react';
import Card from '../cardComponents/Card.js';

export default class Hand extends Component{

	constructor(props){
		super(props);
		this.state={
			selectedHandCardIndex : null
		};
	}

	render(){
		const props = (this.props.players[this.props.playerIndex].hand);
		let handCards = this.props.players[this.props.playerIndex].hand.map(function(card, index){
				return (
				 <Card
					 key={"handCard" + index}
					 active={index === this.props.selectedCardIndex}
					 onClick={() => this.handleSelectHandCard(index)} faceUp={this.props.faceUp} {...card}{...props}/>
				)
			 }, this)
		return <div> {handCards} </div>;
	}

	handleSelectHandCard = (index) => {
		  if(index === this.props.selectedCardIndex){
				this.props.callBackSelectedCardIndex(null);
		  }
		  else{
				this.props.callBackSelectedCardIndex(index);
		  }
	}
}

