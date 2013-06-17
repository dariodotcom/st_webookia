/*
 * This is a JavaScript Scratchpad.
 *
 * Enter some JavaScript, then Right Click or choose from the Execute Menu:
 * 1. Run to evaluate the selected text (Ctrl+R),
 * 2. Inspect to bring up an Object Inspector on the result (Ctrl+I), or,
 * 3. Display to insert the result in a comment after the selection. (Ctrl+L)
 */

/* Initialize map */
var map;

function initialize() {
  var mapOptions = {
    zoom: 8,
    center: new google.maps.LatLng(-34.397, 150.644),
    mapTypeId: google.maps.MapTypeId.ROADMAP
  };
  map = new google.maps.Map(document.getElementById('map'),
      mapOptions);
}

initialize();

/* ConcretePopup*/
var popup = $(".concretePopup");
popup.hide();

var ConcretePopup = window.concretePopup = {
    setName: function(name){
        popup.children(".text").children(".name").text(name);
    },
    show: function(){
        popup.show();
    },
    hide: function(){
        popup.hide();
    },
    top: function(top){
        popup.css("top", top);
    },
    
}

var buttons = popup.children('.buttons');
var no = buttons.children('.no');
no.click(ConcretePopup.hide);

/* Bounce marker */
function bounceMarker(marker){
    var position = marker.getPosition();
    map.panTo(position);
    marker.setAnimation(google.maps.Animation.BOUNCE);
    setTimeout(function(){marker.setAnimation(null)}, 2000);
}

function selectConcrete(concrete, marker){
    ConcretePopup.hide();
    // Set top.
    
    var name = concrete.children('.concreteDetail').children('.name').text();
    ConcretePopup.setName(name);
    
    ConcretePopup.show();
    bounceMarker(marker);
}

/* Initialize concretes */
$('#concreteList > .concrete').each(function(index, elem){
    var concrete = $(elem);
    var coords = concrete.children('.concreteDetail').children('.coords').text();
    var split = coords.split(",");
    var position = new google.maps.LatLng(parseFloat(split[0]), parseFloat(split[1]));

    var marker = new google.maps.Marker({
      position: position,
      map: map
    });

    concrete.click(selectConcrete.bind(null, concrete, marker));
})

function alertize(o){
    for(p in o){
        if(o.hasOwnProperty(p)){
            alert(p + ": " + p[o]);
        }
    }
}