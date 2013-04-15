"use strict";
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
		if (help(nodes).contains(node)) {
			help(nodes).remove(node);
			pageMantle.highlighter.removeHighlight(node, hightlightingType);
		} else {
			help(nodes).add(node);
			pageMantle.highlighter.highlight(node, hightlightingType);
		}
		currentSelector.createXpathExpression();
	};

	pageMantle.setCurrentSelector = function(selector) {
		var oldSelector = currentSelector;
		currentSelector = selector;
		pageMantle.highlighter.reset();
		if (selector) {
			selector.includedNodes.forEach(function(node) {
				pageMantle.highlighter.highlight(node, 'includedNode');
			});
			selector.excludedNodes.forEach(function(node) {
				pageMantle.highlighter.highlight(node, 'excludedNode');
			});
			pageMantle.updatePreview();			selector.addXpathObserver(pageMantle.updatePreview);
		}
		if (oldSelector) {
			oldSelector.removeXpathObserver(pageMantle.updatePreview);
		}
	};

	pageMantle.updatePreview = function() {
		var previewNodes = currentSelector.select(pageDocument);
		pageMantle.highlighter.reset('previewNode');
		previewNodes.forEach(function(node) {
			pageMantle.highlighter.highlight(node, 'previewNode');
		});
	};

	return pageMantle;
}