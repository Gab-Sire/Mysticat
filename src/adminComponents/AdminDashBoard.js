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
					<UserManagementList userList={this.props.userList} />
				</div>
				<button id="btnDisconnect">D&eacute;connexion</button>
			</div>
		)
	}
}
