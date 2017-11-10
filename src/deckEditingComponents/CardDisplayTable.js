import React, { Component } from 'react';
import axios from 'axios';
import '../styles/menu.css';
import Card from '../cardComponents/Card.js';

const MAX_CARDS_IN_DECK = 30;

export default class CardDisplayTable extends Component {
  constructor(props) {
    super(props);
    this.state = {
        editMode: false,
        collection : []
    }
  }

  componentWillMount(){
    console.log(this.props);
    this.setState({editMode:this.state.editMode});
    this.getCollection();
    	this.setState({
    		    deck:this.props.deckList.cardList,
            deckIndex: this.props.deckId,
            userId: this.props.playerId,
            deckName: this.props.deckList.name});


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
     console.log("Collection",this.state.collection);
     let editModeCollection = this.state.collection.map((card, index)=>{
         let indexInDeck = this.isThisCardInTheDeck(index);
         //if(indexInDeck!==-1){
           return (
             <span className='cardDisplay' key={"handCard" + index}>
                <Card
                     displayList={true}
                     faceUp={true}
                     index={index}
                     listed={true}
                	 color={(indexInDeck===-1)?"gris": null}
                     onClick={(true===this.state.editMode) ? this.removeCardFromDeck.bind(this, indexInDeck) : null}
                     description={this.state.collection[index].cardDescription}
                     {...card}{...props}
                 />
                {(0===((index+1)%5) ) ? <br/>:null}
             </span>
         )
         //}else{
          /* return(
           <span className='cardDisplay' key={"collectionCard" + index}>
              <Card
                   displayList={true}
                   faceUp={true}
                   index={index}
                   listed={true}
                   onClick={(true===this.state.editMode) ? this.addCardToDeck.bind(this, index) : null}
                   description={this.state.collection[index].cardDescription}
                   {...card}{...props}
               />
               {(0===((index+1)%5) ) ? <br/>:null}
            </span>
          )
         }*/
     }, this)

     console.log(editModeCollection);
	return (<div id='CardCollection'>
				<h1 className='displayDeckTitle'>Affichage Deck</h1>
        <div id="cardCounter">{<span className={(deck.length<30) ? "incompleteDeck" : ""}>{deck.length}</span>}/30</div>
        <button onClick={this.switchEditMode.bind(this)}>{(true===this.state.editMode) ? "Changer au mode visualisation" : "Changer au mode edit"}</button>
        <br/>
        {(true===this.state.editMode) ?
          <div>
            <button id="saveDeck" onClick={this.saveDeck.bind(this)}>Sauvegarder le deck</button>
            <input type="text" onChange={this.handleChangeDeckName.bind(this)} placeholder="Nom du deck" value={this.state.deckName} />
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
          if(deck[i].cardId ===card.cardId){
            return i;
          }
      }
      return -1;
  }

}
