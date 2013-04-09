function createPageMantle(pageDocument) {

	var pageMantle = {
		pageDocument : pageDocument,
		highlighter : createHighlighter()
	};

	var currentSelector = null;

	$('body', pageDocument).click(function(e) {
		e.preventDefault();
		if (currentSelector) {
			var domNode = e.target;
			pageMantle.toggleSelection(domNode, currentSelector.includedNodes, 'includedNode');
		}
	});

	pageMantle.toggleSelection = function(node, nodes, hightlightingType) {
		if (nodes.contains(node)) {
			nodes.remove(node);
			pageMantle.highlighter.removeHighlight(node, hightlightingType);
		} else {
			nodes.add(node);
			pageMantle.highlighter.highlight(node, hightlightingType);
		}
	}

	pageMantle.setCurrentSelector = function(selector) {
		currentSelector = selector;
		pageMantle.highlighter.reset();
		if (selector) {
			selector.includedNodes.forEach(function(node) {
				pageMantle.highlighter.highlight(node, 'includedNode');
			});
		}
	}

	return pageMantle;
}