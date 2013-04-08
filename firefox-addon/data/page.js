self.port.on("init", function(pageCss, dialogHtml, dialogCss) {
	// document.body.style.border = "5px solid red";

	var dialog = new DialogIntegration(pageCss, dialogHtml, dialogCss);
	dialog.show();
});
