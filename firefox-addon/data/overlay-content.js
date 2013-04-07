function OverlayContent(overlayDocument) {

	this.init = function() {
			
		// switching between panels
		$(".ftc_switch",overlayDocument).click(function() {
			$(".ftc_active_panel",overlayDocument).removeClass("ftc_active_panel");
			var panelNames = $(this).data('panel').split(',');
			for (var i=0; i<panelNames.length; i++) {
				$(".ftc_panel[name=" + panelNames[i] + "]",overlayDocument).addClass("ftc_active_panel");
			}
		});
		
		// fill in webpage URL
		$(".ftc_data[name=webpageUrl]",overlayDocument).val(document.URL);
		
		// test if DOM nodes can be accessed (code may be deleted later)
		$(".ftc_data[name=description]",overlayDocument).val(document.body.outerHTML);
	}

}
