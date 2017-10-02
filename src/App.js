import React, {Component} from 'react';
import axios from 'axios';
import './app.css';
import _ from 'lodash';
import Login from './Login.js';
import Signup from './Signup.js';
import Card from './Card.js';


class App extends Component{
	constructor(props){
		super(props);
		this.getInitialGameInstance();
		this.state ={
				// En attendant que le call asynchrone finisse besoin d'un
				// player
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
						]
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
							]
						}
				]
			}
		}
	}
	render(){
		let selfHealth = this.state.gameState.players[0].hero.health;
		let opponentHealth = this.state.gameState.players[1].hero.health;
		let globalMana = this.state.gameState.currentMana;
			return(
				<div>
					<div id="board">
						<div id="opponentHand" className="hand">
							<div className="card">
								Card back
							</div>
						</div>
						<div id="opponentHealth" className="heroHealth">
						{opponentHealth}
						</div>
						<div id="globalMana" title="The amount of mana that you get per turn">
							<div>
								{globalMana}
							</div>
						</div>
							<div id="selfHealth" className="heroHealth">
								{selfHealth}
							</div>
							<div id="selfHand" className="hand">
							{this.renderSelfHand()}
						</div>
					</div>
					<Signup />
					<Login />
				</div>
					);
	}
	
	renderSelfHand(){
		let selfHand = this.state.gameState.players[0].hand;
		const props = (this.state.gameState.players[0].hand);
		return _.map(selfHand, card=> <Card key={card.key} {...card}{...props}/>);
		
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

}

export default App;