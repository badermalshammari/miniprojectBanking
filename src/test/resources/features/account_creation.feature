#Feature: Account Creation
#
#  Scenario: Successfully create a new account
#    Given I register a new user with username "testuser" and password "Test1234!"
#    And I login with username "testuser" and password "Test1234@"
#    When I create a new account with userId 1, name "Test Account", and initialBalance 1000.0
#    Then The account creation should be successful