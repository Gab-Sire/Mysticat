import React, { Component } from 'react';
import axios from 'axios';
import '../styles/menu.css';
import Card from '../cardComponents/Card.js';


const TIME_BETWEEN_AXIOS_CALLS = 1000;
const MAX_CARDS_IN_DECK = 30;

export default class CardDisplayTable extends Component {
  constructor(props) {
    super(props);
    this.state = {
        editMode: false
    }
  }

  componentWillMount(){
    this.setState({deck:this.props.deckList.cardList});
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
        <div id="cardCounter">{<span className={(deck.length<30) ? "incompleteDeck" : ""}>{deck.length}</span>}/30</div>
        <button onClick={this.switchEditMode.bind(this)}>{(true===this.state.editMode) ? "Changer au mode visualisation" : "Changer au mode edit"}</button>
				<div className='cardDisplayTable'>
					{deck}
          <button id="saveDeck" onClick={this.saveDeck.bind(this)}>Sauvegarder le deck</button>
					<button id="backToDeckSelection" onClick={this.goBackToDeckList.bind(this)}>Retour &agrave; la s&eacute;lection de deck</button>
				</div>
			</div>);
  }

  saveDeck(){
    axios({
        method:'post',
        url:'http://'+window.location.hostname+':8089/getHardCodedGame',
        responseType:'json',
        headers: {'Access-Control-Allow-Origin': "true"}
      })
        .then((response)=>{
          this.setState({gameState: response.data, isLoaded: true});
          this.forceUpdate();
        })
        .catch(error => {
          console.log('Error fetching and parsing data', error);
          setTimeout(()=>{
            this.getInitialGameInstance();
          }, TIME_BETWEEN_AXIOS_CALLS)
    });
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

  addCardToDeck(index){
    if(this.state.deck.length < MAX_CARDS_IN_DECK){
      let collection = this.state.collection;
      let card = collection[index];
      let deck = this.state.deck;
      deck.push(card);
      this.setState({deck: deck});
    }
  }

  isThisCardInTheDeck(indexInCollection){
      let collection = this.state.collection;
      let deck = this.state.deck;
      let card = collection[indexInCollection];

      for(let i=0; i<deck.length; i++){
          if(deck[i]===card){
            return true;
          }
      }
      return false;
  }

}
