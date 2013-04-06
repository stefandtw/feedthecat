function Overlay(panelHtml, panelCss) {

	this.visible = false;

	this.isVisible = function() {
		return this.visible;
	}

	this.show = function() {
		this.visible = true;
		var overlayDiv = jQuery('<div id="overlay" class="ftc_overlayDiv">' + panelHtml + '	</div>');
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
