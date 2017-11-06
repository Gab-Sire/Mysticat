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
	  const props = (this.props.deckList.cardList);
	  let deck = this.props.deckList.cardList.map(function(card, index){
		  if(0===((index+1)%5) ){
			  return (
						 <span className='cardDisplay'>
               <Card
  							 key={"handCard" + index}
  						 	 displayList={true}
  							 faceUp={true}
  						 	 listed={true}
  						 	 name={this.props.deckList.cardList[index].cardName}
  						 	 index={index}
                 description={this.props.deckList.cardList[index].cardDescription}
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

  goBackToDeckList(){
		this.props.appDisplay("deck_selection");
	}

  switchEditMode(){
      this.setState({editMode: !this.state.editMode})
  }
}
