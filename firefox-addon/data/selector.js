"use strict";
function createSelector(from) {
	if ( typeof from === 'undefined') {
		from = {};
	}

	var selector = {
		includedNodes : from.includedNodes && from.includedNodes.slice(0) || [],
		xpath : from.xpath || ''
	};

	var xpathObservers = [];

	selector.generateXpath = function(pageDocument) {
		var generator = createXpathGenerator();
		try {
			var newXpath = generator.generateXpath(selector.includedNodes, pageDocument);
		} catch (e) {
			console.log(e.message);
			newXpath = '';
		}
		selector.setXpath(newXpath);
		return selector.xpath;
	};

	selector.setXpath = function(value) {
		var oldValue = selector.xpath;
		selector.xpath = value;
		if (value !== oldValue) {
			xpathObservers.forEach(function(observer) {
				observer(value);
			});
		}
	};

	selector.getXpath = function() {
		return selector.xpath;
	};

	selector.addXpathObserver = function(observer) {
		help(xpathObservers).add(observer);
	};

	selector.removeXpathObserver = function(observer) {
		help(xpathObservers).remove(observer);
	};

	selector.select = function(pageDocument) {
		var result = [];
		try {
			var iterator = pageDocument.evaluate(selector.xpath, pageDocument, null, XPathResult.ORDERED_NODE_ITERATOR_TYPE, null);
		} catch (e) {
			console.log('could not evaluate XPath ' + selector.xpath);
			return result;
		}
		var node;
		while ( node = iterator.iterateNext()) {
			result.push(node);
		};
		return result;
	};

	return selector;
}