package com.bader.miniBank.demo.controllers

import com.bader.miniBank.demo.models.Transaction
import com.bader.miniBank.demo.services.TransactionsServices
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal

data class TransferRequest(val fromAccountId: Long, val toAccountId: Long, val amount: BigDecimal)

@RestController
@RequestMapping("/api/transfer")
class TransactionController(private val transactionsServices: TransactionsServices) {

    @PostMapping("/v1/transfer")
    fun transfer(@RequestBody req: TransferRequest): Transaction {
        return transactionsServices.transfer(req.fromAccountId, req.toAccountId, req.amount)
    }
    @GetMapping("/all")
    fun getAllTransactions(): List<Transaction> {
        return transactionsServices.getAllTransactions()
    }
}