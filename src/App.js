import React, {Component} from 'react';
import axios from 'axios';
import _ from 'lodash';
import './App.css';


class App extends Component{
	constructor(props){
		super(props);
		this.getInitialGameInstance();
		this.state ={
				counter :1,
			gameState : {
				//En attendant que le call asynchrone finisse besoin d'un player
				players: [
						{
						hand : [
							{

							}
						]
						}
				]
			}
		}
	}
	render(){
			return(
				<div>
					<div id="board">
						<div id="opponentHand">
							<div className="card">
								Card back
							</div>
						</div>
							<div id="selfHand">
							{this.state.gameState.players[0].hand.map((card)=>{
								return <div className="card">
								<div className="cardName">{card.name}</div>
								<div title="The amount of mana crystals consumed when summoning this minion" className="cardManaCost">Cost: {card.manaCost}</div>
								<div title="The amount of damage this minion deals" className="cardPower">Power: {card.initialPower}</div>
								<div title="The amount of damage this minion can take" className="cardHealth">Health: {card.initialHealth}</div>
								<div title="Speed dictates the order in which attacks resolve" className="cardSpeed">Speed: {card.initialSpeed}</div>
								</div>
							})}
						</div>
					</div>
				</div>
					);
	}
	
	//Fonction qui fetch un game state fait par Spring avec valeurs par defaut
	getInitialGameInstance(){
		axios({
			  method:'get',
			  url:'http://localhost:8089/getHardCodedGame',
			  responseType:'stream',
			  headers: {'Access-Control-Allow-Origin': "true"}
			})
			  .then((response)=>{
				  this.setState({gameState: response.data});
				  this.forceUpdate();
				})
				.catch(error => {
				  console.log('Error fetching and parsing data', error);
				});
	}

}

export default App;