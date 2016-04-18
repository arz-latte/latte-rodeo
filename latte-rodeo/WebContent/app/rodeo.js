
(function(){
	
	function endsWith(str, suffix) {
		return str.indexOf(suffix, str.length - suffix.length) !== -1;
	}

	function getContextRoot() {
		var thisScriptName = "/app/rodeo.js";
		var scriptTags = document.getElementsByTagName("script");
		for (var int = 0; int < scriptTags.length; int++) {
			var scriptURL = scriptTags[int].src;
			if (endsWith(scriptURL, thisScriptName)) {
				return scriptURL.substring(0, scriptURL.length - thisScriptName.length);
			}
		}
		return "/";
	}
	
	var contextRoot = getContextRoot();

	requirejs.config({
	  baseUrl: contextRoot  + '/js',
	  
	  urlArgs: "bust=" + (new Date()).getTime(),
	  
	  paths: {
	      "app": contextRoot  + '/app'
	  },
	  
	  shim: {
	  	"underscore" : {
	  		exports : '_'
	  	}
	  }
	
	});
	
	define("app/rodeo",["underscore"], function(_) {

		_.templateSettings = {
				  evaluate:    /\{\{#([\s\S]+?)\}\}/g,            // {{# console.log("blah") }}
				  interpolate: /\{\{[^#\{]([\s\S]+?)[^\}]\}\}/g,  // {{ title }}
				  escape:      /\{\{\{([\s\S]+?)\}\}\}/g,         // {{{ title }}}
		};
		
		return {
			
		  contextRoot : contextRoot,
		  
		  template : function (path){
			  
		  },

		  loadDocument: function(path, fun) {
		  	var documentURL=this.contextRoot + "/" + path;
		  	var xhr=new XMLHttpRequest();
		  	xhr.open('GET', documentURL, true );
		  	xhr.onload = function(e){
		  		if(xhr.readyState===4){
		  			if(xhr.status===200){
		  				fun(xhr.responseText);
		  			}else{
		  				console.error(xhr.statusText);
		  			}
		  		}
		  	}
		  	xhr.onerror = function(e){
		  		console.error(xhr.statusText);
		  	}
		  	xhr.send(null);
		  },

		  readTemplate : function(templateURL) {
			  console.log("read template:" + this.contextRoot + "/" + templateURL);
		  },
		}

	});	

})();


