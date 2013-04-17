"use strict";
self.port.on("init", function(pageCss, dialogHtml, dialogCss, serverUrl) {
	var pageMantle = createPageMantle(document);

	var cssElement = jQuery('<style type="text/css">' + pageCss + '</style>');
	cssElement.appendTo(document.head);

	var dialog = createDialogContainer(pageMantle, dialogHtml, dialogCss);
});
