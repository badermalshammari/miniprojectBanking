package com.bader.miniBank.demo.repo


import com.bader.miniBank.demo.models.KYC
import org.springframework.data.jpa.repository.JpaRepository

interface KYCREPO : JpaRepository<KYC, Long>{

}