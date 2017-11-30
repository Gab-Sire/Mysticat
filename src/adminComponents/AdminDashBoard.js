import React, {Component} from 'react';
import UserManagementList from './UserManagementList.js';
import '../styles/admin.css';
import Beforeunload from 'react-beforeunload';


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
				<Beforeunload onBeforeunload={() => {	this.props.disconnectPlayerById(this.props.adminId); return "Are you sure?"}}/>
				<h1>Tableau Administrateur</h1>
				<div id="contentAdmin">
					<aside className='gauche'>
						<p id="salutationAdmin">Bonjour, {this.props.adminName}</p>
						<UserManagementList userList={this.props.userList} disconnectPlayerById={this.props.disconnectPlayerById}/>
						<button id="btnDisconnect" className="singleLineAdmin"
							onClick={(event)=>{
									setTimeout(()=>{
										this.props.disconnectPlayerById(this.props.adminId);
									}, TIME_BETWEEN_AXIOS_CALLS)
								}
							}
							>D&eacute;connexion</button>
					</aside>
					<aside className='droit'>
						<div id="radar"></div>
						<button id="btnDisconnect"
							onClick={(event)=>{
									setTimeout(()=>{
										this.props.disconnectPlayerById(this.props.adminId);
									}, TIME_BETWEEN_AXIOS_CALLS)
								}
							}
							>D&eacute;connexion</button>
					</aside>
				</div>
			</div>
		)
	}
}
