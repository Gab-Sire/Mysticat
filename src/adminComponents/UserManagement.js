import React, {Component} from 'react';
import '../styles/admin.css';
import axios from 'axios';

export default class UserManagement extends Component{

	constructor(props){
		super(props);
		
	}

	render(){
		let connected = "isNotConnected";
		if(true === this.props.connected){
			connected = "isConnected";
		}
		if("Admin" !== this.props.username){
			return(
				<div className="userLine">
				<p className={connected}>{this.props.index} {this.props.username}</p><button className="btnDeconnexionUser" disabled={!this.props.connected} onClick={this.disconnectPlayerById.bind(this, this.props.id)}>D&eacute;connexion</button>
				</div>
			)
		}
		else{
			return( <div></div>)
		}
	}
	
	disconnectPlayerById(id){
		this.props.disconnectPlayerById(id);
		this.props.connected = false;
	}
}