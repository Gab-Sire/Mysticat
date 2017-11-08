import React, {Component} from 'react';
import '../styles/admin.css';

export default class AdminDashBoard extends Component{

	constructor(props){
		super(props);
		this.state={

		}
		console.log(this.props.adminName);
	}

	render(){
		return(
			<div id="containerAdmin">
				<h1>Tableau Administrateur</h1>
				<div id="contentAdmin">
					<p>Bonjour, {this.props.adminName}</p><br />
					{this.props.userList.map((user, index) => <p>{index} {user.username}</p>)}
					
				</div>
				<button id="btnDisconnect">D&eacute;connexion</button>
			</div>
		)
	}
}
