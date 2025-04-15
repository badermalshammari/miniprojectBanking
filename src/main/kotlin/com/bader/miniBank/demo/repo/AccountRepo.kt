package com.bader.miniBank.demo.repo

import com.bader.miniBank.demo.models.Account
import org.springframework.data.jpa.repository.JpaRepository

interface AccountRepo : JpaRepository<Account, Long> {
    fun findByUserId(userId: Long): List<Account>
}