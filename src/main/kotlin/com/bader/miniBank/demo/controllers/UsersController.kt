package com.bader.miniBank.demo.controllers

import com.bader.miniBank.demo.models.Users
import com.bader.miniBank.demo.services.UsersServices
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

data class RegisterRequest(val username: String, val password: String)

@RestController
@RequestMapping("/api/users")
class UsersController(private val usersServices: UsersServices) {

    @PostMapping("/register")
    fun register(@RequestBody request: RegisterRequest): ResponseEntity<Users> {
        val user = usersServices.registerUser(request.username, request.password)
        return ResponseEntity.ok(user)
    }

    @GetMapping
    fun getAll(): List<Users> = usersServices.getAllUsers()
}