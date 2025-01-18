package com.example.backend4frontend.service.reminder

import com.example.backend4frontend.data.dto.reminder.ReminderCreateRequest
import com.example.backend4frontend.data.dto.reminder.ReminderFetchResponse
import com.example.backend4frontend.data.dto.reminder.ReminderUpdateRequest
import com.example.backend4frontend.data.domain.entity.MAX_TITLE_LENGTH
import com.example.backend4frontend.data.domain.entity.MIN_DESCRIPTION_LENGTH
import com.example.backend4frontend.data.domain.entity.Reminder
import com.example.backend4frontend.errorhandler.BadRequestException
import com.example.backend4frontend.errorhandler.TaskNotFoundException
import com.example.backend4frontend.repository.ReminderRepository
import com.example.backend4frontend.util.TaskTimestamp
import com.example.backend4frontend.util.converter.ReminderMapper
import org.springframework.stereotype.Service
import kotlin.reflect.full.memberProperties
import java.lang.reflect.Field
import org.springframework.util.ReflectionUtils


@Service
class ReminderServiceImpl(
    private val repository: ReminderRepository,
    private val mapper: ReminderMapper,
    private val reminderTimestamp: TaskTimestamp
) : ReminderService {

    override fun getOpenReminders(isDueToday: Boolean?): Set<ReminderFetchResponse> {
        return if (isDueToday != null) {
            repository.findAllByIsDueTodayOrderByIdAsc(isDueToday).map(mapper::toDto).toSet()
        } else {
            repository.findAllByOrderByIdAsc().map(mapper::toDto).toSet()
        }
    }

    override fun getReminderById(id: Long): ReminderFetchResponse {
        validateReminderIdExistence(id)
        val reminder: Reminder = repository.findReminderById(id)
        return mapper.toDto(reminder)
    }

    override fun createReminder(createRequest: ReminderCreateRequest): ReminderFetchResponse {
        val titleLength: Int = createRequest.title.length
        if (titleLength < MIN_DESCRIPTION_LENGTH || titleLength > MAX_TITLE_LENGTH) {
            throw BadRequestException("Title must be between $MIN_DESCRIPTION_LENGTH and $MAX_TITLE_LENGTH characters in length")
        }

        if (repository.existsByTitle(createRequest.title)) {
            throw BadRequestException("A Reminder with the title '${createRequest.title}' already exists")
        }

        val reminder: Reminder =
            mapper.toEntity(createRequest, reminderTimestamp.createClockWithZone())
        val savedReminder: Reminder = repository.save(reminder)
        return mapper.toDto(savedReminder)
    }

    override fun updateReminder(
        id: Long,
        updateRequest: ReminderUpdateRequest
    ): ReminderFetchResponse {
        validateReminderIdExistence(id)
        val existingReminder: Reminder = repository.findReminderById(id)

        for (prop in ReminderUpdateRequest::class.memberProperties) {
            if (prop.get(updateRequest) != null) {
                val field: Field? = ReflectionUtils.findField(Reminder::class.java, prop.name)
                field?.let {
                    it.isAccessible = true
                    ReflectionUtils.setField(it, existingReminder, prop.get(updateRequest))
                }
            }
        }

        val savedReminder: Reminder = repository.save(existingReminder)
        return mapper.toDto(savedReminder)
    }

    override fun deleteReminder(id: Long): String {
        validateReminderIdExistence(id)
        repository.deleteById(id)
        return "Reminder with id: $id has been deleted."
    }

    private fun validateReminderIdExistence(id: Long) {
        if (!repository.existsById(id)) {
            throw TaskNotFoundException(message = "Reminder with ID: $id does not exist!")
        }
    }

}