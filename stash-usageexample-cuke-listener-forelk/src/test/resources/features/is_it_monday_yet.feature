@mondayfeature @mf
Feature: Is it Monday yet?
  Everybody wants to know when it's Monday

  @monscenario
  Scenario: Sunday ain't Monday
    Given today is Sunday
    When I ask whether it's "Monday" yet
    Then I should be told "Nope"