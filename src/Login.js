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
	}
	
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
				  console.log(response);
				  if(response.data!==null){
					  this.setState({errorMessage: ""});
					  this.props.connectPlayer(response.data);
					
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
		
	    <form onSubmit={this.handleSubmit}>
	    <h1>Login</h1>
	    	<p><label htmlFor="username">Nom d'utilisateur: </label><input type="text" name="username" id="username" ref="username" onChange={this.handleChangeUsername} required/></p>
	        <p><label htmlFor="password">Mot de passe: </label><input type="password" name="password" id="password" ref="password" onChange={this.handleChangePassword} required/></p>
	        <p><input type="Submit" onClick={this.handleClick} value="Submit" readOnly={true}/> <input type="reset" value="Reset"/></p>
	        <p className="errorMessage">{this.state.errorMessage}</p>
	        </form>
	        
	    </div>)
	}
}