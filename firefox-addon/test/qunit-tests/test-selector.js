"use strict";
var selector;

module("selector.generateXpath()", {
	setup : function() {
		selector = createSelector();
	},
	teardown : function() {
		// print the XPath expression to give more information
		ok( typeof selector.getXpath() !== "undefined", "Finished with XPath: " + selector.getXpath());
		// assert that the selector really includes or excludes specified parts
		var coveredNodes = selector.select(document);
		selector.includedNodes.forEach(function(node) {
			ok(help(coveredNodes).contains(node), node.nodeName + " should be covered by the selector, since it was specified as an included item");
		});
		selector.excludedNodes.forEach(function(node) {
			ok(!help(coveredNodes).contains(node));
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

	var xpath = selector.generateXpath(document);

	equal(xpath, "", "XPath should be empty");
});

test("Same class => use @class", function() {
	include(".news_article");

	var xpath = selector.generateXpath(document);

	equal(xpath, "//div[contains(concat(' ',@class,' '),' news_article ')]");
});

test("No class => use element name and parent class", function() {
	include("#news_article_1_headline");
	include("#news_article_2_headline");
	/*TODO next test: exclude("h2[not(../.news_article)][0]");
	* note: "purrfect article 1"
	*/

	var xpath = selector.generateXpath(document);

	equal(xpath, "//div[contains(concat(' ',@class,' '),' news_article ')]/h2");
});
