package com.bader.miniBank.demo.models

import jakarta.persistence.*
import java.math.BigDecimal
import java.util.*

@Entity
@Table(name = "accounts")
data class Account(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val name: String = "",

    val balance: BigDecimal = BigDecimal.ZERO,

    val isActive: Boolean = true,

    val accountNumber: String = UUID.randomUUID().toString(),

    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: Users? = null
)
{
    constructor() : this(
        id = 0,
        name = "",
        balance = BigDecimal.ZERO,
        isActive = true,
        accountNumber = UUID.randomUUID().toString(),
        user = null
    )
}