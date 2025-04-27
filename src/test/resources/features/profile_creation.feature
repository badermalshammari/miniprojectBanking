#Feature: KYC Profile Creation
#
#  Scenario: Successfully create a KYC profile
#    Given I register a new user with username "testuser" and password "Test1234@"
#    And I login with username "testuser" and password "Test1234@"
#    When I create a new KYC profile for userId 1 with firstName "John", lastName "Doe", dateOfBirth "1990-01-01", and salary 5000.0
#    Then The KYC profile creation should be successful