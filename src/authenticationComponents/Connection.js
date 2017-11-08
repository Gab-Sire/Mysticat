import React, {Component} from 'react';
import '../styles/app.css';
import Login from "./Login.js";
import Signup from "./Signup.js";

export default class Connection extends Component{
	constructor(props){
		super(props);
		this.state={
			signupMode:false
		}
		this.connectPlayer =this.connectPlayer.bind(this);
		this.changeSignUpMode=this.changeSignUpMode.bind(this);
	}
	render(){
		if(this.state.signupMode){
			return <Signup connectPlayer={this.connectPlayer} changeSignUpMode={this.changeSignUpMode}/>
		}else{
			return <Login connectPlayer={this.connectPlayer} changeSignUpMode={this.changeSignUpMode}/>
		}
	}

	connectPlayer(playerId, playerName){
		this.props.connectPlayer(playerId, playerName);
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
}
