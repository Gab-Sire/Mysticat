import React, {Component} from 'react';
import UserManagementList from './UserManagementList.js';
import '../styles/admin.css';

const TIME_BETWEEN_AXIOS_CALLS = 5000;
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
				<button id="btnDisconnect"
				onClick={(event)=>{
						setTimeout(()=>{
							this.props.disconnectPlayerById(this.props.adminId);
						}, TIME_BETWEEN_AXIOS_CALLS)
					}
				}
				>D&eacute;connexion</button>
			</div>
		)
	}
}
