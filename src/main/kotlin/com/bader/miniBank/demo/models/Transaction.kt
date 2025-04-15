package com.bader.miniBank.demo.models

import jakarta.persistence.*
import java.math.BigDecimal



@Entity
@Table(name = "transactions")
data class Transaction(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val amount: BigDecimal,

    @ManyToOne
    @JoinColumn(name = "source_account")
    val fromAccount: Account? = null,

    @ManyToOne
    @JoinColumn(name = "destination_account")
    val toAccount: Account? = null
)
{
    constructor() : this(0, BigDecimal.ZERO, null, null)
}