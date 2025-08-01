package com.bader.miniBank.demo

import com.bader.miniBank.demo.models.Role
import com.bader.miniBank.demo.models.Users
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.*
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

@SpringBootTest(
    classes = [DemoApplication::class],  // Correct to point to your main application class
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = ["spring.profiles.active=test"]
)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UsersControllerTests {

    @LocalServerPort
    private var port: Int = 0

    @Autowired
    lateinit var restTemplate: TestRestTemplate

    private fun endpoint(path: String) = "http://localhost:$port$path"

    @Test
    fun createUserTest() {
        val user = Users(username = "NewUser123", password = "SecurePassword1@", role = Role.USER)  // Valid password

        // Send a POST request to create a user
        val result = restTemplate.postForEntity(endpoint("/api/users/register"), user, Users::class.java)

        // Assert that the status code is 200 (OK) and that the username matches
        assertEquals(HttpStatus.OK, result.statusCode)  // Expect 200 OK
        assertEquals("NewUser123", result.body?.username)    // Ensure the username matches
        assertNotNull(result.body?.id)  // Ensure the ID is not null (it should be generated by the database)
    }

    @Test
    fun getAllUsersTest() {
        // Create a user to test the retrieval
        val user = Users(username = "UserToGet", password = "Password123@", role = Role.USER)
        restTemplate.postForEntity(endpoint("/api/users/register"), user, Users::class.java)

        // Retrieve all users
        val response = restTemplate.exchange(
            endpoint("/api/users"),
            HttpMethod.GET,
            HttpEntity(String),
            Array<Users>::class.java
        )

        // Assert that the response is successful and not empty
        assertEquals(HttpStatus.OK, response.statusCode)
        val users = response.body
        assertNotNull(users)  // Ensure the body is not null
        assertTrue(users!!.isNotEmpty())  // Check that the list of users is not empty
    }
}