package com.bader.miniBank.demo.services

import com.bader.miniBank.demo.models.Role
import com.bader.miniBank.demo.models.Users
import com.bader.miniBank.demo.repo.UsersRepository
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class UsersServices(
    private val usersRepository: UsersRepository,
    private val passwordEncoder: PasswordEncoder
) {

    fun registerUser(
        username: String,
        password: String,
        role: Role
    ): Users {
        // Check if username already exists
        if (usersRepository.findByUsername(username) != null) {
            throw ResponseStatusException(HttpStatus.CONFLICT, "Username already exists")
        }

        // Validate password strength
        if (!isValidPassword(password)) {
            throw ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Password must be at least 6 characters, contain 1 capital letter, 1 number, and 1 special character"
            )
        }

        // Encrypt password
        val encryptedPassword = passwordEncoder.encode(password)

        // Create new user object
        val newUser = Users(
            username = username,
            password = encryptedPassword,
            role = role
        )

        // Save user
        return usersRepository.save(newUser)
    }

    fun getAllUsers(): List<Users> = usersRepository.findAll()

    private fun isValidPassword(password: String): Boolean {
        val hasCapital = Regex("[A-Z]").containsMatchIn(password)
        val hasNumber = Regex("[0-9]").containsMatchIn(password)
        val hasSpecial = Regex("[^a-zA-Z0-9]").containsMatchIn(password)
        return password.length >= 6 && hasCapital && hasNumber && hasSpecial
    }
}