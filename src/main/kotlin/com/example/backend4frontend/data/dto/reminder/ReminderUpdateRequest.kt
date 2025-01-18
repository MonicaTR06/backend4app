package com.example.backend4frontend.data.dto.reminder

import java.time.LocalDateTime

data class ReminderUpdateRequest(
    val title: String,

    val dateTime: LocalDateTime,

    val isReminderOpen: Boolean
)
