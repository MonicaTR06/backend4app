package com.example.backend4frontend.util.converter

import com.example.backend4frontend.data.domain.entity.Reminder
import com.example.backend4frontend.data.dto.reminder.ReminderCreateRequest
import com.example.backend4frontend.data.dto.reminder.ReminderFetchResponse
import org.springframework.stereotype.Component
import java.time.Clock
import java.time.LocalDateTime

@Component
class ReminderMapper {
    fun toDto(entity: Reminder) = ReminderFetchResponse(
        entity.id,
        entity.title,
        entity.dueDate,
        entity.isReminderOpen,
        entity.createdOn
    )

    fun toEntity(request: ReminderCreateRequest, clock: Clock): Reminder {
        val reminder = Reminder()
        reminder.title = request.title
        reminder.dueDate = LocalDateTime.now(clock)
        reminder.isReminderOpen = request.isReminderOpen
        reminder.createdOn = LocalDateTime.now(clock)
        return reminder
    }
}