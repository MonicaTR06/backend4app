package com.example.backend4frontend.service.note

import com.example.backend4frontend.data.domain.entity.MAX_DESCRIPTION_LENGTH
import com.example.backend4frontend.data.domain.entity.MAX_TITLE_LENGTH
import com.example.backend4frontend.data.domain.entity.MIN_DESCRIPTION_LENGTH
import com.example.backend4frontend.data.domain.entity.Note
import com.example.backend4frontend.data.dto.note.NoteCreateRequest
import com.example.backend4frontend.data.dto.note.NoteFetchResponse
import com.example.backend4frontend.data.dto.note.NoteItemResponse
import com.example.backend4frontend.data.dto.note.NoteUpdateRequest
import com.example.backend4frontend.errorhandler.BadRequestException
import com.example.backend4frontend.errorhandler.TaskNotFoundException
import com.example.backend4frontend.repository.NoteRepository
import com.example.backend4frontend.util.TaskTimestamp
import com.example.backend4frontend.util.converter.NoteMapper
import org.springframework.stereotype.Service
import org.springframework.util.ReflectionUtils
import java.lang.reflect.Field
import kotlin.reflect.full.memberProperties


@Service
class NoteServiceImpl(
    private val repository: NoteRepository,
    private val mapper: NoteMapper,
    private val taskTimestamp: TaskTimestamp
) : NoteService {

    private fun validateNoteIdExistence(id: Long) {
        if (!repository.existsById(id)) {
            throw TaskNotFoundException(message = "Note with ID: $id does not exist!")
        }
    }

    override fun getNotes(): Set<NoteItemResponse> {
        return repository.findAllByOrderByIdAsc().map(mapper::toDtoItem).toSet()
    }

    override fun getNoteById(id: Long): NoteFetchResponse {
        validateNoteIdExistence(id)
        val task: Note = repository.findNoteById(id)
        return mapper.toDto(task)
    }

    override fun createNote(createRequest: NoteCreateRequest): NoteFetchResponse {
        val titleLength: Int = createRequest.title.length
        if (titleLength < MIN_DESCRIPTION_LENGTH || titleLength > MAX_TITLE_LENGTH) {
            throw BadRequestException("Title must be between $MIN_DESCRIPTION_LENGTH and $MAX_TITLE_LENGTH characters in length")
        }
        if (repository.existsByTitle(createRequest.title)) {
            throw BadRequestException("A Note with the title '${createRequest.description}' already exists")
        }
        val descriptionLength: Int = createRequest.description.length
        if (descriptionLength < MIN_DESCRIPTION_LENGTH || descriptionLength > MAX_DESCRIPTION_LENGTH) {
            throw BadRequestException("Description must be between $MIN_DESCRIPTION_LENGTH and $MAX_DESCRIPTION_LENGTH characters in length")
        }
        val task: Note = mapper.toEntity(createRequest, taskTimestamp.createClockWithZone())
        val savedTask: Note = repository.save(task)
        return mapper.toDto(savedTask)
    }

    override fun updateNote(id: Long, updateRequest: NoteUpdateRequest): NoteFetchResponse {
        validateNoteIdExistence(id)
        val existingTask: Note = repository.findNoteById(id)

        for (prop in NoteUpdateRequest::class.memberProperties) {
            if (prop.get(updateRequest) != null) {
                val field: Field? = ReflectionUtils.findField(Note::class.java, prop.name)
                field?.let {
                    it.isAccessible = true
                    ReflectionUtils.setField(it, existingTask, prop.get(updateRequest))
                }
            }
        }

        val savedTask: Note = repository.save(existingTask)
        return mapper.toDto(savedTask)
    }

    override fun deleteNote(id: Long): String {
        validateNoteIdExistence(id)
        repository.deleteById(id)
        return "Note with id: $id has been deleted."
    }
}