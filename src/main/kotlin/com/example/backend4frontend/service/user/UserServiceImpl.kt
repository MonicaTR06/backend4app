package com.example.backend4frontend.service.user

import com.example.backend4frontend.data.dto.user.UserFetchResponse
import com.example.backend4frontend.data.dto.user.UserRequest
import com.example.backend4frontend.repository.UserRepository
import com.example.backend4frontend.util.converter.UserMapper
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val repository: UserRepository,
    private val mapper: UserMapper
) : UserService {

    override fun findUserByUsernameAndPassword(userRequest: UserRequest): UserFetchResponse {
        val user = repository.findUserByUsernameAndPassword(userRequest.username, userRequest.password)
        return mapper.toDto(user)
    }
}