import React, {Component} from 'react';
import './styles/app.css';
import axios from 'axios';

export default class Login extends Component{
	constructor(props){
		super(props);
		this.state={
			username:'',
			password:'',
			errorMessage:''
		}
		this.handleChangeUsername = this.handleChangeUsername.bind(this);
		this.handleChangePassword = this.handleChangePassword.bind(this);
		this.handleSubmit = this.handleSubmit.bind(this);
		this.handleClick = this.handleClick.bind(this);
	}
	
	handleSubmit(event){
		event.preventDefault();
	}
	
	handleChangeUsername(event){
		this.setState({username: event.target.value});
	}
	
	handleChangePassword(event){
		this.setState({password: event.target.value});
	}
	
	handleClick(event){
		console.log('Login form submitted');
		this.attemptConnection();
		if(this.state.gameState !=null){
			let id = this.state.gameState.USR_ID;
			this.props.handleConnectPlayer(id);
		}
		
	}
	connexionPlayer
	attemptConnection(){
		const data = {username: this.state.username, password: this.state.password}
		axios({
			  method:'post',
			  url:'http://localhost:8089/attemptConnection',
			  responseType:'json',
			  headers: {'Access-Control-Allow-Origin': "true"},
			  data: data
			})
			  .then((response)=>{
				  console.log(response.data);
				  if(response.data!==null){
					  this.setState({errorMessage: ""});
					  this.setState({gameState: response.data});
					  this.forceUpdate();
				  }else{
					  	this.setState({errorMessage: "Échec, le nom d'utilisateur et le mot de passe que vous avez entré ne correspondent pas."});
				  }
				})
				.catch(error => {
				  console.log('Error fetching and parsing data', error);
				});
	}
	
	render(){
		return (<div id="loginForm">
		<h1>Login</h1>
	    <form onSubmit={this.handleSubmit}>
	    	<p>Nom d'utilisateur: <input type="text" name="username" ref="username" onChange={this.handleChangeUsername} required/></p>
	        <p>Mot de passe: <input type="password" name="password" ref="password" onChange={this.handleChangePassword} required/></p>
	        <p><input type="Submit" onClick={this.handleClick} value="Submit" readOnly={true}/> <input type="reset" value="Reset"/></p>
	        <p className="errorMessage">{this.state.errorMessage}</p>
	        </form>
	    </div>)
	}
}