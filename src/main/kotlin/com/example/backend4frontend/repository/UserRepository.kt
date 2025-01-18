package com.example.backend4frontend.repository

import com.example.backend4frontend.data.domain.entity.UserApp
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<UserApp, Long> {

    fun findUserByUsernameAndPassword(username: String, password: String): UserApp

}
