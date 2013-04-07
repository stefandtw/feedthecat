function OverlayIntegration(pageCss, panelHtml, panelCss) {

	this.visible = false;

	this.isVisible = function() {
		return this.visible;
	}

	this.show = function() {
		// write overlay HTML to document
		this.visible = true;
		var overlayDiv = jQuery('<div id="overlay" class="ftc_overlayDiv"></div>');
		overlayDiv.draggable();
		overlayDiv.resizable();
		overlayDiv.appendTo(document.body);

		// write overlay CSS to document
		var cssElement = jQuery('<style type="text/css">' + pageCss + '</style>');
		cssElement.appendTo(document.head);
		
		// create iframe
		var iframe = jQuery('<iframe style="width:100%; height:270px;">');
		iframe.appendTo(overlayDiv);
		iframe.load(function(){
			
			var panelDocument = iframe[0].contentWindow.document;
			$('body',panelDocument).html(panelHtml);

			// write overlay content CSS to panel
			var cssElement = jQuery('<style type="text/css">' + panelCss + '</style>');
			cssElement.appendTo(panelDocument.head);
		
			var overlayContent = new OverlayContent(panelDocument);
			overlayContent.init();
		});
	}

	this.hide = function() {
		this.visible = false;
	}
}
