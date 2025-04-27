//package com.bader.miniBank.demo.steps
//
//import io.cucumber.java.en.When
//import io.cucumber.java.en.Then
//import org.junit.jupiter.api.Assertions.*
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.boot.test.web.client.TestRestTemplate
//import org.springframework.http.ResponseEntity
//
//class KycCreationSteps {
//
//    @Autowired
//    lateinit var restTemplate: TestRestTemplate
//
//    var response: ResponseEntity<String>? = null
//
//    // Create KYC profile after login
//    @When("I create a new KYC profile for userId {long} with firstName {string}, lastName {string}, dateOfBirth {string}, and salary {double}")
//    fun createKycProfile(userId: Long, firstName: String, lastName: String, dateOfBirth: String, salary: Double) {
//        val kycRequest = mapOf("userId" to userId, "firstName" to firstName, "lastName" to lastName, "dateOfBirth" to dateOfBirth, "salary" to salary)
//        response = restTemplate.postForEntity("/api/users/v1/kyc", kycRequest, String::class.java)
//    }
//
//    @Then("The KYC profile creation should be successful")
//    fun verifyKycCreation() {
//        assertEquals(200, response?.statusCodeValue)
//    }
//}