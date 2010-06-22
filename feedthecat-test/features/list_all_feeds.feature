@dev
Feature: List all feeds
	
	As a visitor I want to see all feeds created in the application.


Background:

Given all existing feeds have been deleted


Scenario: Zero feeds are available

Given feed list page
Then show a feed list with 0 entries
	

Scenario: Two feeds are available

Given a feed called 'feed 1'
And a feed called 'feed 2'
And feed list page
Then show a feed list with 2 entries
And feed list has a link 'feed 1'
And feed list has a link 'feed 2'
And link 'feed 1' leads to the feed 'feed 1'
And link 'feed 2' leads to the feed 'feed 2'
