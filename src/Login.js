import React, {Component} from 'react';
import axios from 'axios';
import './app.css';

export default class Login extends Component{
	constructor(props){
		super(props);
		this.state={
			username:'',
			password:''
		}
		this.handleChangeUsername = this.handleChangeUsername.bind(this);
		this.handleChangePassword = this.handleChangePassword.bind(this);
		this.handleSubmit = this.handleSubmit.bind(this);
	}
	
	handleChangeUsername(event){
		this.setState({username: event.target.value});
	}
	
	handleChangePassword(event){
		this.setState({password: event.target.value});
	}
	
	handleSubmit(event){
		console.log('Login form submitted');
		event.preventDefault();
	}
	
	render(){
		return (<div id="loginForm">
		<h1>Login</h1>
	    <form action="http://localhost:8089/attemptConnection" method="post" onSubmit={this.handleSubmit}>
	    	<p>Nom d'utilisateur: <input type="text" name="username" value={this.state.username} onChange={this.handleChangeUsername} required/></p>
	        <p>Mot de passe: <input type="password" name="password" value={this.state.password} onChange={this.handleChangePassword} required/></p>
	        <p><input type="submit" value="Submit" /> <input type="reset" value="Reset" /></p>
	    </form>
	    </div>)
	}
}