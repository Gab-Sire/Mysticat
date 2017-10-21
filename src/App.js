import React, {Component} from 'react';
import axios from 'axios';
import './styles/app.css';
import '../node_modules/bootstrap/dist/css/bootstrap.min.css';
import Board from './boardComponents/Board.js';
import Connection from "./Connection.js";
import MainMenu from './menuComponents/MainMenu.js';
import LoadingScreen from './menuComponents/LoadingScreen.js';
const TIME_BETWEEN_POLLS = 5000;
class App extends Component{
	constructor(props){
		super(props);
		this.state ={
			isServerAvailable: false,
			inGame: false,
			playerId: null,
			gameState: null
		};
	}

	componentWillMount(){
		this.checkServerAvailability();
		this.getTestActionList();
	}

	render(){
		if(true===this.state.isServerAvailable){
			if(false===this.state.inGame && null !==this.state.playerId){
				return <MainMenu playerId={this.state.playerId} getQueueForParent={this.getGameFromQueue} disconnectPlayer={this.disconnectPlayer.bind(this)} />
			}else if(true===this.state.inGame){
				return(
					<Board gameState={this.state.gameState} playerId={this.state.playerId} endGame={this.endGameMode.bind(this)} />
				);
			}
			else{
				return (<div><Connection signupMode={this.state.signupMode} connectPlayer={this.setIdPlayer.bind(this)}/>
						</div>)

			}
		}
		else return (<LoadingScreen text={"Contacting server..."}/>)

	}

	getTestActionList(){
		axios({
			  method:'get',
			  url:'http://localhost:8089/getHardCodedActionSample',
			  responseType:'json',
			  headers: {'Access-Control-Allow-Origin': "true"}
			})
			  .then((response)=>{
				  console.log(response.data);
				})
				.catch(error => {
				  console.log('Error fetching and parsing data', error);
				  setTimeout(()=>{
							  this.checkServerAvailability();
						  }, TIME_BETWEEN_POLLS)
				});
	}

	checkServerAvailability(){
		axios({
			  method:'get',
			  url:'http://localhost:8089/getServerStatus',
			  responseType:'json',
			  headers: {'Access-Control-Allow-Origin': "true"}
			})
			  .then((response)=>{
				  this.setState({isServerAvailable: true});
				})
				.catch(error => {
				  console.log('Error fetching and parsing data', error);
				  setTimeout(()=>{
							  this.checkServerAvailability();
						  }, TIME_BETWEEN_POLLS)
				});
	}

	disconnectPlayer(){
		this.setState({"playerId" : null});
	}

	setIdPlayer(idPlayer){
		this.setState({"playerId" : idPlayer});
	}
	endGameMode(){
		this.setState({"inGame" : false});
	}

	getGameFromQueue = (gameState)=>{
		this.setState({gameState: gameState, inGame: true})
	}
}

export default App;
