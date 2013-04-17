"use strict";
function createSelector(from) {
	if ( typeof from === 'undefined') {
		from = {};
	}

	var selector = {
		includedNodes : from.includedNodes && from.includedNodes.slice(0) || [],
		excludedNodes : from.excludedNodes && from.excludedNodes.slice(0) || [],
		xpath : from.xpath || ''
	};

	var xpathObservers = [];

	selector.createXpathExpression = function(pageDocument) {
		try {
			var newXpath = findXpathByMergingXpaths(selector.includedNodes, pageDocument);
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
	if (help(commonTraits).contains(SAME_CLASS)) {
		var className = 'news_article';
		xpr = "//*[contains(concat(' ',@class,' '),' " + className + " ')]";
	} else if (help(commonTraits).contains(SAME_ELEMENT_NAME)) {
		var elementName = 'h2';
		xpr = '//' + elementName;
	} else {
		xpr = '';
	}
	return xpr;
}

function findXpathByMergingXpaths(includedNodes, pageDocument) {
	var xpaths = findInformativeXpaths(includedNodes);
	var xpath = mergeXpaths(xpaths);
	xpath = optimizeXpath(xpath, pageDocument);
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
			if (!help(className).startsWith('ftc_')) {// ignore feedthecat-specific classes
				classString = classString + "[contains(concat(' ',@class,' '),' " + className + " ')]";
			}
		}
		xpath = '/' + tagName + indexString + classString + xpath;
	} while ( (node = node.parent()) && node.length === 1 && node.get(0).localName);
	console.log("found informative xpath: " + xpath);
	return xpath;
}

function mergeXpaths(xpaths) {
	if (xpaths.length === 0) {
		return '';
	}
	return xpaths.reduce(mergeTwoXpaths);

	function mergeTwoXpaths(aXpath, bXpath) {
		var aTree = createXpathTree(aXpath);
		var bTree = createXpathTree(bXpath);
		if (aTree.elements.length !== bTree.elements.length) {
			throw Error("can't handle nodes of different hierarchies");
		}
		for (var i = 0; i < aTree.elements.length; i++) {
			var aElement = aTree.elements[i];
			var bElement = bTree.elements[i];
			if (aElement.tagName !== bElement.tagName) {
				throw Error("can't handle nodes with different tag names");
			}
			aElement.conditions = aElement.conditions.filter(function(aCondition) {
				return help(bElement.conditions).contains(aCondition);
			});
		}
		return aTree.toXpath();
	};
}

function createXpathTree(xpath) {
	var xpathTree = {
		elements : []
	};
	var i = 0;
	parseXpath();

	function parseXpath() {
		for (; i < xpath.length; i++) {
			if (xpath[i] === '/' && xpath[i - 1] !== '/') {
				i++;
				parseXpathElement();
			}
		}
		return xpathTree;
	}

	function parseXpathElement() {
		var element = createXpathElement();
		for (; i < xpath.length; i++) {
			if (xpath[i] === '/' && xpath[i - 1] !== '/') {
				i--;
				break;
			} else if (xpath[i] === '[') {
				parseXpathCondition(element);
			} else {
				element.tagName += xpath[i];
			}
		}
		xpathTree.elements.push(element);
	}

	/**
	 * Parses a condition that starts with '[' and ends with ']'.
	 * For now, we don't need to know what the condition is about.
	 * We just add the condition string to the element object.
	 */
	function parseXpathCondition(parentElement) {
		var openingBracketIndex = i;
		var dontCareAboutDetails = {};
		i++;
		for (; i < xpath.length; i++) {
			if (xpath[i] === '[') {
				parseXpathCondition(dontCareAboutDetails);
			} else if (xpath[i] === ']') {
				var condition = xpath.substring(openingBracketIndex, i + 1);
				parentElement.conditions.push(condition);
				return;
			}
		}
	}


	xpathTree.toXpath = function() {
		var xpath = '';
		xpathTree.elements.forEach(function(element) {
			xpath += '/' + element.tagName;
			element.conditions.forEach(function(condition) {
				xpath += condition;
			});
		});
		return xpath;
	};

	return xpathTree;
}

function createXpathElement(tagName, conditions) {
	return {
		/** The term 'tagName' is a little simplified. It can include a '/' or other axis prefixes */
		tagName : tagName || '',
		conditions : conditions || []
	};
}

/**
 * Shortens an XPath expression. The optimized expression still selects
 * the same elements as the original one.
 * A document object is needed to validate this.
 */
function optimizeXpath(xpath, pageDocument) {
	//TODO refactor
	var originalSelection = createSelector({
		xpath : xpath
	}).select(pageDocument);
	var tree = createXpathTree(xpath);
	replaceElementsWithDescendantsSelector();
	removeConditions();

	function replaceElementsWithDescendantsSelector() {
		for (var i = 0; i < tree.elements.length - 1; i++) {
			var element = tree.elements[i];
			help(tree.elements).remove(element);
			tree.elements[i].tagName = '/' + tree.elements[i].tagName;
			var changedXpath = tree.toXpath();
			var selector = createSelector({
				xpath : changedXpath
			});
			var optimizedSelection = selector.select(pageDocument);
			if (help(optimizedSelection).equals(originalSelection)) {
				i--;
				xpath = changedXpath;
			} else {
				tree = createXpathTree(xpath);
			}
		}
	}

	function removeConditions() {
		//TODO remove unnecessary conditions
		tree.elements.forEach(function(element) {

		});
	}

	return xpath;
}
