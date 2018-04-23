$(document).ready(function(){
	main = new Main();
});
class Main{
	constructor(){
		let currentHost = window.location.hostname;
		let url = currentHost + ':9092';
		let body = $('body');
		let main = $('#cards');
		let socketInterface = new SocketInterface(url);
		//TODO: put this in the backend
		let scenarioList = [
			{
				"name":"Single-Shot",
				"about":"One shooter, one shot at one location. Can we determine the location of the shot? ",
				"value":"Scenario1"

			},
			{
				"name":"Shooter Shoots at multiple locations",
				"about":"One shooter shoots once at multiple locations. Model predicts time and direction the shooter is traveling.",
				"value":"Scenario2"
			},
			{
				"name":"Multi-Shot",
				"about":"Multiple shooters, shooting from multiple locations. Are the multiple shooter able to be identified? How many can the average person identify? ",
				"value":"Scenario3"
			},
			{
				"name":"Weapons",
				"about":"Multi-person-shot with identifiable sounds that represent different weapons. Can we correlate the sounds to the different weapons? How many can the average person identify? ",
				"value":"Scenario4"
			},
			{
				"name":"Single shot added communication",
				"about":"Single-shot with additional background military communications. How much does the communication interfere with locating the gunshot?",
				"value":"Scenario5"
			},
			{
				"name":"Similar Gunfire Locations",
				"about":"Two shots with very similar origins. Can we determine that these shots are very close? ",
				"value":"Scenario6"
			},
			{
				"name":"Two shots at the same time.",
				"about":"Twos shots happen at the same time at different locations. Can we identify that both have happened? Does this disorientate the user? ",
				"value":"Scenario7"
			},
			{
				"name":"Continuous shooting",
				"about":"A shooter is fire continuous from a location. Is it easy to identify that this is what is taking place given the type of sound being used? ",
				"value":"Scenario8"
			}
		];

		let scenarios = new ScenarioRenderer(main, scenarioList, socketInterface);
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
			let scenarioButton = new ScenarioButton(this.divToBindTo, this.scenarios[i].name, this.scenarios[i].about, i+1,()=>{
				this.socketInterface.sendNewScenario({"scenario" : this.scenarios[i].value});
			});
			this.buttonArray.push(scenarioButton);
		}
	}
}
class ScenarioButton{
	constructor(divToBindTo, text, about, index, callback){
		this.divToBindTo = divToBindTo;
		this.callback = callback;
		this.text = text;
		this.about = about;
		this.index = index;
		this.render();
	}
	render(){
		
		let wrapper = $(`<div class=" raised fluid card" style="
			    background-color: rgba(34, 36, 38, 0);
			">
			    <div class="content">
			      
			      <div class="header" style="
			    color: white;
			">${this.text}</div>
			      <div class="meta" style="
			    color: grey;
			">
			       Scenario ${this.index}
			      </div>
			      <div class="description" style="
			    color: lightgray;
			">
			        ${this.about}
			      </div>
			    </div>
			    
			  </div>`);
		let extraContent = $(`<div class="extra content">
			      <div class="ui buttons">
			        
			      </div>
			    </div>`);
		this.element = $(`<button class="ui inverted red  button">
			Simulate Scenario 
		</button>`);
		this.element.on("click", ()=>{
			this.callback();
		});
		extraContent.append(this.element);
		wrapper.append(extraContent);
		this.divToBindTo.append(wrapper);
	}
}
