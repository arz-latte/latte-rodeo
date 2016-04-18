require([ "app/common", "underscore"], function(common, _){
	console.log("this is the index file");
	
	common.loadDocument("app/index.template", function(f){
		var div=document.createElement("div");
		var t=_.template(f);
		div.innerHTML=t({"greeting":"welt"});
		document.body.appendChild(div);
	});
	
});