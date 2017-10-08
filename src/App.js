import React, {Component} from 'react';
import axios from 'axios';
import './styles/app.css';
import _ from 'lodash';
import Board from './boardComponents/Board.js';
import Card from './cardComponents/Card.js';
import Minion from './cardComponents/Minion.js';
import CardTile from './cardComponents/CardTile.js';
import Field from './boardComponents/Field.js';
import Graveyard from './boardComponents/Graveyard.js';
import Deck from './boardComponents/Deck.js';
import Hero from './boardComponents/Hero.js';
import Login from "./Login.js";
import Signup from "./Signup.js";

class App extends Component{
	constructor(props){
		super(props);
		this.getInitialGameInstance();
		this.state ={
			inGame: false,
			gameState : {
				currentMana: 0,
				players: [
					{
						hero: {
							health:30
						},
						hand : [
							{
								name: "test",
								initialPower: "test",
								manaCost: "test",
								initialHealth: "test",
								initialSpeed: "test",
								description: "test"
							}
						], 
						field : [
							null, 
							null, 
							null, 
							null, 
							null, 
							null, 
							null
						],
						graveyard:[],
						deck: []
					},
					{
						hero:{
							health:30
						},
						hand : [
							{
								name: "test",
								initialPower: "test",
								manaCost: "test",
								initialHealth: "test",
								initialSpeed: "test",
								description: "test"

							}
						],
						field : [
							null, 
							null, 
							null, 
							null, 
							null, 
							null, 
							null
						],
						graveyard:[],
						deck:[]
					}
				]
		}
		};
	}
		
	render(){
		if(true===this.state.inGame){
			return(
				<Board gameState={this.state.gameState}/>
			);
		}
		else{
			return <Signup />
		}
	}
	
	// Fonction qui fetch un game state fait par Spring avec valeurs par defaut
	getInitialGameInstance(){
		axios({
			  method:'get',
			  url:'http://localhost:8089/getHardCodedGame',
			  responseType:'json',
			  headers: {'Access-Control-Allow-Origin': "true"}
			})
			  .then((response)=>{
				  this.setState({gameState: response.data});
				  this.forceUpdate();
				  console.log(response.data);
				})
				.catch(error => {
				  console.log('Error fetching and parsing data', error);
				});
	}
	
	updateGameState(){
		const data = this.state.gameState;
		axios({
			  method:'post',
			  url:'http://localhost:8089/updateGame',
			  responseType:'json',
			  headers: {'Access-Control-Allow-Origin': "true"},
			  data: data
			})
			  .then((response)=>{
				  this.setState({gameState: response.data});
				  this.forceUpdate();
				  console.log(response.data);
				})
				.catch(error => {
				  console.log('Error fetching and parsing data', error);
				});
	}

}

export default App;