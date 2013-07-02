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
	var Api = {
		post : function _apiPost(url, json, onSuccess, onError) {
			$.ajax({
				type : "POST",
				beforeSend : function(req) {
					req.setRequestHeader("Content-Type", "application/json");
				},
				url : API_URL + url,
				data : JSON.stringify(json),
			}).done(onSuccess).fail(onError);
		},
		get : function _apiGet(url, onSuccess, onError) {
			$.ajax({
				type : "GET",
				url : API_URL + url
			}).done(onSuccess).fail(onError);
		}
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

	Loan.prototype = {
		getId : function _loanGetId() {
			return this.id;
		},

		accept : function _loanAccept(onSuccess, onError) {
			Api.get("/loans/loan/" + this.id + "/accept", onSuccess, onError);
		},
		refuse : function _loanRefuse(onSuccess, onError) {
			Api.get("/loans/loan/" + this.id + "/refuse", onSuccess, onError);
		},
		ship : function _loanShip(onSuccess, onError) {
			Api.get("/loans/loan/" + this.id + "/shipped", onSuccess, onError);
		},
		giveBack : function _loanGiveBack(onSuccess, onError) {
			Api.get("/loans/loan/" + this.id + "/giveback", onSuccess, onError);
		},
		message : function _loanMessage(text, onSuccess, onError) {
			var json = {
				messageText : text
			};
			var url = "/loans/loan/" + this.id + "/messages";
			Api.post(url, json, onSuccess, onError)
		}
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
			var url = "/books/book/" + this.id;
			Api.post(url, json, onSuccess, onError);
		},
		review : function _review(text, mark, onSuccess, onError) {
			var json = {
				mark : mark,
				text : text
			};
			var url = "/books/book/" + this.id + "/review";
			Api.post(url, json, onSuccess, onError);
		},
		comment : function _comment(text, onSuccess, onError) {
			var json = {
				text : text
			};
			var url = "/books/book/" + this.id + "/review/comment";
			Api.post(url, json, onSuccess, onError);
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
		append : function _append(errorMsg, parent) {
			var error = $("<div class=\"error clearfix\"></div>").prependTo(
					(parent ? parent : errorContainer));

			$("<div class=\"errorText\"></div>").text(errorMsg).appendTo(error);

			$("<div class=\"close\"></div>").text("x").click(function() {
				error.remove()
			}).appendTo(error);
		}
	}

	// Book UI
	Webookia.Initializer.plugin(function() {
		if ($doc("bookUI")) {
			var book = new Book($(".detailContainer .id").text());
			var defaultOnSuccess = location.reload.bind(location);
			var defaultOnError = function(resp) {
				console.log(resp);
			}

			$("#bookPrivacy").change(function(event) {
				var privacy = $(event.target).val();
				book.privacy(privacy, defaultOnSuccess, defaultOnError);
			});

			$("#bookStatus").change(
					function(event) {
						var status = event.target.checked ? "AVAILABLE"
								: "NOT_AVAILABLE";
						book.status(status, defaultOnSuccess, defaultOnError)
					});

			$("#commentSubmit").click(function() {
				var text = $("#commentText").val();
				book.comment(text, defaultOnSuccess, defaultOnError)
			});

			$("#reviewSubmit").click(
					function(event) {
						var mark = Marker.valueOf($("#reviewMark"));
						var text = $("#reviewText").val();
						if (mark == 0 || text == "") {
							Webookia.Error.append("Compila entrambi i campi.",
									$(event.target.parentNode));
							return;
						}

						book.review(text, mark, defaultOnSuccess,
								defaultOnError);
					});
		}
	});

	// Loan UI
	Webookia.Initializer.plugin(function() {
		if ($doc("loanUI")) {
			var loan = new Loan($(".details .id").text())
			var defaultOnSuccess = location.reload.bind(location);
			var defaultOnError = function(resp) {
				console.log(resp);
			}

			$("#accepted").click(function() {
				loan.accept(defaultOnSuccess, defaultOnError);
			});

			$("#refused").click(function() {
				loan.refuse(defaultOnSuccess, defaultOnError);
			});

			$("#shipped").click(function() {
				loan.ship(defaultOnSuccess, defaultOnError);
			});

			$("#given_back").click(function() {
				loan.giveBack(defaultOnSuccess, defaultOnError);
			});
			
			$("#messageSubmit").click(function(event){
				var text = $("#messageText").val();
				if(text == ""){
					Webookia.Error.append("Non puoi mandare un messaggio vuoto.", $(event.target.parentNode));
					return;
				}
				
				loan.message(text, defaultOnSuccess, defaultOnError);
			});

		}
	});

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

			function error(req) {
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

	Webookia.HTML = {}

	// Marker
	var Marker = Webookia.HTML.Marker = function(editable, initialValue, id) {
		var div = this._div = $('<div class="marker"></div>');
		var stars = this.stars = new Array();

		this.currentMark = initialValue || 0;

		div.attr('id', id);
		div.data("marker", this);

		if (editable) {
			div.addClass('editable');
			div.mouseout(this.onMarkerLeave.bind(this));
		}

		for ( var i = 0; i < this.maxValue; i++) {
			var markValue = i + 1;
			var star = $('<span class="star">&nbsp;</span>');

			// Append created star
			div.append(star);
			stars.push(star);

			// Add listeners
			if (editable) {
				star.mouseover(this.showMark.bind(this, markValue));
				star.click(this.setMarkValue.bind(this, markValue));
			}
		}

		this.showMark(this.currentMark);
	}

	Marker.valueOf = function(elem) {
		return elem.data("marker").getValue();
	}

	Marker.prototype = {
		maxValue : 5,

		showMark : function(value) {
			for ( var i = 0; i < 5; i++) {
				var elem = this.stars[i];

				if (i < value) {
					elem.addClass("active");
				} else {
					elem.removeClass("active");
				}
			}
		},

		onMarkerLeave : function() {
			this.showMark(this.currentMark);
		},

		setMarkValue : function(mark) {
			if (mark <= 0 || mark > this.maxMark) {
				return;
			}

			this.currentMark = mark;
		},

		getValue : function() {
			return this.currentMark;
		}
	};

	Webookia.Initializer
			.plugin(function() {
				var markers = document.querySelectorAll(".markerPlaceholder");
				for ( var i = 0; i < markers.length; i++) {
					var placeholder = $(markers[i]), id = placeholder
							.attr('id'), text = placeholder.text(), mark = (text == "" ? null
							: parseInt(text)), editable = placeholder
							.hasClass("editable");

					var marker = new Webookia.HTML.Marker(editable, mark, id);
					placeholder.replaceWith(marker._div);
				}
			});

})();