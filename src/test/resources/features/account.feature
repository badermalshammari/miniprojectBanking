Feature: Account creation

  Scenario: Account creation should fail without authentication
    When I send a POST request to /api/accounts/v1/accounts without token
    Then the response status should be 403

  Scenario: Create account after user registration and login
    Given a user is registered with username "accuser" and password "Secure123@"
    And the user logs in with username "accuser" and password "Secure123@"
    When I create an account with name "Savings" and initial balance 100.00
    Then the account should be created successfully with status 200