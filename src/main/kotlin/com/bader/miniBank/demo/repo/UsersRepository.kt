package com.bader.miniBank.demo.repo

import com.bader.miniBank.demo.models.Users
import org.springframework.data.jpa.repository.JpaRepository

interface UsersRepository : JpaRepository<Users, Long> {
    fun findByUsername(username: String): Users?
}