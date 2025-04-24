package com.bader.miniBank.demo

import com.bader.miniBank.demo.models.Role
import com.bader.miniBank.demo.models.Users
import com.bader.miniBank.demo.repo.UsersRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
class InitUserRunner {

    @Bean
    fun initUsers(usersRepository: UsersRepository, passwordEncoder: PasswordEncoder) = CommandLineRunner {
        val user = Users(
            username = "Coded",
            password = passwordEncoder.encode("Password123"),
            role = Role.ADMIN
        )

        if (usersRepository.findByUsername(user.username) == null) {
            println("✅ Creating user ${user.username}")
            usersRepository.save(user)
        } else {
            println("⚠️ User ${user.username} already exists")
        }
    }
}