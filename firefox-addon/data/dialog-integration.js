"use strict";
function createDialog(pageMantle, dialogHtml, dialogCss) {

	var dialog = {};

	// write dialog HTML to document
	var dialogDiv = jQuery('<div title="Create a feed"></div>');
	dialogDiv.appendTo(document.body);

	// create iframe
	var iframe = jQuery('<iframe frameborder="0" style="width:100%; height:270px;">');
	iframe.appendTo(dialogDiv);
	iframe.load(function() {

		var dialogDocument = iframe[0].contentWindow.document;
		$('body', dialogDocument).html(dialogHtml);

		// write dialog content CSS to dialog
		var dialogCssElement = jQuery('<style type="text/css">' + dialogCss + '</style>');
		dialogCssElement.appendTo(dialogDocument.head);

		var dialogContent = createDialogContent(dialogDocument, pageMantle);

		var keepPositionFixed = function(event) {
			$(event.target).parent().css('position', 'fixed');
		};
		$(dialogDiv).dialog({
			dialogClass : 'ftc_dialogDiv',
			width : 750,
			create : keepPositionFixed,
			resize : keepPositionFixed,
			close : pageMantle.destroy
		});

	});

	return dialog;
}
