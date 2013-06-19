const
DEBUG_MODE = true;
const
LOG_PREFIX = "[WEBOOKIA LOG] ";
const
API_URL = "/api";

var $doc = document.getElementById.bind(document);

(function __WEBOOKIA__() {

	// Main object declaration
	var Webookia = window.Webookia = {};

	var logFn = (console ? console.log.bind(console) : alert.bind(window));
	var Utility = Webookia.Utility = {
		log : function _log(message) {
			if (DEBUG_MODE) {
				logFn(LOG_PREFIX + message);
			}
		}
	}

	// Initializer
	var initializers = new Array();

	var Initializer = Webookia.Initializer = {
		plugin : function _plugin(initializer) {
			initializers.push(initializer);
		}
	}

	$(function() {
		$(initializers).each(function(index, f) {
			f.call(window);
		})
	})

	/*
	 * ============ API ACCESS ============
	 */
	var Api = Webookia.Api = {}

	Api.post = function(url, json, onSuccess, onError) {
		$.ajax({
			type : "POST",
			beforeSend : function(req) {
				req.setRequestHeader("Content-Type", "application/json");
			},
			url : API_URL + url,
			data : JSON.stringify(json),
		}).done(onSuccess).fail(onError);
	}

	// Loans
	var Loan = Webookia.Loan = function(id) {
		this.id = id;
	}

	Loan.create = function(bookId, onsuccess, onerror) {
		var json = {
			bookId : bookId
		};
		var url = "/loans/new";
		Api.post(url, json, onsuccess, onerror);
	}

	// Books
	var Book = Webookia.Book = function(id) {
		this.id = id;
	}

	Book.prototype = {
		getId : function _getId() {
			return this.id;
		},
		privacy : function _privacy(privacy, onSuccess, onError) {
			var json = {
				privacy : privacy
			};
			var url = "/books/book/" + this.id;
			Api.post(url, json, onSuccess, onError);
		},
		status : function _status(status, onSuccess, onError) {
			var json = {
				status : status
			};
			var url = "books/book/" + this.id;
			Api.post(url, json, onSuccess, onError);
		},
		review : function _review(text, mark, onSuccess, onError) {
			var json = {
				mark : mark,
				text : text
			};
			var url = "books/book/" + this.id + "/review";
			Api.post(url, json, onSuccess, onError);
		},
		comment : function _comment() {

		}
	}

	// Error
	var errorContainer;
	Webookia.Initializer.plugin(function() {
		errorContainer = $doc("errorContainer")
				|| ($("<div id=\"errorContainer\"></div>")
						.prependTo($("#content")));

	});

	var Error = Webookia.Error = {
		append : function _append(errorMsg) {
			var error = $("<div class=\"error clearfix\"></div>");
			$("<div class=\"errorText\"></div>").text(errorMsg).appendTo(error);
			var close = $("<div class=\"close\"></div>").text("x");
			close.appendTo(error);
			close.click(function() {
				error.remove();
			});
			error.appendTo(errorContainer);
		}

	}

	// Concrete UI
	function toCoords(elem) {
		var coords = elem.text();
		if (coords == "") {
			return null;
		}

		var split = coords.split(",");
		return new google.maps.LatLng(parseFloat(split[0]),
				parseFloat(split[1]));
	}

	var ConcreteUI = {
		map : null,
		concretes : null,
		popup : null,

		loadMap : function _loadMap() {
			var mapOptions = {
				zoom : 8,
				center : toCoords($('.selfCoords')),
				mapTypeId : google.maps.MapTypeId.ROADMAP
			};

			this.map = new google.maps.Map($doc('map'), mapOptions);
		},

		bounceMarker : function _bounceMarker(marker) {
			var position = marker.getPosition();
			this.map.panTo(position);
			marker.setAnimation(google.maps.Animation.BOUNCE);
			setTimeout(function() {
				marker.setAnimation(null)
			}, 2000);
		},

		loadPopup : function _loadPopup() {
			var popupElem = $(".concretePopup");
			popupElem.hide();

			var buttons = popupElem.children('.buttons');
			var no = buttons.children('.no');
			var yes = buttons.children('.yes');

			this.popup = {
				setName : function(name) {
					popupElem.children(".text").children(".name").text(name);
				},
				show : function() {
					popupElem.show();
				},
				hide : function() {
					popupElem.hide();
				},
				top : function(top) {
					popupElem.css("top", top + "px");
				},
				yes : function(listener) {
					yes.unbind('click');
					yes.click(listener);
				}
			}

			no.click(this.popup.hide);
		},

		loadConcretes : function loadConcretes() {
			this.concretes = $('#concreteList > .concrete');
			Utility.log("lol");

			var ui = this;
			this.concretes.each(function(index, elem) {
				var concrete = $(elem);
				var position = toCoords(concrete.children('.concreteDetail')
						.children('.coords'));

				var marker = new google.maps.Marker({
					position : position,
					map : ui.map
				});

				concrete.click(ui.selectConcrete.bind(ui, concrete, marker));
			});
		},

		selectConcrete : function _selectConcrete(concrete, marker) {
			this.popup.hide();
			var detail = concrete.children('.concreteDetail');

			// Set top offset
			var top = concrete.position().top;
			var height = concrete.height();
			var newPosition = top + height + 9;
			this.popup.top(newPosition);

			// Set name
			var name = detail.children('.name').text();
			this.popup.setName(name);

			// Set listener
			var id = detail.children('.id').text();
			this.popup.yes(this.createLoan.bind(this, id));

			this.popup.show();
			this.bounceMarker(marker);
		},

		createLoan : function(detailId) {
			function success(req) {
				location.href = "/loans/detail?id=" + req.descriptor.id;
			}
			
			function error(req){
				Webookia.Error.append(req.responseJSON.descriptor.message);
			}

			Loan.create(detailId, success, error);
		},

		init : function _init() {
			this.loadMap();
			this.loadPopup();
			this.loadConcretes();
		}
	}

	Initializer.plugin(function __loadConcreteUI__() {
		if ($doc("concreteDisplay")) {
			ConcreteUI.init();
		}
	});

})();