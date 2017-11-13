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
		
		return(
			<div className="userLine">
			<p className={connected}>{this.props.index} {this.props.username}</p><button className="btnDeconnexionUser" onClick={this.disconnectPlayerById.bind(this, this.props.id)}>D&eacute;connexion</button>
			</div>
		)
	}
	
	disconnectPlayerById(id){
		this.props.disconnectPlayerById(id);
	}
}