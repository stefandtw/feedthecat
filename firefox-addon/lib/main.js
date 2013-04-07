var widgets = require("sdk/widget");
var tabs = require("sdk/tabs");
var self = require("sdk/self");
var data = self.data;

var panelHtml = data.load("panel.html");
var panelCss = data.load("panel.css")//
+ '\n' + data.load("jquery-ui-1.10.2.custom/css/ui-lightness/jquery-ui-1.10.2.custom.min.css")//
+ '\n' + data.load("HTML-KickStart-master/css/prettify.css")//
+ '\n' + data.load("HTML-KickStart-master/css/kickstart-buttons.css")//
+ '\n' + data.load("HTML-KickStart-master/css/kickstart-forms.css")//
+ '\n' + data.load("HTML-KickStart-master/css/kickstart-grid.css")//
+ '\n' + data.load("HTML-KickStart-master/css/kickstart-menus.css")//
+ '\n' + data.load("HTML-KickStart-master/css/kickstart.css")//
//+ '\n' + data.load("HTML-KickStart-master/style.css")//
;

var widget = widgets.Widget({
	id : "feedthecat-widget",
	label : "feedthecat",
	contentURL : "http://www.mozilla.org/favicon.ico",
	// panel: panel,
	onClick : function() {
		worker = tabs.activeTab.attach({
			contentScriptFile : [data.url("page.js"), data.url("overlay.js"), data.url("jquery-1.9.1.min.js"), data.url("jquery-ui-1.10.2.custom/js/jquery-ui-1.10.2.custom.min.js"), data.url("jquery-migrate-1.1.1.min.js"), data.url("HTML-KickStart-master/js/kickstart.js")]
		});
		worker.port.emit("init", panelHtml, panelCss);
	}
});