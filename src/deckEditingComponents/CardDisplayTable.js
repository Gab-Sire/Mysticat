import React, { Component } from 'react';
import axios from 'axios';
import '../styles/menu.css';
import Card from '../cardComponents/Card.js';


const TIME_BETWEEN_AXIOS_CALLS = 1000;

export default class CardDisplayTable extends Component {
  constructor(props) {
    super(props);
    this.state = {
        editMode: false
    }
  }

  render() {
      const props = this.state.deck;
      let deck = this.state.deck.map(function(card, index){
		  if(0===((index+1)%5) ){
			  return (
						 <span className='cardDisplay'>
               <Card
  							 key={"handCard" + index}
  						 	 displayList={true}
  							 faceUp={true}
  						 	 listed={true}
  						 	 name={this.state.deck[index].cardName}
  						 	 index={index}
                 onClick={(true===this.state.EditMode) ? this.removeCardFromDeck.bind(this, index) : null}
                 description={this.state.deck[index].cardDescription}
                 beingEdited = {this.state.EditMode}
  						 {...card}{...props} /><br/>
             </span>
						)
		  }else{
			  return (
						 <span className='cardDisplay'>
               <Card
  							 key={"handCard" + index}
  						 	 displayList={true}
  							 faceUp={true}
  						 	 index={index}
  					 	   listed={true}
                 onClick={(true===this.state.editMode) ? this.removeCardFromDeck.bind(this, index) : null}
                 description={this.props.deckList.cardList[index].cardDescription}
  						 {...card}{...props} />
             </span>
						)
		  }

		 }, this)
	return (<div id='CardCollection'>
				<h1 className='displayDeckTitle'>Affichage Deck</h1>
        <div id="cardCounter">{deck.length}/30</div>
        <button onClick={this.switchEditMode.bind(this)}>{(true===this.state.editMode) ? "Changer au mode visualisation" : "Changer au mode edit"}</button>
				<div className='cardDisplayTable'>
					{deck}
					<button id="backToDeckSelection" onClick={this.goBackToDeckList.bind(this)}>Retour &agrave; la s&eacute;lection de deck</button>
				</div>
			</div>);
  }

  componentWillMount(){
    this.setState({deck:this.props.deckList.cardList});
  }

  goBackToDeckList(){
		this.props.appDisplay("deck_selection");
	}

  switchEditMode(){
      this.setState({editMode: !this.state.editMode})
  }

  removeCardFromDeck(index){
      let deck = this.state.deck;
      deck.splice(index, 1);
      this.setState({deck: deck});
      console.log(this.state.deck);
  }
}
