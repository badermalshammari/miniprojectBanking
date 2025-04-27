//package com.bader.miniBank.demo.steps
//
//import io.cucumber.java.en.When
//import io.cucumber.java.en.Then
//import org.junit.jupiter.api.Assertions.*
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.boot.test.web.client.TestRestTemplate
//import org.springframework.http.ResponseEntity
//
//
//class AccountCreationSteps {
//
//    @Autowired
//    lateinit var restTemplate: TestRestTemplate
//
//    var response: ResponseEntity<String>? = null
//
//    // Create account after login
//    @When("I create a new account with userId {long}, name {string}, and initialBalance {double}")
//    fun createAccount(userId: Long, name: String, initialBalance: Double) {
//        val accountRequest = mapOf("userId" to userId, "name" to name, "initialBalance" to initialBalance)
//        response = restTemplate.postForEntity("/api/accounts/v1/accounts", accountRequest, String::class.java)
//    }
//
//    @Then("The account creation should be successful")
//    fun verifyAccountCreation() {
//        assertEquals(201, response?.statusCodeValue)
//    }
//}