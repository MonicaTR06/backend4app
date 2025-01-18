package com.example.backend4frontend.data.dto.note

import com.example.backend4frontend.data.domain.entity.MAX_DESCRIPTION_LENGTH
import com.example.backend4frontend.data.domain.entity.MAX_TITLE_LENGTH
import com.example.backend4frontend.data.domain.entity.MIN_DESCRIPTION_LENGTH
import jakarta.validation.constraints.Size

data class NoteCreateRequest(
    @Size(min = MIN_DESCRIPTION_LENGTH, max = MAX_TITLE_LENGTH)
    val title: String,

    @Size(min = MIN_DESCRIPTION_LENGTH, max = MAX_DESCRIPTION_LENGTH)
    val description: String,

    val isFavorite: Boolean
)
