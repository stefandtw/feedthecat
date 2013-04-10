function createSelector() {

	var selector = {
		includedNodes : [],
		excludedNodes : []
	};

	var xpath = '';
	var xpathObservers = [];

	selector.recreateXpathExpression = function() {
		var xpr = '';
		if (selector.includedNodes[0]) {
			xpr = "//*[@class='news_article']";
		}
		selector.setXpath(xpr);
		return xpath;
	};

	selector.setXpath = function(value) {
		var oldValue = xpath;
		xpath = value;
		if (value !== oldValue) {
			xpathObservers.forEach(function(observer) {
				observer(value);
			});
		}
	};

	selector.getXpath = function() {
		return xpath;
	};

	selector.addXpathObserver = function(observer) {
		xpathObservers.add(observer);
	};

	selector.removeXpathObserver = function(observer) {
		xpathObservers.remove(observer);
	};

	selector.select = function(pageDocument) {
		var result = [];
		try {
			var iterator = pageDocument.evaluate(xpath, pageDocument, null, XPathResult.ORDERED_NODE_ITERATOR_TYPE, null);
		} catch (e) {
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
