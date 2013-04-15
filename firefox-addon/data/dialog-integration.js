"use strict";
function createDialog(pageMantle, dialogHtml, dialogCss) {

	var dialog = {};

	// write dialog HTML to document
	var dialogDiv = jQuery('<div title="Create a feed"></div>');
	dialogDiv.appendTo(document.body);

	// create iframe
	var iframe = jQuery('<iframe frameborder="0" style="width:100%; height:100%;">');
	iframe.appendTo(dialogDiv);
	iframe.load(function() {

		var dialogDocument = iframe[0].contentWindow.document;
		dialogDocument.write(dialogHtml); // document.write() has the advantage to actually use the doctype from the given string.
		dialogDocument.close();

		// write dialog content CSS to dialog
		var dialogCssElement = jQuery('<style type="text/css">' + dialogCss + '</style>');
		dialogCssElement.appendTo(dialogDocument.head);

		var dialogContent = createDialogContent(dialogDocument, pageMantle);

		var keepPositionFixed = function(event) {
			$(event.target).parent().css('position', 'fixed');
		};
		$(dialogDiv).dialog({
			dialogClass : 'ftc_dialogDiv',
			/*TODO fix the vertical position. This seems especially difficult for pages without html5 doctype.
			 position : ['center', 'bottom'], //this works with the html5 doctype, but not in quirks mode
			 */
			width : 750,
			height : 270,
			create : keepPositionFixed,
			resize : keepPositionFixed,
			close : pageMantle.destroy
		});

	});

	return dialog;
}
