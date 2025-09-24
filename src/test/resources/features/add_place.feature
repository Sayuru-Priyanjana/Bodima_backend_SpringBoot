Feature: Add Place

  Scenario: Successfully add a valid place
    Given a user with id 1 exists
    When the user adds a place with name "Test Place" and rent 5000.0
    Then the place should be saved successfully

  Scenario: Reject invalid place with empty name
    Given a user with id 1 exists
    When the user adds a place with an empty name
    Then the system should reject the place
