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
    name = "note",
    uniqueConstraints = [UniqueConstraint(name = "uk_note_title", columnNames = ["title"])]
)
class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "note_sequence")
    @SequenceGenerator(name = "note_sequence", sequenceName = "note_sequence", allocationSize = 1)
    val id: Long = 0

    @Size(min = MIN_DESCRIPTION_LENGTH, max = MAX_TITLE_LENGTH)
    @Column(name = "title", nullable = false, unique = true)
    var title: String = ""

    @Size(min = MIN_DESCRIPTION_LENGTH, max = MAX_DESCRIPTION_LENGTH)
    @Column(name = "description", nullable = false)
    var description: String = ""

    @Column(name = "is_favorite", nullable = false)
    var isFavorite: Boolean = false

    @Column(name = "created_on", nullable = false)
    var createdOn: LocalDateTime = LocalDateTime.now()
}