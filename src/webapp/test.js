$(document).ready(function(){
	main = new Main();
});
class Main{
	constructor(){
		let currentHost = window.location.hostname;
		let url = currentHost + ':9092';
		let body = $('body');
		let socketInterface = new SocketInterface(url);
		let coordinateInterface = new CoordinateInterface();
		let intervalTime = 1000;
		let numSegmentsDelay = 10;
		let audioStreamer = new AudioStreamer(intervalTime, numSegmentsDelay);
		socketInterface.getSound((file)=>{
			audioStreamer.loadNew(file);
		});
		audioStreamer.addControls(body, ()=>{
			let data = coordinateInterface.getCoordinates();
			console.log(data);
			socketInterface.sendCoordinates(data);
		});
	}
}
class CoordinateInterface{
	constructor(){
		this.alpha = 0;
		this.intervalTime = 100;
		var self = this;
		window.addEventListener("deviceorientation", function(data){
			self.newData(data, self)
		}, true);
	}
	getCoordinates(){
		return {
			"rotation": this.alpha
		};
	}
	newData(data, self){
		this.alpha = data.alpha;
	}

}
class SocketInterface{
	constructor(url){
		this.socket =  io.connect(url);
	}
	getConfig(callback){
		this.socket.emit('getConfig');
		this.socket.on('config', function(){
			callback();
		});
	}
	sendCoordinates(coordinates){
		this.socket.emit('updateCoordinates', coordinates);
	}
	getSound(callback){
		this.socket.on('sound', function(file) {
			//console.log("recieved");
			callback(file);
		});
	}
}
class AudioStreamer{
	constructor(intervalTime, numSegmentsDelay){
		this.numSegmentsDelay = numSegmentsDelay;
		this.intervalTime = intervalTime;
		this.on = false;
		this.buffer =[]; 
	}
	addControls(divToBindTo, startStreamCallback){
		this.audioStreamerControls = new AudioStreamerControls(divToBindTo);
		this.audioStreamerControls.addStartButton(()=>{
			this.startStream(startStreamCallback);
		});
		this.audioStreamerControls.addStopButton(()=>{
			this.stopStream();
		});
	}
	startStream(callback){
		console.log("starting Stream");
		this.on = true; 
		this.timer = setInterval(()=>{ 
			//console.log(this.buffer.length);
			if(this.buffer.length > this.numSegmentsDelay){
				this.playSound(this.buffer.shift());
			}
			callback();
		}, this.intervalTime);

	}
	stopStream(){
		this.on = false; 
		clearInterval(this.timer);
	}
	playSound(wavURL){
		var audio = new Audio(wavURL);
		audio.play();
	}
	convertSoundToBlobURL(file){
		var arrayBuffer = new Uint8Array(file).buffer;
    	var blob = new Blob([arrayBuffer], {type : 'audio/wav'});
    	var url = URL.createObjectURL(blob);
    	//console.log(url);
		return url;
	}
	loadNew(file){
		if(this.on){
			console.log(file);
			var wavURL = this.convertSoundToBlobURL(file);
			this.buffer.push(wavURL);
			//this.playSound(wavURL);
		}
	}
}
class AudioStreamerControls{
	constructor(divToBindTo){
		this.divToBindTo = divToBindTo;
	}
	addStartButton(callback){
		this.startButton = $(`
			<button class="ui button">
			Start
		</button>`);
		this.startButton.on("click", function(){
			callback();
		});
		this.divToBindTo.append(this.startButton);
	}
	addStopButton(callback){
		this.stopButton = $(`
			<button class="ui button">
			Stop
		</button>`);
		this.stopButton.on("click", function(){
			callback();
		});
		this.divToBindTo.append(this.stopButton);
	}
}
