package com.bader.miniBank.demo.steps

import com.bader.miniBank.demo.repo.AccountRepo
import com.bader.miniBank.demo.repo.KYCREPO
import com.bader.miniBank.demo.repo.TransactionRepo
import com.bader.miniBank.demo.repo.UsersRepository
import io.cucumber.java.Before
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.*

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LoginSteps {

    @Autowired lateinit var restTemplate: TestRestTemplate
    @Autowired lateinit var userRepository: UsersRepository
    @Autowired lateinit var accountRepository: AccountRepo
    @Autowired lateinit var transactionRepository: TransactionRepo
    @Autowired lateinit var kycRepository: KYCREPO

    private var response: ResponseEntity<String>? = null

    data class LoginRequest(val username: String, val password: String)
    data class RegisterRequest(val username: String, val password: String)

    @Before
    fun cleanDatabase() {
        println("Cleaning DB before scenario")
        transactionRepository.deleteAll()
        accountRepository.deleteAll()
        kycRepository.deleteAll()
        userRepository.deleteAll()
        println("DB cleaned")
    }

    @When("I send a login request with username {string} and password {string}")
    fun sendLoginRequest(username: String, password: String) {
        val request = LoginRequest(username, password)
        val headers = HttpHeaders().apply {
            contentType = MediaType.APPLICATION_JSON
        }
        val entity = HttpEntity(request, headers)

        response = restTemplate.postForEntity("/auth/login", entity, String::class.java)

        println("Sent login request to /auth/login")
        println("Payload: $request")
        println("Status Code: ${response?.statusCode}")
        println("Response Body: ${response?.body}")
    }

    @When("register a new user with username {string} and password {string}")
    fun registerBeforeLogin(username: String, password: String) {
        println("Creating user $username before login")
        val request = RegisterRequest(username, password)
        val headers = HttpHeaders().apply {
            contentType = MediaType.APPLICATION_JSON
        }
        val entity = HttpEntity(request, headers)
        val registerResponse = restTemplate.postForEntity("/api/users/register", entity, String::class.java)
        println("Registration Status: ${registerResponse.statusCode}")

        println("Logging in after registration")
        sendLoginRequest(username, password)
    }

    @Then("the login response status should be {int}")
    fun checkLoginStatus(expectedStatus: Int) {
        assertEquals(HttpStatusCode.valueOf(expectedStatus), response?.statusCode)
    }

    @Then("the response should contain a JWT token")
    fun checkJwtToken() {
        val body = response?.body ?: ""
        assertTrue(body.contains("token") || body.contains("accessToken"), "JWT token not found in response: $body")
    }
}