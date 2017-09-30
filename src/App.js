import React, {Component} from 'react';
import CardList from './CardList';
import axios from 'axios';
import _ from 'lodash';
import './App.css';


class App extends Component{
	constructor(props){
		super(props);
		this.state ={
			loaded: false,
			
		}
		this.getInitialGameInstance();
	}
	render(){
		if(this.state.loaded){
			console.log(this.state.gameState.players[0].hand);
			return(
				<div>
					<div id="board">
						<div id="opponentHand">
							<div className="card">
								Card back
							</div>
						</div>
						<div id="selfHand">
							<CardList 
								cards = {this.state.gameState.players[0].hand}
						/>
						</div>
					</div>
					<div>				
				</div>
				</div>
					);
		}
		return(
				<div>
					<div id="board">
						<div id="opponentHand">
							<div className="card">
								Card back
							</div>
						</div>
						<div id="selfHand">
							<CardList 
						/>						
						</div>
					</div>
					<div>				
				</div>
				</div>
					);
	}
	
	getInitialGameInstance(){
		axios({
			  method:'get',
			  url:'http://localhost:8089/getHardCodedGame',
			  responseType:'stream',
			  headers: {'Access-Control-Allow-Origin': "true"}
			})
			  .then((response)=>{
				  console.log(response);
				  this.state.gameState = response.data;
				  this.state.loaded = true;
				  this.render();
				})
				.catch(error => {
				  console.log('Error fetching and parsing data', error);
				});
	}

}

export default App;