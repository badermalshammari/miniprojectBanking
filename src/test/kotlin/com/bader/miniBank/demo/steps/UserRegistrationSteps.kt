package com.bader.miniBank.demo.steps

import com.bader.miniBank.demo.repo.AccountRepo
import com.bader.miniBank.demo.repo.KYCREPO
import com.bader.miniBank.demo.repo.TransactionRepo
import com.bader.miniBank.demo.repo.UsersRepository
import io.cucumber.java.Before
import io.cucumber.java.en.When
import io.cucumber.java.en.Then
import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.*

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserRegistrationSteps {

    @Autowired lateinit var restTemplate: TestRestTemplate
    @Autowired lateinit var userRepository: UsersRepository
    @Autowired lateinit var accountRepository: AccountRepo
    @Autowired lateinit var transactionRepository: TransactionRepo
    @Autowired lateinit var kycRepository: KYCREPO

    private var response: ResponseEntity<String>? = null

    data class RegistrationRequest(val username: String, val password: String)

    @Before
    fun beforeEachScenario() {
        println("Cleaning DB before scenario")
        transactionRepository.deleteAll()
        accountRepository.deleteAll()
        kycRepository.deleteAll()
        userRepository.deleteAll()
        println("DB cleaned")
    }

    @When("I send a registration request with username {string} and password {string}")
    fun sendRegistrationRequest(username: String, password: String) {
        val request = RegistrationRequest(username, password)
        val headers = HttpHeaders().apply { contentType = MediaType.APPLICATION_JSON }
        val entity = HttpEntity(request, headers)
        response = restTemplate.postForEntity("/api/users/register", entity, String::class.java)
        println("Status: ${response?.statusCode}, Body: ${response?.body}")
    }

    @When("a user with username {string} and password {string} already exists")
    fun createExistingUser(username: String, password: String) {
        println("Creating pre-existing user")
        val request = RegistrationRequest(username, password)
        val headers = HttpHeaders().apply { contentType = MediaType.APPLICATION_JSON }
        val entity = HttpEntity(request, headers)
        restTemplate.postForEntity("/api/users/register", entity, String::class.java)
    }

    @Then("the response status should be {int}")
    fun checkStatusCode(expectedStatusCode: Int) {
        assertEquals(HttpStatusCode.valueOf(expectedStatusCode), response?.statusCode)
    }

    @Then("the response should be not null")
    fun confirmResponse() {
        assertNotNull(response?.body)
    }
}