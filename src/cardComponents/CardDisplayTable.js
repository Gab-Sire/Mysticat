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
			return (
			 <Card
				 key={"handCard" + index}
			 	 displayList={true}
				 faceUp={true}
			 	 index={index}
			 {...card}{...props}/>
			)
		 }, this)
	return (<table><tr>{deck}</tr></table>);  
	  
  }
}