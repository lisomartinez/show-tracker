Feature: List Shows
  As a user
  I want to know which shows I am currently following
  So I know what I'm interested in

  Background:
    Given I am logged

  @ok
  Scenario: List Shows followed by an user
    Given I have a list of followed courses
    When I ask for my list of shows
    Then I get the list with shows.