package com.example.backend4frontend.web.rest

import com.example.backend4frontend.data.dto.reminder.ReminderCreateRequest
import com.example.backend4frontend.data.dto.reminder.ReminderFetchResponse
import com.example.backend4frontend.data.dto.reminder.ReminderUpdateRequest
import com.example.backend4frontend.service.reminder.ReminderService
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
@RequestMapping("api/v1/reminders")
class ReminderController(private val service: ReminderService) {

    @GetMapping
    fun getReminders(
        @RequestParam("isDueToday", required = false) isDueToday: Boolean?
    ): ResponseEntity<Set<ReminderFetchResponse>> = ResponseEntity.ok(service.getOpenReminders(isDueToday))

    @GetMapping("{id}")
    fun getReminderById(@PathVariable id: Long): ResponseEntity<ReminderFetchResponse> = ResponseEntity.ok(service.getReminderById(id))

    @PostMapping
    fun createReminder(
        @Valid @RequestBody
        createRequest: ReminderCreateRequest
    ): ResponseEntity<ReminderFetchResponse> {
        val reminder = service.createReminder(createRequest)
        return ResponseEntity(reminder, HttpStatus.CREATED)
    }

    @PatchMapping("{id}")
    fun updateReminder(
        @PathVariable id: Long,
        @Valid @RequestBody
        updateRequest: ReminderUpdateRequest
    ): ResponseEntity<ReminderFetchResponse> = ResponseEntity.ok(service.updateReminder(id, updateRequest))

    @DeleteMapping("{id}")
    fun deleteReminder(@PathVariable id: Long): ResponseEntity<Unit> {
        val headerValue: String = service.deleteReminder(id)
        val httpHeader = HttpHeaders()
        httpHeader.add("delete-Reminder-header", headerValue)
        return ResponseEntity(null, httpHeader, HttpStatus.NO_CONTENT)
    }
}