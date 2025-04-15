package com.bader.miniBank.demo.services

import com.bader.miniBank.demo.models.Transaction
import com.bader.miniBank.demo.repo.AccountRepo
import com.bader.miniBank.demo.repo.TransactionRepo
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.math.BigDecimal

@Service
class TransactionsServices(
    private val transactionRepo: TransactionRepo,
    private val accountRepo: AccountRepo
) {
    fun transfer(fromAccountId: Long, toAccountId: Long, amount: BigDecimal): Transaction {
        val from = accountRepo.findById(fromAccountId)
            .orElseThrow { ResponseStatusException(HttpStatus.BAD_REQUEST, "Source account not found") }

        val to = accountRepo.findById(toAccountId)
            .orElseThrow { ResponseStatusException(HttpStatus.BAD_REQUEST, "Destination account not found") }

        if (!from.isActive || !to.isActive) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "One of the accounts is inactive.")
        }

        if (from.balance < amount) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient balance in source account.")
        }

        val updatedFrom = from.copy(balance = from.balance - amount)
        val updatedTo = to.copy(balance = to.balance + amount)

        accountRepo.saveAll(listOf(updatedFrom, updatedTo))

        val transaction = Transaction(
            fromAccount = updatedFrom,
            toAccount = updatedTo,
            amount = amount
        )

        return transactionRepo.save(transaction)
    }

    fun getAllTransactions(): List<Transaction> {
        return transactionRepo.findAll()
    }
}