"use strict";
function createXpathGenerator() {
	var generator = {};

	/**
	 * Generates an XPath expression based on selected nodes in a document.
	 *
	 * Throws an exception in cases where the included nodes are too
	 * different for the algorithm to find a common XPath expression.
	 * Hopefully future versions of the algorithm will be able to handle
	 * more of those cases.
	 */
	generator.generateXpath = function(includedNodes, pageDocument) {
		var xpaths = findInformativeXpaths(includedNodes);
		var xpath = mergeXpaths(xpaths);
		xpath = optimizeXpath(xpath, pageDocument);
		return xpath;
	};

	return generator;

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
		var classList;
		var tagName;
		var node = jQuery(forNode);
		do {
			tagName = node.get(0).localName;
			indexString = '[' + (node.parent().children(tagName).index(node) + 1) + ']';
			classString = '';
			classList = node.get(0).classList;
			for (var i = 0; i < classList.length; i++) {
				var className = classList.item(i);
				if (!help(className).startsWith('ftc_')) {// ignore feedthecat-specific classes
					classString = classString + "[contains(concat(' ',@class,' '),' " + className + " ')]";
				}
			}
			xpath = '/' + tagName + indexString + classString + xpath;
		} while ( (node = node.parent()) && node.length === 1 && node.get(0).localName);
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
		var originalSelection = createSelector({
			xpath : xpath
		}).select(pageDocument);
		var tree = createXpathTree(xpath);

		tryAndOptimize(replaceElementWithDescendantsSelector, getElementArray);
		tryAndOptimize(function(element) {
			tryAndOptimize(removeCondition, getConditionArrayGetter(element));
		}, getElementArray);

		return xpath;

		function tryAndOptimize(optimize, getItemArray) {
			var array = getItemArray();
			for (var i = 0; i < array.length; i++, array = getItemArray()) {
				var optimization = optimize(array[i], array, i);
				var changedXpath = tree.toXpath();
				var selector = createSelector({
					xpath : changedXpath
				});
				var optimizedSelection = selector.select(pageDocument);
				if (help(optimizedSelection).equals(originalSelection)) {
					xpath = changedXpath;
					if (optimization && optimization.onSuccessChangeIndex) {
						i = optimization.onSuccessChangeIndex(i);
					}
				} else {
					tree = createXpathTree(xpath);
				}
			}
		}

		function replaceElementWithDescendantsSelector(element, array, i) {
			if (i === array.length - 1) {
				return;
			}
			help(array).remove(element);
			array[i].tagName = '/' + array[i].tagName;
			return {
				onSuccessChangeIndex : function(i) {
					return i - 1;
				}
			};
		}

		function removeCondition(condition, array, i) {
			help(array).remove(condition);
			return {
				onSuccessChangeIndex : function(i) {
					return i - 1;
				}
			};
		}

		function getElementArray() {
			return tree.elements;
		}

		function getConditionArrayGetter(element) {
			return function() {
				return element.conditions;
			};
		}

	}

}