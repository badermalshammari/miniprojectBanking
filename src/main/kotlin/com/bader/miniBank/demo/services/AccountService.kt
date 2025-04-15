package com.bader.miniBank.demo.services

import com.bader.miniBank.demo.models.Account
import com.bader.miniBank.demo.repo.AccountRepo
import com.bader.miniBank.demo.repo.UsersRepository
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class AccountService(
    private val accountRepo: AccountRepo,
    private val usersRepository: UsersRepository
) {
    fun createAccount(userId: Long, name: String, balance: BigDecimal): Account {
        val user = usersRepository.findById(userId)
            .orElseThrow { Exception("User not found") }

        val newAccount = Account(
            user = user,
            name = name,
            balance = balance
        )

        return accountRepo.save(newAccount)
    }

    fun closeAccount(accountId: Long) {
        val acc = accountRepo.findById(accountId)
            .orElseThrow { Exception("Account not found") }

        val updated = acc.copy(isActive = false)
        accountRepo.save(updated)
    }

    fun getAllAccounts(): List<Account> = accountRepo.findAll()

    fun getUserAccounts(userId: Long): List<Account> =
        accountRepo.findByUserId(userId)
}
