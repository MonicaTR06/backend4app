package com.example.backend4frontend.data.dto.task

import com.example.backend4frontend.data.domain.Priority
import java.time.LocalDateTime

data class TaskFetchResponse(
    val id: Long,
    val title: String,
    val isTaskOpen: Boolean,
    val createdOn: LocalDateTime,
    val priority: Priority
)
