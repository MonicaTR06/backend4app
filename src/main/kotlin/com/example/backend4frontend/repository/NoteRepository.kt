package com.example.backend4frontend.repository

import com.example.backend4frontend.data.domain.entity.Note
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface NoteRepository : JpaRepository<Note, Long> {

    fun findNoteById(id: Long): Note

    fun findAllByOrderByIdAsc(): Set<Note>

    fun existsByTitle(title: String): Boolean
}
