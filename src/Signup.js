import React, {Component} from 'react';
import './styles/app.css';
import axios from 'axios';
import _ from 'lodash';

export default class Login extends Component{
	constructor(props){
		super(props);
		this.state={
			username:'', password:''
		}
		this.handleChangeUsername = this.handleChangeUsername.bind(this);
		this.handleChangePassword = this.handleChangePassword.bind(this);
		//this.handleSubmit = this.handleSubmit.bind(this);
		//this.handleChangePassword = this.attemptSignup.bind(this)
	}
	
	handleChangeUsername(event){
		this.setState({username: event.target.value});
	}
	
	handleChangePassword(event){
		this.setState({password: event.target.value});
	}
	
	handleSubmit(event){
		const attemptSignup= this.attemptSignup();
		event.preventDefault();
		console.log('Signup form submitted');
		//this.attemptSignup = this.attemptSignup.bind(this);
		attemptSignup;
	}
	
	attemptSignup(){
		const data = {username: this.refs.username, password: this.refs.password}
		axios({
			  method:'post',
			  url:'http://localhost:8089/signUp',
			  responseType:'json',
			  headers: {'Access-Control-Allow-Origin': "true"},
			  data: data
			})
			  .then((response)=>{
				  console.log(response.data);
				  if(response!==null){
					  this.forceUpdate();
					  //TODO rediriger vers main menu
				  }
				})
				.catch(error => {
				  console.log('Error fetching and parsing data', error);
				});
	}
	
	
	render(){
		return (<div id="SignUpForm">
		<h1>Sign up</h1>
	    <form onSubmit={this.handleSubmit.bind(this)}>
	    	<p>Nom d'utilisateur: <input type="text" name="username" ref="username"/></p>
	        <p>Mot de passe: <input type="password" name="password" ref="password"/></p>
	        <p><button type="button" onClick={this.handleSubmit}>Submit</button> <input type="reset" value="Reset" /></p>
	        
	    </form>
	    </div>)
	}
}