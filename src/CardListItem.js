import React, { Component } from 'react';
import './App.css';

export default class CardListItem extends Component {
  constructor(props) {
    super(props);
    this.state = {
    }
  }
  
  renderHand(){
	    const { card } = this.props;
	    return (
	    	<div className="card">
				<div className="cardSpeed">
				{card.name}
				Cost: {card.manaCost}
				Power: {card.initialPower}
				Health: {card.initialHealth}
				Speed: {card.initialSpeed}
				</div>
			</div>
	    )
	  }
  }