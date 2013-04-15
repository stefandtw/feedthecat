"use strict";
var selector;

module("selector.createXpathExpression()", {
	setup : function() {
		selector = createSelector();
	},
	teardown : function() {
		// print the XPath expression to give more information
		ok( typeof selector.getXpath() !== "undefined", "Finished with XPath: " + selector.getXpath());
		// assert that the selector really includes or excludes specified parts
		var coveredNodes = selector.select(document);
		selector.includedNodes.forEach(function(node) {
			ok(coveredNodes.contains(node), node.nodeName + " should be covered by the selector, since it was specified as an included item");
		});
		selector.excludedNodes.forEach(function(node) {
			ok(!coveredNodes.contains(node));
		});
	}
});

/**
 * Adds parts of the page to the list of included elements.
 * For testing, a jQuery selector is used to specifiy those elements.
 */
function include(jQuerySelector) {
	var domNodes = jQuery(jQuerySelector).get();
	domNodes.forEach(function(node) {
		selector.includedNodes.push(node);
	});
	ok(domNodes.length > 0, "Including " + domNodes.length + " node(s) via selector " + jQuerySelector);
}

test("No specifics => return empty XPath", function() {

	var xpath = selector.createXpathExpression();

	equal(xpath, "", "XPath should be empty");
});

test("Same class => use @class", function() {
	include(".news_article");

	var xpath = selector.createXpathExpression();

	equal(xpath, "//div[contains(concat(' ',@class,' '),' news_article ')]");
});

test("No class => use element name", function() {
	include("#news_article_1_headline");
	/*TODO next test: exclude("h2[not(../.news_article)][0]");
	* note: "purrfect article 1"
	*/

	var xpath = selector.createXpathExpression();

	equal(xpath, "//h2");
});
