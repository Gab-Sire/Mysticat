import React, { Component } from 'react';
import HeroDisplay from './HeroDisplay.js';
import Beforeunload from 'react-beforeunload';

export default class HeroChange extends Component{
	constructor(props){
		super(props);
		this.state={};
	}

	render(){
      let heros = this.props.heros;
      let heroSelect = this.props.heros.map(function(hero, index){
		  return(<span> 
          	<HeroDisplay hero={hero} key={hero} sendingTheHero={this.sendTheHero.bind(this)}/>
          	{(0===((index+1)%5) ) ? <br/>:null}
          </span>);
      }.bind(this));
	 
		 
		 return (<div id='MainMenu'>
		 			<div>
			 			<Beforeunload onBeforeunload={() => {this.deconnexion(); return "Are you sure?"}}/>
			 			<h2 className='displayDeckTitle'>Quel est le Hero que tu préfères?</h2>
			 			<h5 className='displayDeckTitle'> Moi, j'ai bien Mitaine</h5>
			 			<div className="containerHero">
			 				{heroSelect}
			 				<button id="backToMenu" onClick={this.goBackToMenu.bind(this)}>Retour au menu</button>
			 			</div>
		 			</div>
		 		</div>);
		
	}//sendingTheHero={this.sendTheHero.bind(this)}
	goBackToMenu(){
		this.props.appDisplay("menu");
	}
	
	sendTheHero(hero){
		this.props.sendingTheHero(hero);
	}
	
}
