package com.example.backend4frontend.data.dto.task

import com.example.backend4frontend.data.domain.Priority
import com.example.backend4frontend.data.domain.entity.MAX_TITLE_LENGTH
import com.example.backend4frontend.data.domain.entity.MIN_DESCRIPTION_LENGTH
import jakarta.validation.constraints.Size

data class TaskCreateRequest(
    @Size(min = MIN_DESCRIPTION_LENGTH, max = MAX_TITLE_LENGTH)
    val title: String,
    val isTaskOpen: Boolean,
    val priority: Priority
)
