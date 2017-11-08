import React, {Component} from 'react';
import '../styles/admin.css';

export default class UserManagement extends Component{

	constructor(props){
		super(props);
		this.state={

		}
	}

	render(){
		return(
			<div id="containerUserList">
				{this.props.userList.map((user, index) => <p>{index} {user.username}</p>)}	
			</div>
		)
	}
}