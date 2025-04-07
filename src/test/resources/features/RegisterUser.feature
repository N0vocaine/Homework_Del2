Feature: User Registration on Basketball England Website
  This feature tests the registration form functionality for creating a new supporter account.

  Scenario: Successful user registration
    Given I navigate to the Basketball England registration page
    When I enter valid member details
    And I enter matching passwords
    And I accept the terms and conditions
    Then I submit the registration form
    And I should see a success message


  Scenario: User registration with missing last name
    Given I navigate to the Basketball England registration page
    When I enter member details without a last name
    And I enter matching passwords
    And I submit the registration form
    Then I should see an error message for the missing last name

  Scenario: User registration with non-matching passwords
    Given I navigate to the Basketball England registration page
    When I enter valid member details
    And I enter non-matching passwords
    And I submit the registration form
    Then I should see an error message for password mismatch

  Scenario: User registration without accepting terms and conditions
    Given I navigate to the Basketball England registration page
    When I enter valid member details
    And I enter matching passwords
    And I do not accept the terms and conditions
    And I submit the registration form
    Then I should see an error message for unaccepted terms

