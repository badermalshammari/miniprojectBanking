package com.bader.miniBank.demo.services

import com.bader.miniBank.demo.models.Account
import com.bader.miniBank.demo.repo.AccountRepo
import com.bader.miniBank.demo.repo.UsersRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.math.BigDecimal

@Service
class AccountService(
    private val accountRepo: AccountRepo,
    private val usersRepository: UsersRepository
) {
    fun createAccount(userId: Long, name: String, balance: BigDecimal): Account {
        val user = usersRepository.findById(userId)
            .orElseThrow { ResponseStatusException(HttpStatus.BAD_REQUEST, "Source account not found") }

        val existingAccounts = accountRepo.findByUserId(userId)
        if (existingAccounts.size >= 5) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "User already has 5 accounts.")
        }

        if (balance < BigDecimal.ZERO) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Initial balance cannot be negative")
        }

        val newAccount = Account(
            user = user,
            name = name,
            balance = balance
        )

        return accountRepo.save(newAccount)
    }

    fun closeAccount(accountId: Long) {
        val acc = accountRepo.findById(accountId)
            .orElseThrow { ResponseStatusException(HttpStatus.BAD_REQUEST, "Account not found") }

        val updated = acc.copy(isActive = false)
        accountRepo.save(updated)
    }

    fun getAllAccounts(): List<Account> = accountRepo.findAll()

    fun getUserAccounts(userId: Long): List<Account> =
        accountRepo.findByUserId(userId)
}