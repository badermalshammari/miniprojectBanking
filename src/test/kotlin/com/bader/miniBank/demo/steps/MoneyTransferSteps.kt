//package com.bader.miniBank.demo.steps
//
//import io.cucumber.java.en.Then
//import io.cucumber.java.en.When
//import org.junit.jupiter.api.Assertions.assertEquals
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.boot.test.context.SpringBootTest
//import org.springframework.boot.test.web.client.TestRestTemplate
//import org.springframework.http.*
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//class MoneyTransferSteps {
//
//    @Autowired
//    lateinit var restTemplate: TestRestTemplate
//
//    private var response: ResponseEntity<String>? = null
//
//    data class TransferRequest(val fromAccountNumber: String, val toAccountNumber: String, val amount: java.math.BigDecimal)
//
////    @When("the user transfers {int} from {string} to {string}")
////    fun transferMoney(amount: Int, fromAccount: String, toAccount: String) {
////        val transferRequest = TransferRequest(
////            fromAccountNumber = fromAccount,
////            toAccountNumber = toAccount,
////            amount = amount.toBigDecimal()
////        )
////        val headers = HttpHeaders().apply {
////            contentType = MediaType.APPLICATION_JSON
////            setBearerAuth(CommonSteps.token!!)
////        }
////        val entity = HttpEntity(transferRequest, headers)
////        response = restTemplate.postForEntity("/transfer/v1/transfer", entity, String::class.java)
////    }
//
////    @Then("the money should be transferred successfully")
////    fun verifyTransfer() {
////        assertEquals(HttpStatus.OK, response?.statusCode)
////    }
//}