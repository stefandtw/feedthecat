self.port.on("init", function(panelHtml, panelCss) {
//	document.body.style.border = "5px solid red";

	var overlay = new Overlay(panelHtml, panelCss);
	overlay.show();
});
