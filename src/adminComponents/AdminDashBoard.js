import React, {Component} from 'react';
import UserManagementList from './UserManagementList.js';
import '../styles/admin.css';

export default class AdminDashBoard extends Component{

	constructor(props){
		super(props);
		this.state={

		}
	}

	render(){
		return(
			<div id="containerAdmin">
				<h1>Tableau Administrateur</h1>
				<div id="contentAdmin">
					<p>Bonjour, {this.props.adminName}</p><br />
					<img src="../img/backgroundImg/radar.gif" alt="radar"/>
					<UserManagementList userList={this.props.userList} disconnectPlayerById={this.props.disconnectPlayerById}/>
				</div>
				<button id="btnDisconnect" onClick={this.disconnectPlayerById.bind(this, this.props.adminId)}>D&eacute;connexion</button>
			</div>
		)
	}
	
	disconnectPlayerById(id){
		setTimeout(() => {this.props.disconnectPlayerById(id)}, 3000);
	}
}
