package com.bader.miniBank.demo.repo

import com.bader.miniBank.demo.models.Transaction
import org.springframework.data.jpa.repository.JpaRepository

interface TransactionRepo : JpaRepository<Transaction, Long>