package com.example.backend4frontend.util.converter

import com.example.backend4frontend.data.domain.entity.Note
import com.example.backend4frontend.data.dto.note.NoteCreateRequest
import com.example.backend4frontend.data.dto.note.NoteFetchResponse
import com.example.backend4frontend.data.dto.note.NoteItemResponse
import org.springframework.stereotype.Component
import java.time.Clock
import java.time.LocalDateTime

@Component
class NoteMapper {

    fun toDto(entity: Note) = NoteFetchResponse(
        entity.id,
        entity.title,
        entity.description,
        entity.isFavorite,
        entity.createdOn
    )

    fun toDtoItem(entity: Note) = NoteItemResponse(
        entity.id,
        entity.title,
        entity.isFavorite
    )

    fun toEntity(request: NoteCreateRequest, clock: Clock): Note {
        val task = Note()
        task.title = request.title
        task.description = request.description
        task.isFavorite = request.isFavorite
        task.createdOn = LocalDateTime.now(clock)
        return task
    }
}