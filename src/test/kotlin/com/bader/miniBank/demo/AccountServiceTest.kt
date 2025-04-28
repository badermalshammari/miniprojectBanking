package com.bader.miniBank.demo

import com.bader.miniBank.demo.models.Account
import com.bader.miniBank.demo.models.Users
import com.bader.miniBank.demo.repo.AccountRepo
import com.bader.miniBank.demo.repo.UsersRepository
import com.bader.miniBank.demo.services.AccountService
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import java.math.BigDecimal
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class AccountServiceTest {

    private val accountRepo: AccountRepo = mock(AccountRepo::class.java)
    private val usersRepository: UsersRepository = mock(UsersRepository::class.java)
    private val accountService = AccountService(accountRepo, usersRepository)

    @Test
    fun `should create account successfully`() {
        val userId = 1L
        val username = "TestUser"
        val password = "Password1" // Assume valid password meets all restrictions
        val balance = BigDecimal(100)
        val user = Users(id = userId, username = username, password = password)
        val newAccount = Account(user = user, name = "TestAccount", balance = balance)

        // Mocking the behavior
        `when`(usersRepository.findById(userId)).thenReturn(Optional.of(user))
        `when`(accountRepo.findByUserId(userId)).thenReturn(emptyList()) // Ensure no existing accounts
        `when`(accountRepo.save(any(Account::class.java))).thenReturn(newAccount)

        // Call service method
        val createdAccount = accountService.createAccount(userId, "TestAccount", balance)

        // Assertions
        assertEquals("TestAccount", createdAccount.name)
        assertEquals(balance, createdAccount.balance)
        assertEquals(userId, createdAccount.user?.id)
    }

    @Test
    fun `should throw error when user already has 5 accounts`() {
        val userId = 1L
        val username = "TestUser"
        val password = "Password1" // Valid password
        val balance = BigDecimal(100)
        val user = Users(id = userId, username = username, password = password)
        val existingAccounts = listOf(
            Account(user = user, name = "Account1", balance = balance),
            Account(user = user, name = "Account2", balance = balance),
            Account(user = user, name = "Account3", balance = balance),
            Account(user = user, name = "Account4", balance = balance),
            Account(user = user, name = "Account5", balance = balance)
        )

        `when`(usersRepository.findById(userId)).thenReturn(Optional.of(user))
        `when`(accountRepo.findByUserId(userId)).thenReturn(existingAccounts)

        // Ensure that an exception is thrown when a user tries to create a 6th account
        val exception = assertFailsWith<ResponseStatusException> {
            accountService.createAccount(userId, "NewAccount", balance)
        }

        // Assert the exception
        assertEquals(HttpStatus.BAD_REQUEST, exception.statusCode)
        assertEquals("User already has 5 accounts.", exception.reason)
    }

    @Test
    fun `should close account successfully`() {
        val accountId = 1L
        val userId = 1L
        val user = Users(id = userId, username = "TestUser", password = "password")
        val account = Account(id = accountId, user = user, name = "TestAccount", balance = BigDecimal(100))

        // Mocking the behavior
        `when`(accountRepo.findById(accountId)).thenReturn(Optional.of(account))
        `when`(accountRepo.save(any(Account::class.java))).thenReturn(account.copy(isActive = false))

        // Call the closeAccount method
        accountService.closeAccount(accountId)

        // Verify the account is marked as closed (isActive = false)
        verify(accountRepo).save(account.copy(isActive = false))
    }
}