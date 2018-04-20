$(document).ready(function(){
	main = new Main();
});
class Main{
	constructor(){
		let currentHost = window.location.hostname;
		let url = currentHost + ':9092';
		let body = $('body');
		let socketInterface = new SocketInterface(url);
		//TODO: put this in the backend
		let scenarioList = [
			{
				"name":"Scenario 1",
				"value":"Scenario1"
			},
			{
				"name":"Scenario 2",
				"value":"Scenario2"
			},
			{
				"name":"Scenario 3",
				"value":"Scenario3"
			},
			{
				"name":"Scenario 4",
				"value":"Scenario4"
			},
			{
				"name":"Scenario 5",
				"value":"Scenario5"
			},
			{
				"name":"Scenario 6",
				"value":"Scenario6"
			},
			{
				"name":"Scenario 7",
				"value":"Scenario7"
			},
			{
				"name":"Scenario 8",
				"value":"Scenario8"
			},
			{
				"name":"Scenario 9",
				"value":"Scenario9"
			}
		];
		let scenarios = new ScenarioRenderer(body, scenarioList, socketInterface);
	}
}
class SocketInterface{
	constructor(url){

		this.socket =  io.connect(url);
		console.log(this.socket);
	}
	getScenarioList(callback){
		this.socket.emit('getConfig');
		this.socket.on('config', function(){
			callback();
		});
	}
	sendNewScenario(scenario){
		//console.log("JUST GONNA SEND IT");
		//console.log(scenario);
		//console.log(this.socket);
		this.socket.emit('updateScenario', scenario);
	}

}
class ScenarioRenderer{
	constructor(divToBindTo, list, socketInterface){
		this.divToBindTo = divToBindTo;
		this.scenarios = list;
		this.socketInterface = socketInterface;
		this.buttonArray=[];
		this.render();
	}
	render(){
		for(let i =0; i< this.scenarios.length; i++){
			let scenarioButton = new ScenarioButton(this.divToBindTo, this.scenarios[i].name, ()=>{
				this.socketInterface.sendNewScenario({"scenario" : this.scenarios[i].value});
			});
			this.buttonArray.push(scenarioButton);
		}
	}
}
class ScenarioButton{
	constructor(divToBindTo, text, callback){
		this.divToBindTo = divToBindTo;
		this.callback = callback;
		this.text = text;
		this.render();
	}
	render(){
		this.element = $(`<button class="ui button">
			${this.text}
		</button>`);
		this.element.on("click", ()=>{
			this.callback();
		});
		this.divToBindTo.append(this.element);
	}
}
