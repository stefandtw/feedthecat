var widgets = require("sdk/widget");
var tabs = require("sdk/tabs");
var self = require("sdk/self");

var widget = widgets.Widget({
  id: "feedthecat-widget",
  label: "feedthecat",
  contentURL: "http://www.mozilla.org/favicon.ico",
  onClick: function() {
	  worker = tabs.activeTab.attach({
		  contentScriptFile: self.data.url("page.js")
	  });
	  worker.port.emit("init");
  }
});
