"use strict";
function createHighlighter() {

	var highlighter = {};

	var changes = [];

	var getCssClassForType = function(type) {
		switch (type) {
			case 'includedNode':
				return 'ftc_included';
			case 'previewNode':
				return 'ftc_preview';
			default:
				throw new Error('Unknown type ' + type);
		}
	};

	highlighter.highlight = function(domNode, type) {
		jQuery(domNode).addClass(getCssClassForType(type));
		help(changes).add({
			node : domNode,
			type : type
		});
	};

	highlighter.removeHighlight = function(domNode, type) {
		jQuery(domNode).removeClass(getCssClassForType(type));
		help(changes).remove({
			node : domNode,
			type : type
		});
	};

	highlighter.reset = function(type) {
		changes.forEach(function(change) {
			if ( typeof type === 'undefined' || change.type === type) {
				highlighter.removeHighlight(change.node, change.type);
			}
		});
	};

	return highlighter;
}
