function createHighlighter() {

	var highlighter = {};

	var changes = [];

	var getCssClassForType = function(type) {
		switch (type) {
			case 'includedNode':
				return 'ftc_included';
			default:
				throw new Error('Unknown type ' + type);
		}
	}

	highlighter.highlight = function(domNode, type) {
		jQuery(domNode).addClass(getCssClassForType(type));
		changes.add({
			node : domNode,
			type : type
		});
	}

	highlighter.removeHighlight = function(domNode, type) {
		jQuery(domNode).removeClass(getCssClassForType(type));
		changes.remove({
			node : domNode,
			type : type
		});
	}

	highlighter.reset = function() {
		changes.forEach(function(change) {
			highlighter.removeHighlight(change.node, change.type);
		});
	}

	return highlighter;
}
