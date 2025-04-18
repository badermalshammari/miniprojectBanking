package com.bader.miniBank.demo.models

import jakarta.persistence.*

@Entity
@Table(name = "users")
data class Users(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val username: String = "",

    val password: String = ""
)
{
    constructor() : this(0, "", "")
}