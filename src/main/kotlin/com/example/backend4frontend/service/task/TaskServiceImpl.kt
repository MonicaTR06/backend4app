package com.example.backend4frontend.service.task

import com.example.backend4frontend.data.domain.TaskStatus
import com.example.backend4frontend.data.domain.entity.MAX_TITLE_LENGTH
import com.example.backend4frontend.data.domain.entity.MIN_DESCRIPTION_LENGTH
import com.example.backend4frontend.data.domain.entity.Task
import com.example.backend4frontend.data.dto.task.TaskCreateRequest
import com.example.backend4frontend.data.dto.task.TaskFetchResponse
import com.example.backend4frontend.data.dto.task.TaskUpdateRequest
import com.example.backend4frontend.errorhandler.BadRequestException
import com.example.backend4frontend.errorhandler.TaskNotFoundException
import com.example.backend4frontend.repository.TaskRepository
import com.example.backend4frontend.util.TaskTimestamp
import com.example.backend4frontend.util.converter.TaskMapper
import org.springframework.stereotype.Service
import org.springframework.util.ReflectionUtils
import java.lang.reflect.Field
import kotlin.reflect.full.memberProperties


@Service
class TaskServiceImpl(
    private val repository: TaskRepository,
    private val mapper: TaskMapper,
    private val taskTimestamp: TaskTimestamp
) : TaskService {

    override fun getTasks(status: TaskStatus?): Set<TaskFetchResponse> {
        return when (status) {
            TaskStatus.OPEN -> repository.findAllByIsTaskOpenOrderByIdAsc(true).map(mapper::toDto).toSet()
            TaskStatus.CLOSED -> repository.findAllByIsTaskOpenOrderByIdAsc(false).map(mapper::toDto).toSet()
            else -> repository.findAllByOrderByIdAsc().map(mapper::toDto).toSet()
        }
    }

    override fun getTaskById(id: Long): TaskFetchResponse {
        validateTaskIdExistence(id)
        val task: Task = repository.findTaskById(id)
        return mapper.toDto(task)
    }

    override fun createTask(createRequest: TaskCreateRequest): TaskFetchResponse {
        val titleLength: Int = createRequest.title.length
        if (titleLength < MIN_DESCRIPTION_LENGTH || titleLength > MAX_TITLE_LENGTH) {
            throw BadRequestException("Title must be between $MIN_DESCRIPTION_LENGTH and $MAX_TITLE_LENGTH characters in length")
        }

        if (repository.existsByTitle(createRequest.title)) {
            throw BadRequestException("A task with the title '${createRequest.title}' already exists")
        }
        val task: Task = mapper.toEntity(createRequest, taskTimestamp.createClockWithZone())
        val savedTask: Task = repository.save(task)
        return mapper.toDto(savedTask)
    }

    override fun updateTask(id: Long, updateRequest: TaskUpdateRequest): TaskFetchResponse {
        validateTaskIdExistence(id)
        val existingTask: Task = repository.findTaskById(id)

        for (prop in TaskUpdateRequest::class.memberProperties) {
            if (prop.get(updateRequest) != null) {
                val field: Field? = ReflectionUtils.findField(Task::class.java, prop.name)
                field?.let {
                    it.isAccessible = true
                    ReflectionUtils.setField(it, existingTask, prop.get(updateRequest))
                }
            }
        }

        val savedTask: Task = repository.save(existingTask)
        return mapper.toDto(savedTask)
    }

    override fun deleteTask(id: Long): String {
        validateTaskIdExistence(id)
        repository.deleteById(id)
        return "Task with id: $id has been deleted."
    }

    private fun validateTaskIdExistence(id: Long) {
        if (!repository.existsById(id)) {
            throw TaskNotFoundException(message = "Task with ID: $id does not exist!")
        }
    }
}