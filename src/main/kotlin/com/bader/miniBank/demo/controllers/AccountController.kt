package com.bader.miniBank.demo.controllers

import com.bader.miniBank.demo.models.Account
import com.bader.miniBank.demo.services.AccountService
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal

@RestController
@RequestMapping("/api/accounts")
class AccountController(private val accountService: AccountService) {


    @PostMapping("/v1/accounts")
    fun createAccount(@RequestBody request: CreateAccountRequest): Account =
        accountService.createAccount(
            userId = request.userId,
            name = request.name,
            balance = request.initialBalance
        )

    @DeleteMapping("/close/{accountId}")
    fun closeAccount(@PathVariable accountId: Long) =
        accountService.closeAccount(accountId)

    @GetMapping("/user/{userId}")
    fun getUserAccounts(@PathVariable userId: Long): List<Account> =
        accountService.getUserAccounts(userId)

    @GetMapping("/v1/accounts")
    fun getAllAccounts(): Map<String, List<Account>> {
        val allAccounts = accountService.getAllAccounts()
        return mapOf("accounts" to allAccounts)
    }
}
data class CreateAccountRequest(
    val userId: Long,
    val name: String,
    val initialBalance: BigDecimal
)