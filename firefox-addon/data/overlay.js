function Overlay(panelHtml, panelCss) {
	
	this.visible = false;

	this.isVisible = function() {
		return this.visible;
	}

	this.show = function() {
		this.visible = true;
var overlayDiv = jQuery('<div id="overlay" '
	   	//+ ' style="padding-left: 1em; border: 2px solid black; position: fixed; top: 0; left: 10%; width: 80%; height: 170px; background-color: #FFF; filter:alpha(opacity=95); -moz-opacity:0.95; -khtml-opacity: 0.95; opacity: 0.95; z-index: 10000;'
		+ ' class="ftc_overlayDiv"'
	   	+ '">'
		+ panelHtml
		+ '	</div>');
overlayDiv.draggable();
overlayDiv.resizable();
overlayDiv.appendTo(document.body);

var cssElement = jQuery('<style type="text/css">' + panelCss + '</style>');
cssElement.appendTo(document.head);
	}

	this.hide = function() {
		this.visible = false;
	}
}
