package com.example.backend4frontend.data.dto.task

import com.example.backend4frontend.data.domain.Priority

data class TaskUpdateRequest(
    val title: String?,
    val isTaskOpen: Boolean?,
    val priority: Priority?
)
