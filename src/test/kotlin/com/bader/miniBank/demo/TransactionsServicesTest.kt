package com.bader.miniBank.demo

import com.bader.miniBank.demo.models.Account
import com.bader.miniBank.demo.repo.AccountRepo
import com.bader.miniBank.demo.repo.TransactionRepo
import com.bader.miniBank.demo.services.TransactionsServices
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.mockito.Mockito.*
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import java.math.BigDecimal
import java.util.*

class TransactionsServicesTest {

    private val transactionRepo: TransactionRepo = mock(TransactionRepo::class.java)
    private val accountRepo: AccountRepo = mock(AccountRepo::class.java)
    private val transactionsServices = TransactionsServices(transactionRepo, accountRepo)

    @Test
    fun `should transfer money between accounts successfully`() {
        val fromAccount = Account(id = 1, balance = BigDecimal(100), isActive = true)
        val toAccount = Account(id = 2, balance = BigDecimal(50), isActive = true)

        // Mocking the retrieval of accounts from accountRepo
        `when`(accountRepo.findById(1L)).thenReturn(Optional.of(fromAccount))
        `when`(accountRepo.findById(2L)).thenReturn(Optional.of(toAccount))

        // Mocking the saveAll() method to return the updated accounts
        `when`(accountRepo.saveAll(anyList())).thenAnswer {
            val updatedAccounts = it.arguments[0] as List<Account>
            updatedAccounts.map { account ->
                if (account.id == 1L) {
                    // Update the balance for the source account
                    account.copy(balance = BigDecimal(70))
                } else if (account.id == 2L) {
                    // Update the balance for the destination account
                    account.copy(balance = BigDecimal(80))
                } else {
                    account
                }
            }
        }

        // Perform the transfer
        val amount = BigDecimal(30)
        val transaction = transactionsServices.transfer(1L, 2L, amount)

        // Safely retrieve the updated accounts
        val updatedFromAccount: Account = accountRepo.findById(1L).orElseThrow {
            ResponseStatusException(HttpStatus.BAD_REQUEST, "Source account not found")
        }
        val updatedToAccount: Account = accountRepo.findById(2L).orElseThrow {
            ResponseStatusException(HttpStatus.BAD_REQUEST, "Destination account not found")
        }

        // Ensure that saveAll was correctly invoked with non-null objects
        verify(accountRepo).saveAll(anyList()) // Verify that saveAll() was called

        // Assert the balances were updated correctly after the transfer
        assertEquals(BigDecimal(70), updatedFromAccount.balance) // After transfer, balance should be reduced
        assertEquals(BigDecimal(80), updatedToAccount.balance) // After transfer, balance should be increased

        // Verify the transaction object was created and returned
        assertNotNull(transaction)
        assertEquals(amount, transaction.amount)

        // Ensure that the fromAccount and toAccount in the transaction are not null
        assertNotNull(transaction.fromAccount)
        assertNotNull(transaction.toAccount)

        // Compare the ids of the accounts in the transaction, not the full object
        assertEquals(1L, transaction.fromAccount?.id) // Ensure the fromAccount id matches
        assertEquals(2L, transaction.toAccount?.id)   // Ensure the toAccount id matches
    }

    @Test
    fun `should throw exception when source account does not exist`() {
        `when`(accountRepo.findById(1L)).thenReturn(Optional.empty())

        val exception = assertThrows(ResponseStatusException::class.java) {
            transactionsServices.transfer(1L, 2L, BigDecimal(30))
        }

        assertEquals(HttpStatus.BAD_REQUEST, exception.statusCode)
        assertEquals("Source account not found", exception.reason)
    }

    @Test
    fun `should throw exception when insufficient balance`() {
        val fromAccount = Account(id = 1, balance = BigDecimal(100), isActive = true)
        val toAccount = Account(id = 2, balance = BigDecimal(50), isActive = true)

        `when`(accountRepo.findById(1L)).thenReturn(Optional.of(fromAccount))
        `when`(accountRepo.findById(2L)).thenReturn(Optional.of(toAccount))

        val exception = assertThrows(ResponseStatusException::class.java) {
            transactionsServices.transfer(1L, 2L, BigDecimal(200))
        }

        assertEquals(HttpStatus.BAD_REQUEST, exception.statusCode)
        assertEquals("Insufficient balance in source account.", exception.reason)
    }
}