import React, { Component } from 'react';
import Beforeunload from 'react-beforeunload';

export default class CardTile extends Component{
	constructor(props){
		super(props);
		this.state={};
	}

	render(){
      let heros = this.props.heros;
      let heroSelect = this.props.heros.map(function(hero, index){//[];
      //let nbHero = this.props.heros.length;
      //this.sendTheHero.bind
	  //for(var i = 0; i < nbHero; i++){
		  
		 // heroSelect.push(
		  return(<span> 
          	<a className={"card wide "+hero} key={"hero" + index} onClick={() => this.sendTheHero(hero)}></a>
          	{(0===((index+1)%5) ) ? <br/>:null}
          </span>)});
	  //}
		 
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
	
	sendTheHero= (hero) => {
		this.props.sendHero(hero);
	}
	
}
