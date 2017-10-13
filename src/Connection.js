import React, {Component} from 'react';
import './styles/app.css';
import Login from "./Login.js";
import Signup from "./Signup.js";

export default class Connection extends Component{
	constructor(props){
		super(props);
		this.state={
			signupMode:false
		}
		this.connectPlayer =this.connectPlayer.bind(this);
	}
	render(){
		if(this.props.signupMode){
			return <Signup handleConnectPlayer="{this.connectPlayer}" />
		}else{
			return <Login handleConnectPlayer="{this.connectPlayer}" />
		}
	}
	connectPlayer(playerId){
		super.props.connectPlayer(playerId);
	}
	
}