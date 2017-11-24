
import React, { Component } from 'react';
import axios from 'axios';
import '../styles/menu.css';
import Card from '../cardComponents/Card.js';
import Not30CardsInDeckWarning from './Not30CardsInDeckWarning.js';
import SavedDeckSuccess from './SavedDeckSuccess.js';
import IllegalDeckNameWarning from "./IllegalDeckNameWarning.js";
import * as Constantes from '../Constantes.js';

const MAX_CARDS_IN_DECK = 30;
const MAX_DECK_NAME_LENGTH = 255;
const MIN_DECK_NAME_LENGTH = 1;

export default class CardDisplayTable extends Component {
  constructor(props) {
    super(props);
    this.state = {
        editMode: false,
        collection : [],
        isNewFavorite : false,
        isAlertDeckSizeVisible: false,
        isSuccessVisible: false,
        isAlertDeckNameVisible: false
    }
  }

  componentWillMount(){
    this.getCollection();
    this.setState({
            favorite: this.props.favorite,
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
                     description={this.props.deckList.cardList[indexInDeck].cardDescription}
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
        <div id="topMenuItems">
        <Not30CardsInDeckWarning
            MAX_CARDS_IN_DECK={MAX_CARDS_IN_DECK} visible={this.state.isAlertDeckSizeVisible}
            onDismiss = {this.onDismissWarningDeckSize.bind(this)}
        />
        <IllegalDeckNameWarning
            MIN_DECK_NAME_LENGTH={MIN_DECK_NAME_LENGTH} MAX_DECK_NAME_LENGTH={MAX_DECK_NAME_LENGTH} visible={this.state.isAlertDeckNameVisible}
            onDismiss = {this.onDismissWarningName.bind(this)}
        />
        <SavedDeckSuccess
            visible={this.state.isSuccessVisible}
            onDismiss = {this.onDismissSuccess.bind(this)}
        />
         <button id="backToDeckSelection" onClick={this.props.goDeckSelection}>Retour &agrave; la s&eacute;lection de deck</button>
        <h2 className='displayDeckTitle'>{(true===this.state.editMode) ? "Modification du deck" : "Affichage du deck"}</h2>
        <button className='buttonDeckMod' onClick={this.switchEditMode.bind(this)}>{(true===this.state.editMode) ? "Changer au mode visualisation" : "Changer au mode edit"}</button>
				<div>{(true===this.state.editMode) ?
				<div className='displayDeckTitle'><input type="text" onChange={this.handleChangeDeckName.bind(this)} placeholder="Nom du deck" value={this.state.deckName} />
				<button id="saveDeck" onClick={this.saveDeck.bind(this)}>Sauvegarder le deck</button>
        </div>
				:
				<h4 className='displayDeckTitle'>{this.state.deckName}</h4>
						}</div>
        <div id="cardCounter">{<span className={(false===this.isTheDeckValid()) ? "invalidDeck" : ""}>{deck.length}</span>}/30</div>
        {
          (true===this.state.editMode) ? (
            (this.state.deckIndex===this.state.favorite) ? <div title="Votre deck actif est celui avec lequel vous jouez une partie." id="changeFavoriteDeckText">Deck actif</div> :
            <div id="changeFavoriteDeckText" title="Votre deck actif est celui avec lequel vous jouez une partie.">Choisir comme deck actif<input id="selectFavoriteDeckCheckbox" type="checkbox" name="isFavorite" onChange={this.handleChangeFavorite.bind(this)} /></div>
          ) : (this.state.deckIndex===this.state.favorite) ? <div title="Votre deck actif est celui avec lequel vous jouez une partie." id="changeFavoriteDeckText">Deck actif</div> : null
        }
        </div>
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
	        </div>
        }
			</div>);
  }

  getCollection(){
	  axios({
	        method:'get',
	        url:'http://'+window.location.hostname+':'+Constantes.PORT_NUMBER+'/getCollection',
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
    if(true===this.isTheDeckValid() && this.isTheDeckNameValid()){
      let userId = this.state.userId;
      let cardIds = [];
      let deckIndex = this.state.deckIndex;
      let deckName = this.state.deckName;
      let isNewFavorite = this.state.isNewFavorite;
      for(let i=0; i<this.state.deck.length; i++){
          cardIds.push(this.state.deck[i].cardId);
      }
      axios({
          method:'post',
          url:'http://'+window.location.hostname+':'+Constantes.PORT_NUMBER+'/saveDeck',
          responseType:'json',
          headers: {'Access-Control-Allow-Origin': "true"},
          data:{
            cardIds: cardIds,
            deckIndex: deckIndex,
            deckName: deckName,
            userId: userId,
            isNewFavorite: isNewFavorite
          }
        })
          .then((response)=>{
            this.setState({isSuccessVisible:true,
                          isAlertDeckSizeVisible: false,
                          isAlertDeckNameVisible: false})
          })
          .catch(error => {
            console.log('Error fetching and parsing data', error);
            this.deconnexion();
          });
    }
    else{
      if(false===this.isTheDeckValid()){
        this.setState({isAlertDeckSizeVisible:true})
      }else{
          this.setState({isAlertDeckSizeVisible:false})
      }
      if(false===this.isTheDeckNameValid()){
        this.setState({isAlertDeckNameVisible:true})
      }else{
          this.setState({isAlertDeckNameVisible:false})
      }
      this.setState({isSuccessVisible:false})
    }
  }

  handleChangeFavorite(event){
    let target = event.target;
    let isFavorite = target.checked;
    this.setState({isNewFavorite: isFavorite});
  }

  handleChangeDeckName(event){
    this.setState({deckName: event.target.value})
  }

  goBackToDeckList(){
		this.props.appDisplay("deck_selection");
	}

  switchEditMode(){
      this.setState({editMode: !this.state.editMode,
                    isSuccessVisible: false,
                    isAlertDeckNameVisible: false,
                    isAlertDeckSizeVisible: false})
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

  isTheDeckNameValid(){
    return (this.state.deckName.length <= MAX_DECK_NAME_LENGTH && this.state.deckName.length >= MIN_DECK_NAME_LENGTH);
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

  onDismissWarningDeckSize(){
    this.setState({isAlertDeckSizeVisible: false})
  }

  onDismissWarningName(){
    this.setState({isAlertDeckNameVisible: false})
  }


  onDismissSuccess(){
    this.setState({isSuccessVisible: false})
  }

  deconnexion(){
    this.props.disconnectPlayer();
  }
}
