import React, { Component } from 'react';
import HeroDisplay from './HeroDisplay.js';
import Beforeunload from 'react-beforeunload';

export default class HeroChange extends Component{
	constructor(props){
		super(props);
		this.state={};
	}

	render(){
      let heroSelect = this.props.heros.map(function(hero, index){
		  return(<span>
          	<HeroDisplay hero={hero} key={hero} sendingTheHero={this.sendTheHero.bind(this)} isActiveHero={this.props.currentHero===hero}/>
          	{(0===((index+1)%5) ) ? <br/>:null}
          </span>);
      }.bind(this));


		 return (<div id='MainMenu'>
		 			<div id="heroSelectionContainer">
			 			<Beforeunload onBeforeunload={() => {this.props.disconnectPlayer(); return "Are you sure?"}}/>
			 			<h2 className='displayHeroSelectionTitle'>Choisissez votre avatar</h2>
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
