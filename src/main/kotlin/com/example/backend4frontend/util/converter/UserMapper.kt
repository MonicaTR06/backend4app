package com.example.backend4frontend.util.converter

import com.example.backend4frontend.data.domain.entity.UserApp
import com.example.backend4frontend.data.dto.user.UserFetchResponse
import org.springframework.stereotype.Component

@Component
class UserMapper {
    fun toDto(entity: UserApp) = UserFetchResponse(
        entity.id,
        entity.username,
        entity.email,
        entity.photo,
        entity.cellphone
    )
}