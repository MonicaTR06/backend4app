package com.example.backend4frontend.service.note

import com.example.backend4frontend.data.dto.note.NoteCreateRequest
import com.example.backend4frontend.data.dto.note.NoteFetchResponse
import com.example.backend4frontend.data.dto.note.NoteItemResponse
import com.example.backend4frontend.data.dto.note.NoteUpdateRequest


interface NoteService {

    fun getNotes(): Set<NoteItemResponse>

    fun getNoteById(id: Long): NoteFetchResponse

    fun createNote(createRequest: NoteCreateRequest): NoteFetchResponse

    fun updateNote(id: Long, updateRequest: NoteUpdateRequest): NoteFetchResponse

    fun deleteNote(id: Long): String
}