package com.example.backend4frontend.data.dto.user

import com.example.backend4frontend.data.domain.entity.MAX_TITLE_LENGTH
import com.example.backend4frontend.data.domain.entity.MIN_DESCRIPTION_LENGTH
import com.example.backend4frontend.data.domain.entity.MIN_EMAIL_LENGTH
import com.example.backend4frontend.data.domain.entity.PASSWORD_LENGTH
import jakarta.validation.constraints.Size

data class UserCreateRequest(
    @Size(min = MIN_DESCRIPTION_LENGTH, max = MAX_TITLE_LENGTH)
    val username: String,

    @Size(min = PASSWORD_LENGTH, max = PASSWORD_LENGTH)
    val password: String,

    @Size(min = MIN_EMAIL_LENGTH, max = MIN_EMAIL_LENGTH)
    val email: String? = null,

    val photo: String? = null,

    val cellphone: String? = null
)
