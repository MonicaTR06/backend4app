package com.example.backend4frontend.data.dto.user

data class UserFetchResponse(
    val id: Long,
    val username: String,
    val email: String?,
    val photo: String?,
    val cellphone: String?
)
