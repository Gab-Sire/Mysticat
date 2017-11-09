import React, {Component} from 'react';

export default class CardTile extends Component{
	constructor(props){
		super(props);
		this.state={};
	}

	render(){
		if(true===this.props.isUserDeck){
			return (<div className="cardTile" onClick={this.selectDeck.bind(this)}><div className="displayDeckAjouter">Nouveau Deck</div></div>);
		}else{
			return (<div className="cardTile"></div>);
		}
		
	}
	selectDeck(){
		this.props.deckSelection(this.props.index);
		this.props.appDisplay("displayDeck");
	}
}
