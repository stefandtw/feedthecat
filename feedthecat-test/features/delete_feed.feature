@dev
Feature: Delete a feed
	
	As a user I want to delete a feed that is no longer relevant.


Background:

Given all existing feeds have been deleted
And a feed called 'feed 1'
And a feed called 'feed 2'


Scenario: Deleted feeds disappear from the list

Given feed list page
When I click on the delete button next to 'feed 1'
Then show a feed list with 1 entries
And feed list has a link 'feed 2'
