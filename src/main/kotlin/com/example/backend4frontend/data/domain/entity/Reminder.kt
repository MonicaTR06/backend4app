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
import java.time.LocalDateTime

@Entity
@Table(
    name = "reminder",
    uniqueConstraints = [UniqueConstraint(name = "uk_reminder_title", columnNames = ["title"])]
)
class Reminder {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "remember_sequence")
    @SequenceGenerator(name = "remember_sequence", sequenceName = "remember_sequence", allocationSize = 1)
    val id: Long = 0

    @Size(min = MIN_DESCRIPTION_LENGTH, max = MAX_TITLE_LENGTH)
    @Column(name = "title", nullable = false, unique = true)
    var title: String = ""

    @Column(name = "due_date", nullable = false)
    var dueDate: LocalDateTime = LocalDateTime.now()

    @Column(name = "is_remember_open", nullable = false)
    var isReminderOpen: Boolean = true

    @Column(name = "created_on", nullable = false)
    var createdOn: LocalDateTime = LocalDateTime.now()
}