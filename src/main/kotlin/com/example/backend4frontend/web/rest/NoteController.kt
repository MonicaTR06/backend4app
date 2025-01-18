package com.example.backend4frontend.web.rest

import com.example.backend4frontend.data.dto.note.NoteCreateRequest
import com.example.backend4frontend.data.dto.note.NoteFetchResponse
import com.example.backend4frontend.data.dto.note.NoteItemResponse
import com.example.backend4frontend.data.dto.note.NoteUpdateRequest
import com.example.backend4frontend.service.note.NoteService
import jakarta.validation.Valid
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@CrossOrigin
@RestController
@RequestMapping("api/v1/notes")
class NoteController(private val service: NoteService) {

    @GetMapping
    fun getNotes(): ResponseEntity<Set<NoteItemResponse>> = ResponseEntity.ok(service.getNotes())

    @GetMapping("{id}")
    fun getNoteById(@PathVariable id: Long): ResponseEntity<NoteFetchResponse> = ResponseEntity.ok(service.getNoteById(id))

    @PostMapping
    fun createNote(
        @Valid @RequestBody
        createRequest: NoteCreateRequest
    ): ResponseEntity<NoteFetchResponse> {
        val note = service.createNote(createRequest)
        return ResponseEntity(note, HttpStatus.CREATED)
    }

    @PatchMapping("{id}")
    fun updateNote(
        @PathVariable id: Long,
        @Valid @RequestBody
        updateRequest: NoteUpdateRequest
    ): ResponseEntity<NoteFetchResponse> = ResponseEntity.ok(service.updateNote(id, updateRequest))

    @DeleteMapping("{id}")
    fun deleteNote(@PathVariable id: Long): ResponseEntity<Unit> {
        val headerValue: String = service.deleteNote(id)
        val httpHeader = HttpHeaders()
        httpHeader.add("delete-Note-header", headerValue)
        return ResponseEntity(null, httpHeader, HttpStatus.NO_CONTENT)
    }
}