package com.example.backend4frontend.data.dto.reminder

import java.time.LocalDateTime

data class ReminderFetchResponse(
    val id: Long,
    val title: String,
    val dueDate: LocalDateTime,
    val isReminderOpen: Boolean,
    var createdOn: LocalDateTime
)
