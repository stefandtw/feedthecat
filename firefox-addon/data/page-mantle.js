function createPageMantle(pageDocument) {

	var pageMantle = {
		pageDocument : pageDocument,
		highlighter : createHighlighter()
	};

	var currentSelector = null;

	$('body', pageDocument).click(function(event) {
		event.preventDefault();
	});
	$('body', pageDocument).mousedown(function(event) {
		var domNode = event.target;
		if (event.which === 1) {
			if (currentSelector) {
				pageMantle.toggleSelection(domNode, currentSelector.includedNodes, 'includedNode');
			}
		} else if (event.which === 2) {
			if (currentSelector) {
				pageMantle.toggleSelection(domNode, currentSelector.excludedNodes, 'excludedNode');
			}
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
			selector.excludedNodes.forEach(function(node) {
				pageMantle.highlighter.highlight(node, 'excludedNode');
			});
		}
	}

	return pageMantle;
}