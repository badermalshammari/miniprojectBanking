package com.bader.miniBank.demo.services

import com.bader.miniBank.demo.repo.UsersRepository
import com.bader.miniBank.demo.models.Users
import org.springframework.stereotype.Service

@Service
class UsersServices(private val usersRepository: UsersRepository) {

    fun registerUser(username: String, password: String): Users {
        if (usersRepository.findByUsername(username) != null) {
            throw Exception("Username already exists")
        }

        val newUser = Users(username = username, password = password)
        return usersRepository.save(newUser)
    }

    fun getAllUsers(): List<Users> = usersRepository.findAll()
}