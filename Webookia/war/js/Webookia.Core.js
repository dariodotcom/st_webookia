const
DEBUG_MODE = true;
const
LOG_PREFIX = "[WEBOOKIA LOG] ";

var $doc = document.getElementById.bind(document);

(function __WEBOOKIA__() {

	// Main object declaration
	var Webookia = window.Webookia = {};

	var Utility = Webookia.Utility = {
		log : function _log(message) {
			if (DEBUG_MODE) {
				(window.console ? window.console.log : window.alert).call(
						window, (LOG_PREFIX + message));
			}
		}
	}

	// Initializer
	var initializers = new Array();
	
	var Initializer = Webookia.Initializer = {
		plugin: function _plugin(initializer){
			initializers.push(initializer);
		}
	}
	
	$(function(){
		$(initializers).each(function(index, f){
			f.call(window);
		})
	})

	// Concrete UI
	function toCoords(elem){
		var coords = elem.text();
		if(coords == ""){
			return null;
		}
		
	    var split = coords.split(",");
	    return new google.maps.LatLng(parseFloat(split[0]), parseFloat(split[1]));
	}
	
	var ConcreteUI = {
		map: null,
		concretes: null,
		popup: null,
		
		loadMap: function _loadMap(){
			var mapOptions = {
				zoom : 8,
				center : toCoords($('.selfCoords')),
				mapTypeId : google.maps.MapTypeId.ROADMAP
			};
			
			this.map = new google.maps.Map($doc('map'), mapOptions);
		},
		
		bounceMarker: function _bounceMarker(marker){
		    var position = marker.getPosition();
		    this.map.panTo(position);
		    marker.setAnimation(google.maps.Animation.BOUNCE);
		    setTimeout(function(){marker.setAnimation(null)}, 2000);
		},
		
		loadPopup: function _loadPopup(){
			var popupElem = $(".concretePopup");
			popupElem.hide();

			this.popup = {
			    setName: function(name){
			        popupElem.children(".text").children(".name").text(name);
			    },
			    show: function(){
			        popupElem.show();
			    },
			    hide: function(){
			        popupElem.hide();
			    },
			    top: function(top){
			        popupElem.css("top", top);
			    },
			}
			
			var buttons = popupElem.children('.buttons');
			var no = buttons.children('.no');
			no.click(this.popup.hide);
		},
		
		loadConcretes: function loadConcretes(){
			this.concretes = $('#concreteList > .concrete');
			Utility.log("lol");		
			
			var ui = this;
			this.concretes.each(function(index, elem){
				var concrete = $(elem);
				var position = toCoords(concrete.children('.concreteDetail').children('.coords'));
				
			    var marker = new google.maps.Marker({
			    	position: position,
			    	map: ui.map
			    });

			    concrete.click(ui.selectConcrete.bind(ui, concrete, marker));
			});
		},
		
		selectConcrete: function _selectConcrete(concrete, marker){
		    this.popup.hide();
		    
		    // Set top.
		    
		    var name = concrete.children('.concreteDetail').children('.name').text();
		    this.popup.setName(name);
		    
		    this.popup.show();
		    this.bounceMarker(marker);
		},
		
		init: function _init(){
			this.loadMap();
			this.loadPopup();
			this.loadConcretes();
		}	
	}
	
	Initializer.plugin(function __loadConcreteUI__(){
		ConcreteUI.init();
	});
	
})();