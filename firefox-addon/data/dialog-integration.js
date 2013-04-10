function createDialog(pageMantle, pageCss, dialogHtml, dialogCss) {

	var dialog = {};

	dialog.show = function() {
		// write dialog HTML to document
		var dialogDiv = jQuery('<div class="ftc_dialogDiv"></div>');
		dialogDiv.draggable({
			iframeFix : true,
			cursor : "crosshair"
		});
		dialogDiv.resizable();
		dialogDiv.appendTo(document.body);

		// write dialog CSS to document
		var cssElement = jQuery('<style type="text/css">' + pageCss + '</style>');
		cssElement.appendTo(document.head);

		// create iframe
		var iframe = jQuery('<iframe frameborder="0" style="width:100%; height:270px;">');
		iframe.appendTo(dialogDiv);
		iframe.load(function() {

			var dialogDocument = iframe[0].contentWindow.document;
			$('body', dialogDocument).html(dialogHtml);

			// write dialog content CSS to dialog
			var cssElement = jQuery('<style type="text/css">' + dialogCss + '</style>');
			cssElement.appendTo(dialogDocument.head);

			var dialogContent = createDialogContent(dialogDocument, pageMantle);

			var draggableHandler = jQuery('<div style="position: absolute; height: 30px; width: 30px; background-color: black; z-index: 1000;"></div>');
			draggableHandler.appendTo(dialogDiv);
		});
	};

	return dialog;
}
