#Feature: Money Transfer
#
#  Scenario: Transfer money between two accounts
#    Given the user "transferuser" is registered with password "Password123@"
#    And the user logs in with username "transferuser" and password "Password123@"
#    And the user creates a new account named "Primary" with initial balance 2000
#    And the user creates another account named "Savings" with initial balance 0
#    When the user transfers 500 from "Primary" to "Savings"
#    Then the money should be transferred successfully