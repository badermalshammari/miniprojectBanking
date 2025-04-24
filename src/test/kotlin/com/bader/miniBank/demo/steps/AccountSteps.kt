package com.bader.miniBank.demo.steps

import com.bader.miniBank.demo.steps.UserRegistrationSteps.RegistrationRequest
import com.bader.miniBank.demo.steps.LoginSteps.LoginRequest
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.*
import java.math.BigDecimal

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AccountSteps {

    @Autowired
    lateinit var restTemplate: TestRestTemplate

    private var token: String? = null
    private var response: ResponseEntity<String>? = null

    data class CreateAccountRequest(val userId: Long, val name: String, val initialBalance: BigDecimal)

    @Given("a user is registered with username {string} and password {string}")
    fun registerUser(username: String, password: String) {
        val request = RegistrationRequest(username, password)
        val headers = HttpHeaders().apply { contentType = MediaType.APPLICATION_JSON }
        val entity = HttpEntity(request, headers)
        restTemplate.postForEntity("/api/users/register", entity, String::class.java)
    }

    @Given("the user logs in with username {string} and password {string}")
    fun loginUser(username: String, password: String) {
        val request = LoginRequest(username, password)
        val headers = HttpHeaders().apply { contentType = MediaType.APPLICATION_JSON }
        val entity = HttpEntity(request, headers)
        val loginResponse = restTemplate.postForEntity("/auth/login", entity, String::class.java)

        val tokenBody = loginResponse.body ?: ""
        token = Regex("\"token\":\"(.*?)\"").find(tokenBody)?.groupValues?.get(1)
    }

    @When("I create an account with name {string} and initial balance {double}")
    fun createAccount(name: String, balance: Double) {
        val headers = HttpHeaders().apply {
            contentType = MediaType.APPLICATION_JSON
            setBearerAuth(token)
        }
        val userId = 1L
        val request = CreateAccountRequest(userId, name, BigDecimal(balance))
        val entity = HttpEntity(request, headers)
        response = restTemplate.postForEntity("/api/accounts/v1/accounts", entity, String::class.java)
    }

    @When("I send a POST request to /api/accounts/v1/accounts without token")
    fun sendPostWithoutToken() {
        val request = CreateAccountRequest(1L, "Unauthorized", BigDecimal(50))
        val headers = HttpHeaders().apply { contentType = MediaType.APPLICATION_JSON }
        val entity = HttpEntity(request, headers)
        response = restTemplate.postForEntity("/api/accounts/v1/accounts", entity, String::class.java)
    }

    @Then("the account should be created successfully with status 200")
    fun verifyAccountCreated() {
        assertEquals(HttpStatus.OK, response?.statusCode)
        assertNotNull(response?.body)
    }

    @Then("the response status should be {int}")
    fun verifyStatusCode(expectedStatusCode: Int) {
        assertEquals(HttpStatus.valueOf(expectedStatusCode), response?.statusCode)
    }
}