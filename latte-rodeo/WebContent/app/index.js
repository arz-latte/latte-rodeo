require([ "app/rodeo", "underscore"], function(Rodeo, _){
	
	console.log("this is the index file");
	
	var data={
			greeting : "welt " + (new Date()).getTime(),
			list : ["a", "b", "c"]
	};
	
	Rodeo.loadDocument("app/index.template", function(f){
		var div=document.createElement("div");
		var t=_.template(f);
		div.innerHTML=t(data);
		document.body.appendChild(div);
	});
	
});