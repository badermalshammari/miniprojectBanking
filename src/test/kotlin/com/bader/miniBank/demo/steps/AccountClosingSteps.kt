//package com.bader.miniBank.demo.steps
//
//import io.cucumber.java.en.*
//import org.junit.jupiter.api.Assertions.*
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.boot.test.context.SpringBootTest
//import org.springframework.boot.test.web.client.TestRestTemplate
//import org.springframework.http.*
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//class AccountClosingSteps {
//
//    @Autowired lateinit var restTemplate: TestRestTemplate
//    private var token = ""
//    private var accountNumber: String = ""
//
//    @Given("a user with username {string} and password {string} is registered")
//    fun registerUser(username: String, password: String) {
//        val request = mapOf("username" to username, "password" to password)
//        restTemplate.postForEntity("/users/v1/register", request, String::class.java)
//    }
//
//    @Given("the user logs in with username {string} and password {string}")
//    fun loginUser(username: String, password: String) {
//        val request = mapOf("username" to username, "password" to password)
//        val headers = HttpHeaders().apply { contentType = MediaType.APPLICATION_JSON }
//        val response = restTemplate.postForEntity("/auth/login", HttpEntity(request, headers), Map::class.java)
//        token = response.body?.get("token") as String
//    }
//
//    @Given("the user creates an account named {string} with balance {int}")
//    fun createAccount(name: String, balance: Int) {
//        val body = mapOf("userId" to 1, "name" to name, "initialBalance" to balance)
//        val headers = HttpHeaders().apply {
//            contentType = MediaType.APPLICATION_JSON
//            setBearerAuth(token)
//        }
//        val response = restTemplate.postForEntity("/accounts/v1/accounts", HttpEntity(body, headers), Map::class.java)
//        accountNumber = response.body?.get("accountNumber").toString()
//    }
//
//    @When("the user closes the account")
//    fun closeAccount() {
//        val headers = HttpHeaders().apply { setBearerAuth(token) }
//        val response = restTemplate.postForEntity(
//            "/accounts/v1/accounts/$accountNumber/close",
//            HttpEntity(null, headers),
//            String::class.java
//        )
//        assertEquals(HttpStatus.OK, response.statusCode)
//    }
//
//    @Then("the account should be closed successfully")
//    fun closeSuccess() {
//        println("✅ Account closed successfully.")
//    }
//}