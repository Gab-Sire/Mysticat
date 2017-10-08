import React, {Component} from 'react';
import './styles/app.css';
import axios from 'axios';

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
		this.attemptConnection();
		
	}
	
	attemptConnection(){
		const data = {username: this.state.username, password: this.state.password}
		axios({
			  method:'get',
			  url:'http://localhost:8089/attemptConnection',
			  responseType:'json',
			  headers: {'Access-Control-Allow-Origin': "true"},
			  data: data
			})
			  .then((response)=>{
				  console.log(response.data);
				  if(response!==null){
					  this.setState({gameState: response.data});
					  this.forceUpdate();
					  //TODO rediriger vers main menu
				  }
				})
				.catch(error => {
				  console.log('Error fetching and parsing data', error);
				});
	}
	
	render(){
		return (<div id="loginForm">
		<h1>Login</h1>
	    <form>
	    	<p>Nom d'utilisateur: <input type="text" name="username" value={this.state.username} onChange={this.handleChangeUsername} required/></p>
	        <p>Mot de passe: <input type="password" name="password" value={this.state.password} onChange={this.handleChangePassword} required/></p>
	        <p><button type="button" onClick={this.handleSubmit} >Submit</button> <input type="reset" value="Reset" /></p>
	    </form>
	    </div>)
	}
}