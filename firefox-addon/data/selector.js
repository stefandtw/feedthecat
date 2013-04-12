function createSelector() {

	var selector = {
		includedNodes : [],
		excludedNodes : []
	};

	var xpath = '';
	var xpathObservers = [];

	selector.createXpathExpression = function() {
		var newXpath = findXpathByMergingXpaths(selector.includedNodes);
		selector.setXpath(newXpath);
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

function findXpathBlindly() {
	var SAME_CLASS = 'same class';
	var SAME_ELEMENT_NAME = 'same element name';
	var allTraitsArray = [SAME_CLASS, SAME_ELEMENT_NAME];
	/*
	 var getTraitsIntersection = function(traitArray1, traitArray2) {
	 //TODO
	 };
	 var getTraits = function(node) {
	 //TODO
	 }
	 var findCommonTraits = function findCommonTraits(possibleTraits, nodes) {
	 if (nodes.length === 1) {
	 //return possibleTraits;
	 var traits = getTraits(node);
	 return getTraitsIntersection(traits, possibleTraits);
	 } else if (nodes.length > 2) {
	 var lastNode = nodes.pop();
	 var commonInTwo = findCommonTraits(possibleTraits, [nodes[0], lastNode]);
	 return findCommonTraits(commonInTwo, nodes);
	 } else {
	 var nextTraits = findCommonTraits(possibleTraits, nodes[0]);
	 findCommonTraits(nextTraits, nodes[1]);
	 }

	 };
	 var commonTraits = findCommonTraits(allTraitsArray, selector.includedNodes);
	 */
	var commonTraits = [SAME_CLASS];

	var xpr;
	if (commonTraits.contains(SAME_CLASS)) {
		var className = 'news_article';
		xpr = "//*[contains(concat(' ',@class,' '),' " + className + " ')]";
	} else if (commonTraits.contains(SAME_ELEMENT_NAME)) {
		var elementName = 'h2';
		xpr = '//' + elementName;
	} else {
		xpr = '';
	}
	return xpr;
}

function findXpathByMergingXpaths(includedNodes) {
	var xpaths = findInformativeXpaths(includedNodes);
	var xpath = mergeXpaths(xpaths);
	xpath = optimizeXpath(xpath, includedNodes);
	return xpath;
}

function findInformativeXpaths(includedNodes) {
	var xpaths = [];
	includedNodes.forEach(function(node) {
		var xpath = findInformativeXpath(node);
		xpaths.push(xpath);
	});
	return xpaths;
}

function findInformativeXpath(forNode) {
	var xpath = '';
	var indexString;
	var classString;
	var tagName;
	var node = jQuery(forNode);
	do {
		tagName = node.get(0).localName;
		indexString = '[' + (node.parent().children(tagName).index(node) + 1) + ']';
		classString = '';
		var classList = node.get(0).classList;
		for (var i = 0; i < classList.length; i++) {
			var className = classList.item(i);
			classString = classString + "[contains(concat(' ',@class,' '),' " + className + " ')]";
		}
		xpath = '/' + tagName + indexString + classString + xpath;
	} while ( (node = node.parent()) && node.length === 1 && node.get(0).localName);
	console.log("found xpath: " + xpath);
	return xpath;
}

function mergeXpaths(xpaths) {
	//TODO implement a merging algorithm, like in a diff tool
	if (xpaths.length >= 1) {
		return xpaths[0];
	}
	return mergeXpaths([xpaths[0], xpaths[1]]);
}

function optimizeXpath(xpath) {
	//TODO optimize
	return xpath;
}
