package com.bader.miniBank.demo.services

import com.bader.miniBank.demo.models.KYC
import com.bader.miniBank.demo.repo.KYCREPO
import com.bader.miniBank.demo.repo.UsersRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.math.BigDecimal
import java.time.LocalDate
import java.time.Period

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
        val user = usersRepository.findById(userId)
            .orElseThrow { ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found") }

        if (dateOfBirth.isAfter(LocalDate.now())) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Date of birth cannot be in the future.")
        }

        val age = Period.between(dateOfBirth, LocalDate.now()).years
        if (age < 18) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "User must be at least 18 years old.")
        }

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