package com.example.backend4frontend.web.rest
/*
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.example.backend4frontend.errorhandler.BadRequestException
import com.example.backend4frontend.errorhandler.TaskNotFoundException
import com.example.backend4frontend.data.domain.TaskStatus
import com.example.backend4frontend.data.domain.Priority
import com.example.backend4frontend.data.dto.task.TaskCreateRequest
import com.example.backend4frontend.data.dto.task.TaskFetchResponse
import com.example.backend4frontend.data.dto.task.TaskUpdateRequest
import com.example.backend4frontend.data.domain.entity.MAX_DESCRIPTION_LENGTH
import com.example.backend4frontend.data.domain.entity.MIN_DESCRIPTION_LENGTH
import com.example.backend4frontend.service.task.TaskService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@ExtendWith(SpringExtension::class)
@WebMvcTest(controllers = [TaskController::class])
internal class TaskControllerIntegrationTest(@Autowired private val mockMvc: MockMvc) {

    @MockBean
    private lateinit var mockService: TaskService

    private val mapper = jacksonObjectMapper()

    private val taskId: Long = 33
    private val dummyDto1 = TaskFetchResponse(
        33,
        "test1",
        isTaskOpen = false,
        createdOn = LocalDateTime.now(),
        priority = Priority.LOW
    )

    @BeforeEach
    fun setUp() {
        mapper.registerModule(JavaTimeModule())
    }

    @Test
    fun `given all tasks when fetch happen then check for size`() {
        // GIVEN
        val taskFetchDto = TaskFetchResponse(
            44,
            "test2",
            isTaskOpen = false,
            createdOn = LocalDateTime.now(),
            priority = Priority.LOW
        )
        val fetchDtos = setOf(dummyDto1, taskFetchDto)

        // WHEN
        `when`(mockService.getTasks(null)).thenReturn(fetchDtos)
        val resultActions: ResultActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/tasks"))

        // THEN
        resultActions.andExpect(MockMvcResultMatchers.status().`is`(200))
        resultActions.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(fetchDtos.size))
    }

    @Test
    fun `given open tasks when fetch happen then check for size and isTaskOpen is true`() {
        val fetchDto = TaskFetchResponse(
            44,
            "test2",
            isTaskOpen = true,
            createdOn = LocalDateTime.now(),
            priority = Priority.LOW
        )

        `when`(mockService.getTasks(TaskStatus.OPEN)).thenReturn(setOf(fetchDto))
        val resultActions: ResultActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/tasks?status=open"))

        resultActions.andExpect(MockMvcResultMatchers.status().`is`(200))
        resultActions.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(1))
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$[0].isTaskOpen").value(fetchDto.isTaskOpen))
    }

    @Test
    fun `given closed tasks when fetch happen then check for size  and isTaskOpen is false`() {
        // GIVEN
        // WHEN
        `when`(mockService.getTasks(TaskStatus.CLOSED)).thenReturn(setOf(dummyDto1))
        val resultActions: ResultActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/tasks?status=closed"))

        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$[0].isTaskOpen").value(dummyDto1.isTaskOpen))
    }

    @Test
    fun `given one task when get task by id is called then check for correct description`() {
        `when`(mockService.getTaskById(33)).thenReturn(dummyDto1)
        val resultActions: ResultActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/tasks/${dummyDto1.id}"))

        resultActions.andExpect(MockMvcResultMatchers.status().`is`(200))
        resultActions.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.description").value(dummyDto1.title))
    }

    @Test
    fun `given one task when get task by id is called with string instead of int then check for internal server error`() {
        val resultActions: ResultActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/tasks/404L"))

        resultActions.andExpect(MockMvcResultMatchers.status().is5xxServerError)
    }

    @Test
    fun `given task id when id does not exist then check for task not found exception`() {
        val id: Long = 121

        `when`(mockService.deleteTask(id)).thenThrow(TaskNotFoundException("Task with ID: $id does not exist!"))
        val resultActions: ResultActions = mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/tasks/$id"))

        resultActions.andExpect(MockMvcResultMatchers.status().isNotFound)
    }

    @Test
    fun `given task creation when description is the same to another then check for bad request exception`() {
        val request = TaskCreateRequest(
            title = "t",
            isTaskOpen = false,
            priority = Priority.LOW
        )
        val badRequestException =
            BadRequestException("Description needs to be at least $MIN_DESCRIPTION_LENGTH characters long or maximum $MAX_DESCRIPTION_LENGTH")

        `when`(mockService.createTask(request)).thenThrow(badRequestException)
        val resultActions: ResultActions = mockMvc.perform(
            MockMvcRequestBuilders.post("/api/v1/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
        )

        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest)
    }

    @Test
    fun `given create task request when task gets created then check for correct property`() {
        val request = TaskCreateRequest(
            title = "test for db",
            isTaskOpen = false,
            priority = Priority.LOW
        )
        val fetchDto = TaskFetchResponse(
            0,
            "test for db",
            isTaskOpen = false,
            createdOn = LocalDateTime.now(),
            priority = Priority.LOW
        )

        `when`(mockService.createTask(request)).thenReturn(fetchDto)
        val resultActions: ResultActions = mockMvc.perform(
            MockMvcRequestBuilders.post("/api/v1/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
        )

        resultActions.andExpect(MockMvcResultMatchers.status().isCreated)
        resultActions.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
    }

    @Test
    fun `given update task request when task gets updated then check for correct property`() {
        val str = "1986-04-08 12:30"
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        val dateTime = LocalDateTime.parse(str, formatter)

        val request = TaskUpdateRequest(
            "update task",
            isTaskOpen = false,
            priority = Priority.LOW
        )
        val dummyDto = TaskFetchResponse(
            44,
            request.title ?: "",
            isTaskOpen = true,
            createdOn = dateTime,
            priority = Priority.MEDIUM
        )

        `when`(mockService.updateTask(dummyDto.id, request)).thenReturn(dummyDto)
        val resultActions: ResultActions = mockMvc.perform(
            MockMvcRequestBuilders.patch("/api/v1/tasks/${dummyDto.id}")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request))
        )

        resultActions.andExpect(MockMvcResultMatchers.status().isOk)
        resultActions.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.description").value(dummyDto.title))
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.isTaskOpen").value(dummyDto.isTaskOpen))
    }

    @Test
    fun `given id for delete request when delete task is performed then check for the message`() {
        val expectedHeaderValue = "Task with id: $taskId has been deleted."

        `when`(mockService.deleteTask(taskId)).thenReturn(expectedHeaderValue)
        val resultActions: ResultActions = mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/tasks/$taskId"))

        resultActions.andExpect(MockMvcResultMatchers.status().isNoContent)
        resultActions.andExpect(MockMvcResultMatchers.header().string("delete-task-header", expectedHeaderValue))
    }
}
*/