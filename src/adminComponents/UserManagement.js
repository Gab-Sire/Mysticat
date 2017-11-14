import React, {Component} from 'react';
import '../styles/admin.css';

export default class UserManagement extends Component{

	constructor(props){
		super(props);
		this.state={
			connected : false
		}
	}

	componentWillMount(){
		this.setState({ connected: this.props.connected});
	}

	render(){
		if("Admin" !== this.props.username){
			return(
				<div className="userLine">
				<p className={(this.props.connected) ? "isConnected" : "isNotConnected"}>{this.props.index} {this.props.username}</p><button className="btnDeconnexionUser"
				disabled={false===this.props.connected}
				onClick={this.disconnectPlayerById.bind(this, this.props.id)}>

					D&eacute;connexion
				</button>
				</div>
			)
		}
		else{
			return( <div></div>)
		}
	}

	disconnectPlayerById(id){
		this.props.disconnectPlayerById(id);
		this.setState({ connected: false});
	}
}
