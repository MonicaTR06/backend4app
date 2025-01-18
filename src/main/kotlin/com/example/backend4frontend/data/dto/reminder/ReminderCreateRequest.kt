package com.example.backend4frontend.data.dto.reminder

import com.example.backend4frontend.data.domain.entity.MAX_TITLE_LENGTH
import com.example.backend4frontend.data.domain.entity.MIN_DESCRIPTION_LENGTH
import jakarta.validation.constraints.Size
import java.time.LocalDateTime

data class ReminderCreateRequest(
    @Size(min = MIN_DESCRIPTION_LENGTH, max = MAX_TITLE_LENGTH)
    val title: String,

    val dateTime: LocalDateTime,

    val isReminderOpen: Boolean
)
