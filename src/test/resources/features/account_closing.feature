#Feature: Account Closing
#
#  Scenario: Successfully close an existing account
#    Given a user with username "closerUser" and password "Password1" is registered
#    And the user logs in with username "closerUser" and password "Password123@"
#    And the user creates an account named "Temporary" with balance 300
#    When the user closes the account
#    Then the account should be closed successfully