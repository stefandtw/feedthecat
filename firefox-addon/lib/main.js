"use strict";
var widgets = require("sdk/widget");
var tabs = require("sdk/tabs");
var self = require("sdk/self");
var data = self.data;
var serverUrl = require('sdk/simple-prefs').prefs['serverUrl'];
 
var dialogHtml = data.load("dialog.html");
var pageCss = data.load("highlighter.css")//
+ '\n' + data.load("jquery-ui-1.10.2.custom/css/ui-lightness/jquery-ui-1.10.2.custom.min.css")//
/*ugh.. replace urls */.replace(/url\(images/g, 'url(' + data.url("jquery-ui-1.10.2.custom/css/ui-lightness/images"));
var dialogCss = data.load("dialog.css")//
+ '\n' + data.load("HTML-KickStart-master/css/prettify.css")//
+ '\n' + data.load("HTML-KickStart-master/css/kickstart-buttons.css")//
+ '\n' + data.load("HTML-KickStart-master/css/kickstart-forms.css")//
+ '\n' + data.load("HTML-KickStart-master/css/kickstart-grid.css")//
+ '\n' + data.load("HTML-KickStart-master/css/kickstart-menus.css")//
+ '\n' + data.load("HTML-KickStart-master/css/kickstart.css")//
;

var widget = widgets.Widget({
	id : "feedthecat-widget",
	label : "feedthecat",
	contentURL : data.url("images/Cat_-3.svg"),
	onClick : function() {
		var worker = tabs.activeTab.attach({
			contentScriptFile : [data.url("helper-functions.js"), data.url("page.js"), data.url("page-mantle.js"), data.url("selector.js"), data.url("xpath-generator.js"), data.url("highlighter.js"), data.url("dialog-container.js"), data.url("dialog-content.js"), data.url("jquery/jquery-1.9.1.min.js"), data.url("jquery-ui-1.10.2.custom/js/jquery-ui-1.10.2.custom.min.js"), data.url("jquery/jquery-migrate-1.1.1.min.js"), data.url("HTML-KickStart-master/js/kickstart.js")]
		});
		worker.port.emit("init", pageCss, dialogHtml, dialogCss, serverUrl);
	}
});
