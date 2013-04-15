"use strict";
self.port.on("init", function(pageCss, dialogHtml, dialogCss) {
	var pageMantle = createPageMantle(document);

	var dialog = createDialog(pageMantle, pageCss, dialogHtml, dialogCss);
	dialog.show();
});
