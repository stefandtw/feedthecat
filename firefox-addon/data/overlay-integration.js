function Overlay(panelHtml, panelCss) {

	this.visible = false;

	this.isVisible = function() {
		return this.visible;
	}

	this.show = function() {
		// write overlay HTML to document
		this.visible = true;
		var overlayDiv = jQuery('<div id="overlay" class="ftc_overlayDiv">' + panelHtml + '	</div>');
		overlayDiv.draggable();
		overlayDiv.resizable();
		overlayDiv.appendTo(document.body);

		// write overlay CSS to document
		var cssElement = jQuery('<style type="text/css">' + panelCss + '</style>');
		cssElement.appendTo(document.head);
		
		// switching between panels
		$(".ftc_switch").click(function() {
			$(".ftc_active_panel").removeClass("ftc_active_panel");
			$(".ftc_panel[name=" + $(this).data('panel') + "]").addClass("ftc_active_panel");
		});
		
		// fill in webpage URL
		$(".ftc_data[name=webpageUrl]").val(document.URL);
	}

	this.hide = function() {
		this.visible = false;
	}
}
