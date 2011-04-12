Feature: List all pages
	
	As a visitor I want to see all filtered pages created in the application.


Background:

Given all existing pages have been deleted


Scenario: Zero pages are available

Given page list
Then show a page list with 0 entries
	

Scenario: Two pages are available

Given a page called 'page 1'
And a page called 'page 2'
And page list
Then show a page list with 2 entries
And page list has a link 'page 1'
And page list has a link 'page 2'
And link 'page 1' leads to the page 'page 1'
And link 'page 2' leads to the page 'page 2'
