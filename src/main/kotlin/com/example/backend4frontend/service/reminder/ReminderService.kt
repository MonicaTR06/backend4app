package com.example.backend4frontend.service.reminder

import com.example.backend4frontend.data.dto.reminder.ReminderCreateRequest
import com.example.backend4frontend.data.dto.reminder.ReminderFetchResponse
import com.example.backend4frontend.data.dto.reminder.ReminderUpdateRequest


interface ReminderService {

    fun getOpenReminders(isDueToday: Boolean?): Set<ReminderFetchResponse>

    fun getReminderById(id: Long): ReminderFetchResponse

    fun createReminder(createRequest: ReminderCreateRequest): ReminderFetchResponse

    fun updateReminder(id: Long, updateRequest: ReminderUpdateRequest): ReminderFetchResponse

    fun deleteReminder(id: Long): String
}