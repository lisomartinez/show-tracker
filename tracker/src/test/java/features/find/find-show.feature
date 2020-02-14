Feature: find show
  As a User
  I want to search for a show
  So I can view its information

  Scenario: find show by title
    Given There is a list of existing shows
    When I ask for a show by title
    Then I get the show's information