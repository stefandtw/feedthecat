"use strict";
function createPageMantle(pageDocument) {

	var pageMantle = {
		pageDocument : pageDocument,
		highlighter : createHighlighter()
	};

	var currentSelector = null;

	var clickListener = function(event) {
		var domNode = event.target;
		if (pageMantle.isSelectable(domNode)) {
			event.preventDefault();
		}
	};
	var mousedownListener = function(event) {
		var domNode = event.target;
		if (event.which === 1) {
			if (currentSelector && pageMantle.isSelectable(domNode)) {
				pageMantle.toggleSelection(domNode, currentSelector.includedNodes, 'includedNode');
			}
		}
	};
	$('body', pageDocument).click(clickListener);
	$('body', pageDocument).mousedown(mousedownListener);

	pageMantle.isSelectable = function(domNode) {
		if (jQuery(domNode).parents('.ftc_dialogDiv').length > 0) {
			return false;
		}
		return true;
	}

	pageMantle.toggleSelection = function(node, nodes, hightlightingType) {
		if (help(nodes).contains(node)) {
			help(nodes).remove(node);
			pageMantle.highlighter.removeHighlight(node, hightlightingType);
		} else {
			help(nodes).add(node);
			pageMantle.highlighter.highlight(node, hightlightingType);
		}
		currentSelector.generateXpath(pageDocument);
	};

	pageMantle.setCurrentSelector = function(selector) {
		var oldSelector = currentSelector;
		currentSelector = selector;
		pageMantle.highlighter.reset();
		if (selector) {
			selector.includedNodes.forEach(function(node) {
				pageMantle.highlighter.highlight(node, 'includedNode');
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

	pageMantle.destroy = function() {
		pageMantle.setCurrentSelector(null);
		$('body', pageDocument).off('click', clickListener);
		$('body', pageDocument).off('mousedown', mousedownListener);
	};

	return pageMantle;
}