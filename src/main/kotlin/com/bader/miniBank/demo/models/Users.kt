package com.bader.miniBank.demo.models

import jakarta.persistence.*

@Entity
@Table(name = "users")
data class Users(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(unique = true)
    val username: String = "",

    val password: String = "",

    @Enumerated(EnumType.STRING)
    val role: Role = Role.USER
) {
    constructor() : this(0, "", "", Role.USER)
}

enum class Role {
    USER,
    ADMIN
}