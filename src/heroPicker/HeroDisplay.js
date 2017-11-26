import React, { Component } from 'react';
import Beforeunload from 'react-beforeunload';

export default class HeroDisplay extends Component{
	constructor(props){
		super(props);
		this.state={};
		this.sendTheHero = this.sendTheHero.bind(this);
	}

	render(){
		let hero = this.props.hero;
		let selected = "";
		if(true===this.props.isActiveHero){
			selected = "selected";
		}
      
		  return(
          	<a className={selected+" card wide "+hero} onClick={this.sendTheHero.bind(this)}></a>
          	);
	
		
	}
	
	sendTheHero(){
		let hero = this.props.hero
		this.props.sendingTheHero(hero);
	}
	
}
