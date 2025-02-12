package com.example.backend4frontend.web.rest

import com.example.backend4frontend.data.domain.TaskStatus
import com.example.backend4frontend.data.dto.task.TaskCreateRequest
import com.example.backend4frontend.data.dto.task.TaskFetchResponse
import com.example.backend4frontend.data.dto.task.TaskUpdateRequest
import com.example.backend4frontend.service.task.TaskService
import jakarta.validation.Valid
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@CrossOrigin
@RestController
@RequestMapping("api/v1/tasks")
class TaskController(private val service: TaskService) {

    @GetMapping
    fun getTasks(
        @RequestParam("status", required = false) status: TaskStatus?
    ): ResponseEntity<Set<TaskFetchResponse>> = ResponseEntity.ok(service.getTasks(status))

    @GetMapping("{id}")
    fun getTaskById(@PathVariable id: Long): ResponseEntity<TaskFetchResponse> = ResponseEntity.ok(service.getTaskById(id))

    @PostMapping
    fun createTask(
        @Valid @RequestBody
        createRequest: TaskCreateRequest
    ): ResponseEntity<TaskFetchResponse> {
        val task = service.createTask(createRequest)
        return ResponseEntity(task, HttpStatus.CREATED)
    }

    @PatchMapping("{id}")
    fun updateTask(
        @PathVariable id: Long,
        @Valid @RequestBody
        updateRequest: TaskUpdateRequest
    ): ResponseEntity<TaskFetchResponse> = ResponseEntity.ok(service.updateTask(id, updateRequest))

    @DeleteMapping("{id}")
    fun deleteTask(@PathVariable id: Long): ResponseEntity<Unit> {
        val headerValue: String = service.deleteTask(id)
        val httpHeader = HttpHeaders()
        httpHeader.add("delete-task-header", headerValue)
        return ResponseEntity(null, httpHeader, HttpStatus.NO_CONTENT)
    }
}