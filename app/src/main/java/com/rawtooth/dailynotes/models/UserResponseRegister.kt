package com.rawtooth.dailynotes.models

data class UserResponseRegister(
    val token: String,
    val user: User
)