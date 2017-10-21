import React, {Component} from 'react';
import axios from 'axios';
import '../styles/app.css';
import Field from './Field.js';
import Graveyard from './Graveyard.js';
import Deck from './Deck.js';
import Hero from './Hero.js';
import Hand from './Hand.js';
import SurrenderScreenPopUp from './SurrenderScreenPopUp.js';
import EndGameScreen from './EndGameScreen.js';

const TIME_BETWEEN_POLLS = 1000;

let players;
let selfIndex;
let self;
let opponent;
let opponentIndex;

let selectedFieldCellIndex;
let cardIndex;
let selectedOpponentFieldCellIndex;

export default class Board extends Component{
	constructor(props){
		super(props);
		this.state ={
			playerId: null,
			isThinkingToGiveUp: false,
			hasLostGame : false,
			waitingForOpponentToEndTurn: false,
			actionList : [],
			cellsOfSummonedMinionsThisTurn : [false, false, false, false, false, false, false],
			indexesOfPlayedCardsThisTurn : [false, false, false, false, false, false, false, false, false, false],
			cellsOfAttackingMinion: [false, false, false, false, false, false, false],
			targetMinion:null,
			attackerSelected:null
		};
	}

	componentWillMount(){
		this.setState({gameState: this.props.gameState, isLoaded: true, playerId: this.props.playerId})
		//initializing players main attributes from the gamestate

		players = this.props.gameState.players;
		selfIndex = (players[0].playerId === this.props.playerId) ? 0 : 1;
		opponentIndex = (selfIndex === 0) ? 1 : 0;
		self = players[selfIndex];
		opponent = players[opponentIndex];

	}
	changeAttackingSelected(index){
		if(null === this.state.attackerSelected || index !== this.state.attackerSelected ){
			this.setState({attackerSelected : index});
		}else if(index === this.state.attackerSelected ){
			this.setState({attackerSelected : null});
		}
	}
	changeTargetSelected(index){
		if(null!==opponent.field[index]){
			this.setState({targetMinion : index});
			selectedOpponentFieldCellIndex = index;
			this.addAttackAction();
		}
	}

	render(){
		return(
			<div id="container">
				<div id="board">
					<div id="opponentHand" className="hand">
						<Hand players={players} playerIndex={opponentIndex} faceUp={false} />
					</div>
					<div id="opponentHandUnderLayer"></div>
					<Hero id="opponentHero" health={opponent.hero.health} heroName={opponent.heroPortrait}/>

					<div id="fieldContainer" className="fieldContainer">
						<Graveyard id="opponentGraveyard" size={opponent.graveyard.length} identity={"opponent"}/>
						<div id="opponentField" className="battleField">
							<Field players={players} playerIndex={opponentIndex} belongsToSelf={false}
							attackerSelected={this.state.attackerSelected} targetSelected={this.changeTargetSelected.bind(this)}/>
						</div>
						<Deck id="opponentDeck" size={opponent.deck.length}/>
					</div>

					<div id="selfFieldContainer" className="fieldContainer">
						<Graveyard id="selfGraveyard" size={self.graveyard.length} identity={"self"}/>
						<div id="selfField" className="battleField">
							<Field players={players} playerIndex={selfIndex} belongsToSelf={true} self={self} callBackSelectedMinion={this.retrieveMinionSelectedIndex}
							 cellsOfSummonedMinionsThisTurn={this.state.cellsOfSummonedMinionsThisTurn} cellsOfAttackingMinion={this.state.cellsOfAttackingMinion}
							changeAttackerSelected={this.changeAttackingSelected.bind(this)} attackerSelected={this.state.attackerSelected}/>
						</div>
						<Deck id="selfDeck" size={self.deck.length}/>
					</div>
					<div id="selfFieldUnderLayer"></div>

					<Hero id="selfHero" health={self.hero.health} mana={self.remainingMana} heroName={self.heroPortrait}/>

					<div id="selfHand" className="hand">
						<Hand players={players} playerIndex={selfIndex} faceUp={true} callBackSelectedCardIndex={this.retrieveCardSelectedIndex}
						cellsOfSummonedMinionsThisTurn ={this.state.cellsOfSummonedMinionsThisTurn} />
					</div>
					<button id="buttonEndTurn" onClick={this.sendActions.bind(this)}>Fin de tour</button>

					<SurrenderScreenPopUp status={this.state.isThinkingToGiveUp} giveUp={this.surrender.bind(this)} stayInTheGame={this.surrenderGameConfirmStateChange.bind(this)} />
					<EndGameScreen status={this.state.hasLostGame} backToMainMenu={this.backToMainMenu.bind(this)}/>

					<div id="menuGame"><p>Menu</p>
						<p id="listeMenuHidden"><button id="ButtonSurrender" onClick={this.surrenderGameConfirmStateChange.bind(this)}>Abandonner</button></p>
					</div>

					<div id="opponentUserName"><p>{opponent.name}</p></div>
					<div id="selfUserName"><p>{self.name}</p></div>
				</div>
			</div>
		);
	}

	addSummonAction = () => {
		let wereTheseCardsPlayedThisTurn = this.state.indexesOfPlayedCardsThisTurn;
		let areTheseCellsAboutToBeSummonedOn = this.state.cellsOfSummonedMinionsThisTurn;
		let wasThisCardAlreadyPlayedThisTurn = wereTheseCardsPlayedThisTurn[cardIndex];
		let wasAMinionAlreadyPlayedOnThisCell = areTheseCellsAboutToBeSummonedOn[selectedFieldCellIndex];
		let manaCost = self.hand[cardIndex].manaCost;
		let selfMana = self.remainingMana;

		if(false===wasThisCardAlreadyPlayedThisTurn && selfMana>=manaCost
			&& null===self.field[selectedFieldCellIndex] && false===wasAMinionAlreadyPlayedOnThisCell){
			console.log("Card played from hand: "+cardIndex+" on field cell: "+selectedFieldCellIndex);
			wereTheseCardsPlayedThisTurn[cardIndex] = true;
			areTheseCellsAboutToBeSummonedOn[selectedFieldCellIndex] = true;
			this.setState({indexesOfPlayedCardsThisTurn: wereTheseCardsPlayedThisTurn,
										cellsOfSummonedMinionsThisTurn : areTheseCellsAboutToBeSummonedOn});
			let actions = this.state.actionList;
			actions.push({ 	playerIndex : selfIndex,
							indexOfCardInHand : cardIndex,
							fieldCellWhereTheMinionIsBeingSummoned : selectedFieldCellIndex
						});
			this.setState({ actionList: actions })

			self.remainingMana = selfMana - manaCost;
			cardIndex = null;
		}
	}

	addAttackAction = () => {
		let cellsOfAttackingMinion = this.state.cellsOfAttackingMinion;
		let hasThisMinionAlreadyAttacked = !cellsOfAttackingMinion[this.state.attackerSelected];
		let isThereAMinionOnThisCell = (null!==self.field[selectedFieldCellIndex]);
		let isThereAnOpponentMinionThere = (null!==opponent.field[selectedOpponentFieldCellIndex]);

		console.log("And then idk:", selectedOpponentFieldCellIndex);

		if(isThereAMinionOnThisCell && isThereAnOpponentMinionThere && !hasThisMinionAlreadyAttacked){
			cellsOfAttackingMinion[selectedFieldCellIndex] = true;
			this.setState({cellsOfAttackingMinion: cellsOfAttackingMinion});
			let actions = this.state.actionList;
			actions.push({ 	playerIndex : selfIndex,
							attackingMinionIndex : this.state.attackerSelected,
							speed : self.field[this.state.attackerSelected].speed,
							targetIndex: selectedOpponentFieldCellIndex
						});
			this.setState({ actionList: actions })
		}
	}

	getInitialGameInstance(){
		axios({
			  method:'get',
			  url:'http://localhost:8089/getHardCodedGame',
			  responseType:'json',
			  headers: {'Access-Control-Allow-Origin': "true"}
			})
			  .then((response)=>{
				  this.setState({gameState: response.data, isLoaded: true});
				  this.forceUpdate();
				})
				.catch(error => {
				  console.log('Error fetching and parsing data', error);
				  setTimeout(()=>{
					  this.getInitialGameInstance();
				  }, TIME_BETWEEN_POLLS)
		});
	}

	//Periodically calls the back end to know if both players have posted their actions
	checkIfGameUpdated(){
		const data = this.state.playerId;
		axios({
			  method:'post',
			  url:'http://localhost:8089/checkIfGameUpdated',
			  responseType:'json',
			  headers: {'Access-Control-Allow-Origin': "true"},
			  data: data
			})
			  .then((response)=>{
				  console.log(response.data);
				  if(response.data!==null){
					  	this.setState({gameState: response.data,
							  actionList : [],
							  activeIndex : null,
								cellsOfSummonedMinionsThisTurn: [false, false, false, false, false, false, false],
							  indexesOfPlayedCardsThisTurn : [false, false, false, false, false, false, false, false, false, false],
								cellsOfAttackingMinion: [false, false, false, false, false, false, false],
								waitingForOpponentToEndTurn: false
					  	});
						players = this.state.gameState.players;
						self = this.state.gameState.players[selfIndex];
						opponent = this.state.gameState.players[opponentIndex];
						this.forceUpdate();
				  }
				  else{
						if(true===this.state.waitingForOpponentToEndTurn){
							setTimeout(()=>{
								this.checkIfGameUpdated();
							}, TIME_BETWEEN_POLLS)
						}
				  }
				})
				.catch(error => {
				  console.log('Error fetching and parsing data', error);
				});
	}

	sendActions(){
		const data = {
					gameId: this.state.gameState.gameId,
					playerActions: this.state.actionList,
					playerId: this.state.playerId
			};
		console.log("Sending actions: ", data);
		axios({
			  method:'post',
			  url:'http://localhost:8089/sendActions',
			  responseType:'json',
			  headers: {'Access-Control-Allow-Origin': "true"},
			  data: data
			})
			  .then((response)=>{
					this.setState({waitingForOpponentToEndTurn:true});
					cardIndex = null;
				  this.checkIfGameUpdated();
				})
				.catch(error => {
				  console.log('Error fetching and parsing data', error);
				});
	}

	retrieveCardSelectedIndex = (selectedIndex) => {
		if(selectedIndex===cardIndex){
			cardIndex = null;
		}
		else{
			cardIndex = selectedIndex;
		}
	}

	retrieveMinionSelectedIndex = (selectedIndex) =>{
		if(null!==cardIndex && undefined!==cardIndex){
			selectedFieldCellIndex = selectedIndex;
			this.addSummonAction();
		}
	}

	cancelActionSubmission(){
			this.setState({waitingForOpponentToEndTurn:false});
	}

	/* methods to surrender/quit the game */
	surrenderGameConfirmStateChange(){
		let status = this.state.isThinkingToGiveUp;
		this.setState({ isThinkingToGiveUp: !status });
	}

	surrender(){
		self.health = 0;
		this.surrenderGameConfirmStateChange();
		this.cancelActionSubmission();
		setTimeout(()=>{
			this.loseGame();
		}, TIME_BETWEEN_POLLS);
	}

	loseGame(){
		this.setState({ hasLostGame: true });
	}

	backToMainMenu(){
		this.props.endGame();
	}
}
