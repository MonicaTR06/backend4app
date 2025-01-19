package com.example.backend4frontend.web.rest

import com.example.backend4frontend.data.dto.user.UserCreateRequest
import com.example.backend4frontend.data.dto.user.UserFetchResponse
import com.example.backend4frontend.data.dto.user.UserRequest
import com.example.backend4frontend.service.user.UserAppService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@CrossOrigin
@RestController
@RequestMapping("api/v1/users")
class UserController(private val service: UserAppService) {

    @GetMapping
    fun getUser(
        @RequestParam("username", required = true) username: String,
        @RequestParam("password", required = true) password: String
    ): ResponseEntity<UserFetchResponse> = ResponseEntity.ok(
        service.findUserByUsernameAndPassword(UserRequest(username, password))
    )

    @PostMapping
    fun createUser(
        @Valid @RequestBody
        createRequest: UserCreateRequest
    ): ResponseEntity<UserFetchResponse> {
        val user = service.createUserApp(createRequest)
        return ResponseEntity(user, HttpStatus.CREATED)
    }

}