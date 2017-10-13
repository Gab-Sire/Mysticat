import React, {Component} from 'react';
import axios from 'axios';
import './styles/app.css';
import Board from './boardComponents/Board.js';
import Login from "./Login.js";
import Signup from "./Signup.js";
import Connection from "./Connection.js";
import MainMenu from './menuComponents/MainMenu.js';
import LoadingScreen from './boardComponents/LoadingScreen.js';

class App extends Component{
	constructor(props){
		super(props);
		this.state ={
			isServerAvailable: false,
			inGame: false,
			playerId: null,
			signupMode:false,
			tagLoginSignUp:"Sign Up"

		};
	}
	
	changeSignUpMode(){
		let statut = this.state.signupMode;
		this.setState({ signupMode: !statut});
		if(this.state.signupMode){
			this.setState({ tagLoginSignUp: "Sign Up"});
		}else{
			this.setState({ tagLoginSignUp: "Login"});
		}
	}
	
	render(){
		if(true===this.state.isServerAvailable){
			if(false===this.state.inGame && null !==this.state.playerId){
				return <MainMenu playerId={this.state.playerId}/>
			}else if(true===this.state.inGame){
				return(
					<Board />
				);
			}
			else{
				return (<div><Connection signupMode={this.state.signupMode} connectPlayer={this.setIdPlayer.bind(this)}/>
				<div className='linkConnection'>Aller à la page: 
					<button onClick={this.changeSignUpMode.bind(this)}>{this.state.tagLoginSignUp}</button>
				</div>
						</div>)
				
			}	
		}
		else return (<LoadingScreen />)
		
	}
	setIdPlayer(idPlayer){
		this.setState({"playerId" : idPlayer});
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
				  this.forceUpdate();
				  console.log(response.data);
				})
				.catch(error => {
				  console.log('Error fetching and parsing data', error);
				  setTimeout(()=>{
							  this.checkServerAvailability();
						  }, 5000)
				});
	}
	componentWillMount(){
		this.checkServerAvailability();
	}
	
}


export default App;