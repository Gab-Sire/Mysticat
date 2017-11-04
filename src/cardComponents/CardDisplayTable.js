import React, { Component } from 'react';
import axios from 'axios';
import '../styles/app.css';
import Card from '../cardComponents/Card.js';


const TIME_BETWEEN_AXIOS_CALLS = 1000;

export default class CardDisplayTable extends Component {

  constructor(props) {
    super(props);
    this.state = {

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
  						 {...card}{...props} />
             </span>
						)
		  }

		 }, this)
	return (<div id='CardCollection'>
				<h1 className='displayDeckTitle'>Affichage Deck</h1>
				<div className='cardDisplayTable'>
					{deck}
					<button id="backToMenu" onClick={this.goBackToDeckList.bind(this)}>Retour au Liste</button>
				</div>
			</div>);
  }
/**
 * <form>
					<>
					<button onClick={this.sortCard.bind(this)} ></button>
				</from>
 */
  goBackToDeckList(){
		this.props.appDisplay("deck_selection");
	}

}
