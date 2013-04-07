function OverlayIntegration(pageCss, panelHtml, panelCss) {

	this.visible = false;

	this.isVisible = function() {
		return this.visible;
	}

	this.show = function() {
		// write overlay HTML to document
		this.visible = true;
		var overlayDiv = jQuery('<div class="ftc_overlayDiv"></div>');
		overlayDiv.draggable({iframeFix: true, cursor: "crosshair"});
		overlayDiv.resizable();
		overlayDiv.appendTo(document.body);

		// write overlay CSS to document
		var cssElement = jQuery('<style type="text/css">' + pageCss + '</style>');
		cssElement.appendTo(document.head);
		
		// create iframe
		var iframe = jQuery('<iframe frameborder="0" style="width:100%; height:270px;">');
		iframe.appendTo(overlayDiv);
		iframe.load(function(){
			
			var panelDocument = iframe[0].contentWindow.document;
			$('body',panelDocument).html(panelHtml);

			// write overlay content CSS to panel
			var cssElement = jQuery('<style type="text/css">' + panelCss + '</style>');
			cssElement.appendTo(panelDocument.head);
		
			var overlayContent = new OverlayContent(panelDocument);
			overlayContent.init();
			
			var draggableHandler = jQuery('<div style="position: absolute; height: 30px; width: 30px; background-color: black; z-index: 1000;"></div>');
			draggableHandler.appendTo(overlayDiv);
		});
	}

	this.hide = function() {
		this.visible = false;
	}
}
