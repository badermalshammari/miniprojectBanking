package com.bader.miniBank.demo

import com.bader.miniBank.demo.models.KYC
import com.bader.miniBank.demo.repo.KYCREPO
import com.bader.miniBank.demo.repo.UsersRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.core.ParameterizedTypeReference
import java.math.BigDecimal
import java.time.LocalDate
import org.junit.jupiter.api.Assertions.assertEquals

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class KycControllerTest {

    @Autowired
    lateinit var restTemplate: TestRestTemplate

    @Autowired
    lateinit var kycRepo: KYCREPO

    @Autowired
    lateinit var usersRepository: UsersRepository

    @BeforeEach
    fun setUp() {
        kycRepo.deleteAll() // Clean the database before each test
    }

    @Test
    fun `should create KYC profile`() {
        // Prepare data to test the creation of a KYC profile
        val userId = 1L // Ensure that the user exists in the database
        val requestBody = mapOf(
            "userId" to userId,
            "firstName" to "John",
            "lastName" to "Doe",
            "dateOfBirth" to LocalDate.of(1990, 1, 1),
            "salary" to BigDecimal(5000)
        )

        // Send the POST request to create the KYC profile
        val response: ResponseEntity<KYC> = restTemplate.postForEntity("/api/kyc/create", requestBody, KYC::class.java)

        // Validate the response
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals("John", response.body?.firstName) // Check if the first name is correct
        assertEquals("Doe", response.body?.lastName)  // Check if the last name is correct
        assertEquals(BigDecimal(5000), response.body?.salary) // Check if the salary is correct
    }

    @Test
    fun `should get all KYC profiles`() {
        // Insert a KYC profile into the database before testing
        val userId = 1L
        kycRepo.save(KYC(firstName = "Jane", lastName = "Doe", dateOfBirth = LocalDate.of(1985, 5, 20), salary = BigDecimal(6000), user = usersRepository.getById(userId)))

        // Use ParameterizedTypeReference to specify the type of response
        val response: ResponseEntity<List<KYC>> = restTemplate.exchange(
            "/api/kyc/getAll",
            org.springframework.http.HttpMethod.GET,
            null,
            object : ParameterizedTypeReference<List<KYC>>() {}
        )

        // Validate the response
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(1, response.body?.size) // Check that we have exactly 1 profile in the response
    }
}