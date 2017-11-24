import React, { Component } from 'react';
import Beforeunload from 'react-beforeunload';

export default class CardTile extends Component{
	constructor(props){
		super(props);
		this.state={};
	}

	render(){
      let heros = this.props.heros;
      let heroSelect = [];
      let nbHero = this.props.heros.length;
	  for(var i = 0; i < nbHero; i++){
		  
		  heroSelect.push(
		  <span> 
          	<a className={"card wide "+heros[i]} key={"hero" + i}></a>
          	{(0===((i+1)%5) ) ? <br/>:null}
          </span>);
	  }
		 
		 return (<div id='MainMenu'>
		 			<div>
			 			<Beforeunload onBeforeunload={() => {this.deconnexion(); return "Are you sure?"}}/>
			 			<h2 className='displayDeckTitle'>Quel est le Hero que tu préfères?</h2>
			 			<h5 className='displayDeckTitle'> Moi j'ai bien Mitaine</h5>
			 			<div className="containerHero">
			 				{heroSelect}
			 			</div>
		 			</div>
		 		</div>);
		
	}
	
}
