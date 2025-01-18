package com.example.backend4frontend.data.dto.note

data class NoteUpdateRequest(
    val title: String,
    val description: String,
    val isFavorite: Boolean
)
