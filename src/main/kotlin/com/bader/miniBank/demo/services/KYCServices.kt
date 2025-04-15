package com.bader.miniBank.demo.services

import com.bader.miniBank.demo.repo.KYCREPO
import com.bader.miniBank.demo.repo.UsersRepository
import com.bader.miniBank.demo.models.KYC
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.LocalDate

@Service
class KYCServices(
    private val kycRepo: KYCREPO,
    private val usersRepository: UsersRepository
) {
    fun createProfile(
        userId: Long,
        firstName: String,
        lastName: String,
        dateOfBirth: LocalDate,
        salary: BigDecimal
    ): KYC {
        val user = usersRepository.findById(userId).orElseThrow { Exception("User not found") }

        val profile = KYC(
            firstName = firstName,
            lastName = lastName,
            dateOfBirth = dateOfBirth,
            salary = salary,
            user = user
        )
        return kycRepo.save(profile)
    }

    fun getAllProfiles(): List<KYC> = kycRepo.findAll()
}