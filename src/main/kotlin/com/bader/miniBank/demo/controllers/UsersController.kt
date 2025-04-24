package com.bader.miniBank.demo.controllers

import com.bader.miniBank.demo.models.Role
import com.bader.miniBank.demo.models.Users
import com.bader.miniBank.demo.services.UsersServices
import org.springframework.web.bind.annotation.*

data class RegisterRequest(
    val username: String,
    val password: String,
    val role: Role = Role.USER
)

@RestController
@RequestMapping("/api/users")
class UsersController(
    private val usersServices: UsersServices
) {

    @PostMapping("/register")
    fun register(@RequestBody request: RegisterRequest): Users {
        return usersServices.registerUser(
            username = request.username,
            password = request.password,
            role = request.role,
        )
    }

    @GetMapping
    fun getAll(): List<Users> = usersServices.getAllUsers()
}