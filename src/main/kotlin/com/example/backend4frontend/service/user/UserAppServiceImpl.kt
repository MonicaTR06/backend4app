package com.example.backend4frontend.service.user

import com.example.backend4frontend.data.domain.entity.UserApp
import com.example.backend4frontend.data.dto.user.UserCreateRequest
import com.example.backend4frontend.data.dto.user.UserFetchResponse
import com.example.backend4frontend.data.dto.user.UserRequest
import com.example.backend4frontend.repository.UserRepository
import com.example.backend4frontend.util.converter.UserMapper
import org.springframework.stereotype.Service

@Service
class UserAppServiceImpl(
    private val repository: UserRepository,
    private val mapper: UserMapper
) : UserAppService {

    override fun findUserByUsernameAndPassword(userRequest: UserRequest): UserFetchResponse {
        val user =
            repository.findUserByUsernameAndPassword(userRequest.username, userRequest.password)
        return mapper.toDto(user)
    }

    override fun createUserApp(createRequest: UserCreateRequest): UserFetchResponse {
        val user: UserApp = mapper.toEntity(createRequest)
        val savedTask: UserApp = repository.save(user)
        return mapper.toDto(savedTask)
    }
}