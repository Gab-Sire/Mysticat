import React, { Component } from 'react';
/**
 * Pop up menu si on a besoin plus tard
 */
export default class SurrenderScreenPopUp extends Component {

  constructor(props) {
    super(props);
    this.state = {
    		deck:null
    }
  }
  componentDidMount(){
	  this.getDeck();
  }
  getDeck(){
		axios({
			  method:'get',
			  url:'http://'+window.location.hostname+':8089/selectOneDeck/'+this.props.playerId+'/'+this.props.idDeck,
			  responseType:'json',
			  headers: {'Access-Control-Allow-Origin': "true"}
			})
			  .then((response)=>{
				  this.setState({deck: response.data});
			
				})
				.catch(error => {
				  console.log('Error fetching and parsing data', error);
				  setTimeout(()=>{
					 
				  }, TIME_BETWEEN_AXIOS_CALLS)
		});
	}
  
  render() {
	  if(deck !== null){
		  return (<div></div>);  
	  }else{
		  return (<div>En attente du serveur</div>);
	  }
  }
}
