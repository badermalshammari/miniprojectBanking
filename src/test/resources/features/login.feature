Feature: Login functionality with JWT authentication

  Scenario: Login with non-existing user should fail
    When I send a login request with username "ghost" and password "NoPass123@"
    Then the login response status should be 403

  Scenario: Register then login successfully
    When register a new user with username "newuser" and password "Secure123@"
    Then the login response status should be 200
    And the response should contain a JWT token