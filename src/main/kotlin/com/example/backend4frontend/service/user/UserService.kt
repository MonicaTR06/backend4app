package com.example.backend4frontend.service.user

import com.example.backend4frontend.data.dto.user.UserFetchResponse
import com.example.backend4frontend.data.dto.user.UserRequest

interface UserService {
    fun findUserByUsernameAndPassword(userRequest: UserRequest): Set<UserFetchResponse>
}