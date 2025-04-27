//package com.bader.miniBank.demo.steps
//
//import io.cucumber.java.Before
//import io.cucumber.java.en.When
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.boot.test.web.client.TestRestTemplate
//import org.springframework.http.ResponseEntity
//
//class CommonSteps {
//
//    @Autowired
//    lateinit var restTemplate: TestRestTemplate
//
//    var response: ResponseEntity<String>? = null
//    var token: String? = null
//
//    data class RegistrationRequest(val username: String, val password: String)
//
//    // User Registration Step
//    @When("I register a new user with username {string} and password {string}")
//    fun registerNewUser(username: String, password: String) {
//        val request = mapOf("username" to username, "password" to password)
//        response = restTemplate.postForEntity("/api/users/v1/register", request, String::class.java)
//    }
//
//    // User Login Step
//    @When("I login with username {string} and password {string}")
//    fun loginUser(username: String, password: String) {
//        val request = mapOf("username" to username, "password" to password)
//        val loginResponse = restTemplate.postForEntity("/auth/login", request, String::class.java)
//
//        println("Login response status: ${loginResponse.statusCode}")
//        println("Login response body: ${loginResponse.body}")
//
//        token = loginResponse.body?.let { extractToken(it) }
//    }
//
//    // Token Extraction Helper
//    fun extractToken(responseBody: String): String {
//        val tokenRegex = "\"token\":\"(.*?)\"".toRegex()
//        val matchResult = tokenRegex.find(responseBody)
//        return matchResult?.groupValues?.get(1) ?: throw IllegalStateException("Token not found in response")
//    }
//}