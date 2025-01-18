package com.example.backend4frontend.util.converter

import com.example.backend4frontend.data.dto.task.TaskCreateRequest
import com.example.backend4frontend.data.dto.task.TaskFetchResponse
import com.example.backend4frontend.data.domain.entity.Task
import org.springframework.stereotype.Component
import java.time.Clock
import java.time.LocalDateTime


@Component
class TaskMapper {

    fun toDto(entity: Task) = TaskFetchResponse(
        entity.id,
        entity.title,
        entity.isTaskOpen,
        entity.createdOn,
        entity.priority
    )

    fun toEntity(request: TaskCreateRequest, clock: Clock): Task {
        val task = Task()
        task.title = request.title
        task.isTaskOpen = request.isTaskOpen
        task.priority = request.priority
        task.createdOn = LocalDateTime.now(clock)
        return task
    }
}