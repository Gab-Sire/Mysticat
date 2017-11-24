import React, {Component} from 'react';
import axios from 'axios';
import '../styles/app.css';
import Beforeunload from 'react-beforeunload';
import * as Constantes from '../Constantes.js';

export default class CardTile extends Component{
	constructor(props){
		super(props);
		this.state={};
	}

	render(){
      let heros = this.props.heros.map(function(hero, index){
    	  return (
					 <div> 
              <a className={"hero "+hero}></a>
              {(0===((index+1)%5) ) ? <br/>:null}
           </div>
        )
		 }, this)
		 
		 return (<div className='MainMenu'>
		 			<Beforeunload onBeforeunload={() => {this.deconnexion(); return "Are you sure?"}}/>
		 			{heros}
		 		</div>);
		
	}
	
}
