self.port.on("init", function(pageCss, panelHtml, panelCss) {
	// document.body.style.border = "5px solid red";

	var overlay = new OverlayIntegration(pageCss, panelHtml, panelCss);
	overlay.show();
});
