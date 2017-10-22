import React, {Component} from 'react';
import './styles/app.css';
import axios from 'axios';
import { Tooltip } from 'reactstrap';

export default class Login extends Component{
	constructor(props){
		super(props);
		this.state={
			username:'',
			password:'',
			errorMessage: '',
			toolTipOpen: false
		}
		this.handleChangeUsername = this.handleChangeUsername.bind(this);
		this.handleChangePassword = this.handleChangePassword.bind(this);
		this.handleSubmit = this.handleSubmit.bind(this);
		this.handleClick = this.handleClick.bind(this);
		this.toggle = this.toggle.bind(this);
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
		console.log('Signup form submitted');
		this.attemptSignup();
	}

	attemptSignup(){
		const data = {username: this.state.username, password: this.state.password}
		axios({
			  method:'post',
			  url:'http://'+window.location.hostname+':8089/signUp',
			  responseType:'json',
			  headers: {'Access-Control-Allow-Origin': "true"},
			  data: data
			})
			  .then((response)=>{
				  console.log(response.data);
				  if(response.data!==null){
					  this.props.connectPlayer(response.data);
					  this.setState({errorMessage: ""});
				  }
				  else{
					  	this.setState({errorMessage: "Échec, veuillez vérifier le format de votre nom d'utilisateur et mot de passe."});
					  }
				})
				.catch(error => {
				  console.log('Error fetching and parsing data', error);
				});
	}

	checkIfUsernameTaken(){
		console.log("entering");
		const data = {username: this.state.username}
		axios({
			  method:'post',
			  url:'http://'+window.location.hostname+':8089/usernameAvailability',
			  responseType:'json',
			  headers: {'Access-Control-Allow-Origin': "true"},
			  data: data
			})
			  .then((response)=>{
				  console.log("User avail: ",response)
				  this.forceUpdate();
				})
				.catch(error => {
				  console.log('Error fetching and parsing data', error);
				});
	}
	changeSignUpMode(){
		this.props.changeSignUpMode();
		}

	toggle() {
		this.setState({toolTipOpen: !this.state.toolTipOpen})
	}

	render(){
		const USERNAME_MIN_LENGTH = 5;
		const USERNAME_MAX_LENGTH = 30;
		const PASSWORD_MIN_LENGTH = 5;
		const PASSWORD_MAX_LENGTH = 100;
		return (<div id="loginForm" className="container">
	        	<div className="boiteConnexion card-container">
	        		<img alt="profile-img" id="profile-img" className="profile-img-card" src="//ssl.gstatic.com/accounts/ui/avatar_2x.png" />
	        		<p id="profile-name" className="profile-name-card"></p>
	        		<h3 id="titlePageConnexion"> Signup </h3><a id="tooltipLink">?</a>

	                <Tooltip id= "tooltip" placement="top" isOpen={this.state.toolTipOpen} autohide={false} target="tooltipLink" toggle={this.toggle}>
	     	             <h5>Votre nom d'utilisateur doit être unique et doit comprendre:</h5>
	                     <ul>
	                      	<li>Entre {USERNAME_MIN_LENGTH} et {USERNAME_MAX_LENGTH} caracteres inclusivement</li>
	                  		<li>Au moins 1 chiffre</li>
	                  		<li>Au moins 1 lettre majuscule</li>
	                  	</ul>
	                	<h5>Votre mot de passe doit comprendre:</h5>
	                	<ul>
	                  		<li>Entre {PASSWORD_MIN_LENGTH} et {PASSWORD_MAX_LENGTH} caracteres inclusivement</li>
	                  		<li>Au moins 1 chiffre</li>
	                  		<li>Au moins 1 lettre minuscule</li>
	                  		<li>Au moins 1 lettre majuscule</li>
	                  	</ul>
	                </Tooltip>
	        		<form className="form-signin" onSubmit={this.handleSubmit}>
	            		<span id="reauth-email" className="reauth-email"></span>
	            		<input className="form-control" placeholder="Nom d'utilisateur" type="text" name="username" id="username" ref="username" onChange={this.handleChangeUsername} required autoFocus/>
	            		<input className="form-control" placeholder="Mot de passe" type="password" name="password" id="password" ref="password" onChange={this.handleChangePassword} required/>
	                    <input className="btn btn-lg btn-primary btn-block btn-signin" type="Submit" onClick={this.handleClick} value="Signup" readOnly={true} /> <input className="btn btn-lg btn-primary btn-block btn-signin" type="reset" value="Reset"/><br />
	                    <a className="changeConnexionMode" onClick={this.changeSignUpMode.bind(this)} >Cliquez ici pour se connecter</a>
	                    <p className="errorMessage">{this.state.errorMessage}</p>
	            	</form>
	            </div>

        <div className="expectedFormat">
        	<div id="usernameFormat">
				<h4>Votre nom d'utilisateur doit être unique et doit comprendre:</h4>
				<ul>
					<li>Entre {USERNAME_MIN_LENGTH} et {USERNAME_MAX_LENGTH} caracteres inclusivement</li>
					<li>Au moins 1 chiffre</li>
					<li>Au moins 1 lettre minuscule</li>
					<li>Au moins 1 lettre majuscule</li>
				</ul>
			</div>
			<div id="passwordFormat">
				<h4>Votre mot de passe doit comprendre:</h4>
				<ul>
					<li>Entre {PASSWORD_MIN_LENGTH} et {PASSWORD_MAX_LENGTH} caracteres inclusivement</li>
					<li>Au moins 1 chiffre</li>
					<li>Au moins 1 lettre minuscule</li>
					<li>Au moins 1 lettre majuscule</li>
				</ul>
			</div>
        </div>
	    </div>)
	}
}
