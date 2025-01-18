package com.example.backend4frontend.data.dto.note

import java.time.LocalDateTime

data class NoteFetchResponse(
    val id: Long,
    val title: String,
    val description: String,
    val isFavorite: Boolean,
    var createdOn: LocalDateTime
)
