package com.example.backend4frontend.data.domain.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.SequenceGenerator
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import jakarta.validation.constraints.Size

@Entity
@Table(
    name = "userapp",
    uniqueConstraints = [UniqueConstraint(name = "uk_userapp_username", columnNames = ["username"])]
)
class UserApp {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userapp_sequence")
    @SequenceGenerator(name = "userapp_sequence", sequenceName = "userapp_sequence", allocationSize = 1)
    val id: Long = 0

    @Size(min = MIN_DESCRIPTION_LENGTH, max = MAX_USER_LENGTH)
    @Column(name = "username", nullable = false, unique = true)
    var username: String = ""

    @Size(min = PASSWORD_LENGTH, max = PASSWORD_LENGTH)
    @Column(name = "password", nullable = false)
    var password: String = ""

    @Size(min = MIN_EMAIL_LENGTH, max = MAX_EMAIL_LENGTH)
    @Column(name = "email", nullable = true)
    var email: String? = null

    @Column(name = "photo", nullable = true)
    var photo: String? = null

    @Size(min = CELLPHONE_LENGTH, max = CELLPHONE_LENGTH)
    @Column(name = "cellphone", nullable = true)
    var cellphone: String? = null
}