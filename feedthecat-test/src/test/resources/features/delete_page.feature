Feature: Delete a filtered page
	
	As a user I want to delete a filtered page that is no longer relevant.


Background:

Given all existing pages have been deleted
And a page called 'page 1'
And a page called 'page 2'


Scenario: Deleted pages disappear from the list

Given page list
When I click on the delete button next to 'page 1'
Then show a page list with 1 entries
And page list has a link 'page 2'
