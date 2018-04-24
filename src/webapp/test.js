$(document).ready(function(){
	main = new Main();
});
class Main{
	constructor(){
		let currentHost = window.location.hostname;
		let url = currentHost + ':9092';
		let body = $('body');
		let socketInterface = new SocketInterface(url);
		let btn =$('#btnDiv');
		btn.append(`
		 
		    <div class="ui indeterminate active text loader" style="color:white">Connecting to socket</div>
`);
		socketInterface.addOnConnect(()=>{
			btn.empty();
			audioStreamer.addControls(btn, ()=>{
			let data = coordinateInterface.getCoordinates();
			console.log(data);
			socketInterface.sendCoordinates(data);
			});
		});
		let coordinateInterface = new CoordinateInterface();
		let intervalTime = 1000;
		let numSegmentsDelay = 10;
		let audioStreamer = new AudioStreamer(intervalTime, numSegmentsDelay);
		socketInterface.getSound((file)=>{
			audioStreamer.loadNew(file);
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
	addOnConnect(callback){
		this.socket.on('connect', function() {
		  callback();
		});
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
		this.audioCtx = new (window.AudioContext || window.webkitAudioContext)();
	}
	addControls(divToBindTo, startStreamCallback){
		this.audioStreamerControls = new AudioStreamerControls(divToBindTo);
		this.audioStreamerControls.addStartCallback(()=>{
			this.startStream(startStreamCallback);
		});
		this.audioStreamerControls.addStopCallback(()=>{
			this.stopStream();
		});
	}
	startStream(callback){
		console.log("starting Stream");
		this.on = true; 
		this.timer = setInterval(()=>{ 
			//console.log(this.buffer.length);

			callback();
		}, this.intervalTime);

	}
	stopStream(){
		this.on = false; 
		clearInterval(this.timer);
	}
	playSound(wavURL){
		var audio = new Audio(wavURL);
		//audio.play();
	}
	convertSoundToBlobURL(file){
		
		var arrayBuffer = new Uint8Array(file).buffer;
		
		var source = this.audioCtx.createBufferSource();
		var self = this;
		this.audioCtx.decodeAudioData(
                    arrayBuffer, 
                    function (buffer) {
                        source.buffer = buffer;
                        source.connect(self.audioCtx.destination);
                        source.loop = false;
                    });
        source.start(0);
    	//var blob = new Blob([arrayBuffer], {type : 'audio/wav'});
    	//var url = URL.createObjectURL(blob);
    	//console.log(url);

	}
	loadNew(file){
		if(this.on){
			console.log(file);
			var wavURL = this.convertSoundToBlobURL(file);
			//this.buffer.push(wavURL);
			//this.playSound(wavURL);
		}
	}
}
class AudioStreamerControls{
	constructor(divToBindTo){
		this.divToBindTo = divToBindTo;
		this.state = true;
		this.btn = $(`<div class="btn play">
				<span class="bar bar-1"></span>
				<span class="bar bar-2"></span>				
			</div>`);
		this.element = $(`
		<div class="btncontainer" style="margin:0px;">
			
		</div>`); 
		this.element.append(this.btn);
		this.divToBindTo.append(this.element);
		this.element.on("click", ()=>{
			if(this.state){
				$(this.btn).removeClass('play');
				$(this.btn).addClass('pause');
				this.state = false;
				this.startCallback();
			}else{
				$(this.btn).removeClass('pause');
				$(this.btn).addClass('play');
				this.state =true;
				this.stopCallback();
			}
		});
	}

	addStartCallback(callback){
		this.startCallback = callback;
		

	}
	addStopCallback(callback){
		this.stopCallback = callback;

	}
}
