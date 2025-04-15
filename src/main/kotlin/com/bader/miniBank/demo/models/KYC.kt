package com.bader.miniBank.demo.models

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDate

@Entity
@Table(name = "kyc_profiles")
data class KYC(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val firstName: String = "",
    val lastName: String = "",
    val dateOfBirth: LocalDate = LocalDate.now(),
    val salary: BigDecimal = BigDecimal.ZERO,

    @OneToOne
    @JoinColumn(name = "user_id")
    val user: Users? = null
) {
    constructor() : this(0, "", "", LocalDate.now(), BigDecimal.ZERO, null)
}