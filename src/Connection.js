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
	}
	render(){
		if(this.props.signupMode){
			return <Signup />
		}else{
			return <Login />
		}
	}
	
}