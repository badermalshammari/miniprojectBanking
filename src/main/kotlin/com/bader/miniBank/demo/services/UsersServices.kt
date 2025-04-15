package com.bader.miniBank.demo.services

import com.bader.miniBank.demo.repo.UsersRepository
import com.bader.miniBank.demo.models.Users
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class UsersServices(private val usersRepository: UsersRepository) {

    fun registerUser(username: String, password: String): Users {

        if (usersRepository.findByUsername(username) != null) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists")
        }

        if (password.length <= 6) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Password must be more than 6 characters")
        }

        val specialCharPattern = Regex("[^a-zA-Z0-9]")
        if (!specialCharPattern.containsMatchIn(password)) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Password must contain at least one special character")
        }

        val newUser = Users(username = username, password = password)
        return usersRepository.save(newUser)
    }

    fun getAllUsers(): List<Users> = usersRepository.findAll()
}