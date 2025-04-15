package com.bader.miniBank.demo.controllers

import com.bader.miniBank.demo.models.KYC
import com.bader.miniBank.demo.services.KYCServices
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal
import java.time.LocalDate

@RestController
@RequestMapping("/api/kyc")
class KycController(private val kycServices: KYCServices) {

    @PostMapping
    fun createKyc(@RequestBody req: KycRequest): KYC {
        return kycServices.createProfile(
            userId = req.userId,
            firstName = req.firstName,
            lastName = req.lastName,
            dateOfBirth = req.dateOfBirth,
            salary = req.salary
        )
    }

    @GetMapping
    fun getAllKycProfiles(): List<KYC> {
        return kycServices.getAllProfiles()
    }
}

data class KycRequest(
    val userId: Long,
    val firstName: String,
    val lastName: String,
    val dateOfBirth: LocalDate,
    val salary: BigDecimal
)