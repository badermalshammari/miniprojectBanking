Feature: User Registration

  Scenario: Register new user
    When I send a registration request with username "newuser" and password "Secure123@"
    Then the response status should be 200
    And the response should be not null

  Scenario: user already exist
    When a user with username "newuser" and password "Secure123@" already exists
    And I send a registration request with username "newuser" and password "Secure123@"
    Then the response status should be 409
    And the response should be not null