import React, {Component} from 'react';
import Card from '../cardComponents/Card.js';

export default class Hand extends Component{
	
	constructor(props){
		super(props);
		this.state={
			selectedHandCardIndex : null
		};
	}
	
	handleSelectHandCard = (index) => {
		  if(index === this.state.selectedHandCardIndex){
			  this.setState({ selectedHandCardIndex: null })
			  this.props.callBackSelectedCardIndex(index);  
		  }
		  else{
			  this.setState({ selectedHandCardIndex : index })
			  this.props.callBackSelectedCardIndex(index);  
		  }
	}
	
	render(){
		const props = (this.props.players[this.props.playerIndex].hand);
		let handCards = this.props.players[this.props.playerIndex].hand.map(function(card, index){
		    return (
		     <Card
		       key={"handCard" + index}
		       active={index === this.state.selectedHandCardIndex}
		       onClick={() => this.handleSelectHandCard(index)} faceUp={this.props.faceUp} {...card}{...props}/>
		    )
		   }, this)
		return <div> {handCards} </div>;
	}
}