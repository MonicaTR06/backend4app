package com.example.backend4frontend.data.dto.note

data class NoteItemResponse(
    val id: Long,
    val title: String,
    val isFavorite: Boolean
)
