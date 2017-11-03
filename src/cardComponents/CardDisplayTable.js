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
		  if(0!==index && 0===index%5 ){
			  return (
						 <span><Card
							 key={"handCard" + index}
						 	 displayList={true}
							 faceUp={true}
						 	 index={index}
						 {...card}{...props} /><br/></span>
						)  
		  }else{
			  return (
						 <span><Card
							 key={"handCard" + index}
						 	 displayList={true}
							 faceUp={true}
						 	 index={index}
						 {...card}{...props} /></span>
						)   
		  }
			
		 }, this)
	return (<div>{deck}</div>);  
	  
  }
}