import React, {Component} from 'react';
import axios from 'axios';
import './styles/app.css';
import '../node_modules/bootstrap/dist/css/bootstrap.min.css';
import Board from './boardComponents/Board.js';
import Connection from "./authenticationComponents/Connection.js";
import MainMenu from './menuComponents/MainMenu.js';
import LoadingScreen from './menuComponents/LoadingScreen.js';
import DeckSelection from './deckEditingComponents/DeckSelection.js';
import DisplayDeck from './deckEditingComponents/DisplayDeck.js';
import AdminDashBoard from './adminComponents/AdminDashBoard.js';

const TIME_BETWEEN_AXIOS_CALLS = 5000;

class App extends Component{
	constructor(props){
		super(props);
		this.state ={
			isServerAvailable: false,
			inGame: false,
			playerId: null,
			playerName: null,
			gameState: null,
			appDisplay: null,
			userDeckList: null,
			userList: null,
			deckId:null
		};
	}

	componentWillMount(){
		this.checkServerAvailability();
		this.getTestActionList();
	}

	render(){
		if(true===this.state.isServerAvailable){
			
			if("admin_dashboard" === this.state.appDisplay){
				return <AdminDashBoard adminName={this.state.playerName} userList={this.state.userList}/>
			}
			else if("deck_selection" === this.state.appDisplay){
				if(null != this.state.userDeckList){
					return <DeckSelection deckList={this.state.userDeckList} appDisplay={this.updateAppDisplay.bind(this)} deckSelection={this.selectDeck.bind(this)} disconnectPlayer={this.disconnectPlayer.bind(this)}/>
				}
			}
			else if("displayDeck" === this.state.appDisplay){
				return <DisplayDeck goDeckSelection={this.goDeckSelection.bind(this)} playerId={this.state.playerId} deckId={this.state.deckId} appDisplay={this.updateAppDisplay.bind(this)} disconnectPlayer={this.disconnectPlayer.bind(this)} />
			}
			else if("menu" === this.state.appDisplay && (false===this.state.inGame && null !==this.state.playerId)){
				return <MainMenu  goDeckSelection = {this.goDeckSelection.bind(this)} playerId={this.state.playerId} setUserDeckList={this.setUserDeckList.bind(this)} getQueueForParent={this.getGameFromQueue} disconnectPlayer={this.disconnectPlayer.bind(this)} appDisplay={this.updateAppDisplay.bind(this)} />
			}else if(true===this.state.inGame){
				return(
					<Board gameState={this.state.gameState} playerId={this.state.playerId} endGame={this.endGameMode.bind(this)} disconnectPlayer={this.disconnectPlayer.bind(this)} />
				);
			}
			else{
				return (<div><Connection updateAppDisplay={this.updateAppDisplay.bind(this)} signupMode={this.state.signupMode} connectPlayer={this.connectPlayer.bind(this)}/>
						</div>)

			}
		}
		else return (<LoadingScreen text={"Contacting server..."}/>)

	}

	getTestActionList(){
		axios({
			  method:'get',
			  url:'http://'+window.location.hostname+':8089/getHardCodedActionSample',
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
						  }, TIME_BETWEEN_AXIOS_CALLS)
				});
	}

	selectDeck(deckId){
		this.setState({deckId:deckId});
	}

	checkServerAvailability(){
		axios({
			  method:'get',
			  url:'http://'+window.location.hostname+':8089/getServerStatus',
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
						  }, TIME_BETWEEN_AXIOS_CALLS)
				});
	}

	disconnectPlayer(){
		axios({
				method:'post',
				url:'http://'+window.location.hostname+':8089/disconnectUser',
				responseType:'json',
				headers: {'Access-Control-Allow-Origin': "true"},
				data: this.state.playerId
			})
				.then((response)=>{

				})
				.catch(error => {
					console.log('Error fetching and parsing data', error);
		});
		this.setState({"playerId" : null});
	}

	goDeckSelection(){
			axios({
				  method:'post',
				  url:'http://'+window.location.hostname+':8089/getUserDecks',
				  responseType:'json',
				  headers: {'Access-Control-Allow-Origin': "true"},
				  data: this.state.playerId
				})
				  .then((response)=>{
					  	this.setUserDeckList(response.data);
							this.updateAppDisplay("deck_selection");
					})
					.catch(error => {
					  console.log('Error fetching and parsing data', error);
					});
		}
	
	goAdminDashBoard = () => {
		axios({
			  method:'post',
			  url:'http://'+window.location.hostname+':8089/getUsers',
			  responseType:'json',
			  headers: {'Access-Control-Allow-Origin': "true"},
			})
			  .then((response)=>{
				  this.setUserList(response.data);
				  this.updateAppDisplay("admin_dashboard");
				  
				})
				.catch(error => {
				  console.log('Error fetching and parsing data', error);
				});
	}
	

	connectPlayer(idPlayer, namePlayer){
		this.setState({ playerId : idPlayer, 
						playerName : namePlayer, 
						appDisplay: "menu"});
		
		if("Admin" === this.state.playerName){
			this.goAdminDashBoard();
		}
	}

	setUserDeckList(userDeckList){
		this.setState({userDeckList: userDeckList});
	}
	
	setUserList(userList){
		this.setState({userList: userList});
	}

	setIdPlayer(idPlayer){
		this.setState({"playerId" : idPlayer});
	}
	endGameMode(){
		this.setState({"inGame" : false});
	}

	getGameFromQueue = (gameState)=>{
		console.log(this.state.appDisplay);
		this.setState({gameState: gameState, inGame: true})
	}

	updateAppDisplay = (displayMode) =>{
		this.setState({appDisplay: displayMode})
	}
}

export default App;
