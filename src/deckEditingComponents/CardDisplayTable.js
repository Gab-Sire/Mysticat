import React, { Component } from 'react';
import axios from 'axios';
import '../styles/menu.css';
import Card from '../cardComponents/Card.js';
import Not30CardsInDeckWarning from './Not30CardsInDeckWarning.js';

const MAX_CARDS_IN_DECK = 30;

export default class CardDisplayTable extends Component {
  constructor(props) {
    super(props);
    this.state = {
        editMode: false,
        collection : [],
        isAlertVisible: false
    }
  }

  componentWillMount(){
    this.getCollection();
    this.setState({
    		    deck:this.props.deckList.cardList,
            deckIndex: this.props.deckId,
            userId: this.props.playerId,
            deckName: this.props.deckList.name});
    if(this.props.deckList.cardList.length!==MAX_CARDS_IN_DECK){
        this.setState({editMode: true})
    }
  }
  render() {
      const props = this.state.deck;
      let deck = this.state.deck.map(function(card, index){
    	  return (
					 <span className='cardDisplay' key={"handCard" + index}>
              <Card
      						 displayList={true}
      						 faceUp={true}
      					 	 index={index}
      				 	   listed={true}
                   onClick={(true===this.state.editMode) ? this.removeCardFromDeck.bind(this, index) : null}
                   description={this.props.deckList.cardList[index].cardDescription}
      					   {...card}{...props}
               />
              {(0===((index+1)%5) ) ? <br/>:null}
           </span>
        )
		 }, this)
     let editModeCollection = this.state.collection.map((card, index)=>{
     let indexInDeck = this.isThisCardInTheDeck(index);
     if(indexInDeck!==-1){
           return (
             <span className='cardDisplay' key={"handCard" + index}>
                <Card
                     displayList={true}
                     faceUp={true}
                     index={index}
                     listed={true}
                     isFromCollection={false}
                     onClick={(true===this.state.editMode) ? this.removeCardFromDeck.bind(this, indexInDeck) : null}
                     {...card}{...props}
                 />
                 </span>)
         }else{
           return(
           <span className='cardDisplay' key={"collectionCard" + index}>
              <Card
                   displayList={true}
                   faceUp={true}
                   index={index}
                   listed={true}
                   isFromCollection={true}
                   onClick={(true===this.state.editMode) ? this.addCardToDeck.bind(this, index) : null}
                   description={this.state.collection[index].cardDescription}
                   {...card}{...props}
               />
               {(0===((index+1)%5) ) ? <br/>:null}
            </span>
          )
         }
     }, this)

	return (<div id='CardCollection'>
	<button className='buttonDeckMod' onClick={this.switchEditMode.bind(this)}>{(true===this.state.editMode) ? "Changer au mode visualisation" : "Changer au mode edit"}</button>
        <Not30CardsInDeckWarning
            MAX_CARDS_IN_DECK={MAX_CARDS_IN_DECK} visible={this.state.isAlertVisible}
            onDismiss = {this.onDismissWarning.bind(this)}
        />
        <h1 className='displayDeckTitle'>{(true===this.state.editMode) ? "Modification Deck" : "Affichage Deck"}</h1>
				<div>{(true===this.state.editMode) ?
				<div className='displayDeckTitle'><input type="text" onChange={this.handleChangeDeckName.bind(this)} placeholder="Nom du deck" value={this.state.deckName} />
				<button id="saveDeck" onClick={this.saveDeck.bind(this)}>Sauvegarder le deck</button></div>
				:
				<h2 className='displayDeckTitle'>{this.state.deckName}</h2>
						}</div>

        <div id="cardCounter">{<span className={(false===this.isTheDeckValid()) ? "invalidDeck" : ""}>{deck.length}</span>}/30</div>

        <br/>
        {(true===this.state.editMode) ?
          <div>

  	        <div className='cardDisplayTable editMode'>
    					{(true===this.state.editMode) ? editModeCollection : deck}
    					<button id="backToDeckSelection" onClick={this.props.goDeckSelection}>Retour &agrave; la s&eacute;lection de deck</button>
  		      </div>
	        </div>
          :
          <div className='cardDisplayTable'>
    		      {deck}
    		      <button id="backToDeckSelection" onClick={this.props.goDeckSelection}>Retour &agrave; la s&eacute;lection de deck</button>
	        </div>
        }

			</div>);
  }

  getCollection(){
	  axios({
	        method:'post',
	        url:'http://'+window.location.hostname+':8089/getCollection',
	        responseType:'json',
	        headers: {'Access-Control-Allow-Origin': "true"}
	      })
	        .then((response)=>{
	        	this.setState({collection:response.data});
	        })
	        .catch(error => {
	          console.log('Error fetching and parsing data', error);
	        });
  }

  saveDeck(){
    if(true===this.isTheDeckValid()){
      let userId = this.state.userId;
      let cardIds = [];
      let deckIndex = this.state.deckIndex;
      let deckName = this.state.deckName;
      for(let i=0; i<this.state.deck.length; i++){
          cardIds.push(this.state.deck[i].cardId);
      }
      axios({
          method:'post',
          url:'http://'+window.location.hostname+':8089/saveDeck',
          responseType:'json',
          headers: {'Access-Control-Allow-Origin': "true"},
          data:{
            cardIds: cardIds,
            deckIndex: deckIndex,
            deckName: deckName,
            userId: userId
          }
        })
          .then((response)=>{
          })
          .catch(error => {
            console.log('Error fetching and parsing data', error);
          });
    }
    else{
        //TODO pop-up d'avertissement que le deck est invalide.
        this.setState({isAlertVisible:true})
    }
  }

  handleChangeDeckName(event){
    this.setState({deckName: event.target.value})
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
  }

  addCardToDeck(index){
    let collection = this.state.collection;
    let card = collection[index];
    let deck = this.state.deck;
    deck.push(card);
    this.setState({deck: deck});
  }

  isTheDeckValid(){
    return (this.state.deck.length === MAX_CARDS_IN_DECK);
  }

  isThisCardInTheDeck(indexInCollection){
      let collection = this.state.collection;
      let deck = this.state.deck;
      let card = collection[indexInCollection];

      for(let i=0; i<deck.length; i++){
          if(deck[i].cardId ===card.cardId){
            return i;
          }
      }
      return -1;
  }

  onDismissWarning(){
    this.setState({isAlertVisible: false})
  }

}
