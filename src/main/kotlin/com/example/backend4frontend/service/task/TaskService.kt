package com.example.backend4frontend.service.task

import com.example.backend4frontend.data.domain.TaskStatus
import com.example.backend4frontend.data.dto.task.TaskCreateRequest
import com.example.backend4frontend.data.dto.task.TaskFetchResponse
import com.example.backend4frontend.data.dto.task.TaskUpdateRequest


interface TaskService {

    fun getTasks(status: TaskStatus?): Set<TaskFetchResponse>

    fun getTaskById(id: Long): TaskFetchResponse

    fun createTask(createRequest: TaskCreateRequest): TaskFetchResponse

    fun updateTask(id: Long, updateRequest: TaskUpdateRequest): TaskFetchResponse

    fun deleteTask(id: Long): String
}