self.port.on("init", function(dialogIntegrationCss, dialogHtml, dialogCss) {

	var dialog = new DialogIntegration(dialogIntegrationCss, dialogHtml, dialogCss);
	dialog.show();
});
