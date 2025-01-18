package com.example.backend4frontend.data.domain.entity

import com.example.backend4frontend.data.domain.Priority
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.SequenceGenerator
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.time.LocalDateTime

@Entity
@Table(
    name = "task",
    uniqueConstraints = [UniqueConstraint(name = "uk_task_title", columnNames = ["title"])]
)
class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "task_sequence")
    @SequenceGenerator(name = "task_sequence", sequenceName = "task_sequence", allocationSize = 1)
    val id: Long = 0

    @Size(min = MIN_DESCRIPTION_LENGTH, max = MAX_TITLE_LENGTH)
    @Column(name = "title", nullable = false, unique = true)
    var title: String = ""

    @Column(name = "is_task_open", nullable = false)
    var isTaskOpen: Boolean = true

    @Column(name = "created_on", nullable = false)
    var createdOn: LocalDateTime = LocalDateTime.now()

    @Column(name = "closed_on", nullable = true)
    var closedOn: LocalDateTime? = null

    @NotNull
    @Enumerated(EnumType.STRING)
    var priority: Priority = Priority.LOW
}
