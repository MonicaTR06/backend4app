package com.example.backend4frontend.repository

import com.example.backend4frontend.data.domain.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {

    fun findUserByUsernameAndPassword(username: String, password: String): User

}
