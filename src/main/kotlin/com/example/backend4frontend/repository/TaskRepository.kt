package com.example.backend4frontend.repository

import com.example.backend4frontend.data.domain.entity.Task
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TaskRepository : JpaRepository<Task, Long> {

    fun findTaskById(id: Long): Task

    fun findAllByIsTaskOpenOrderByIdAsc(isTaskOpen: Boolean): Set<Task>

    fun findAllByOrderByIdAsc(): Set<Task>

    fun existsByTitle(title: String): Boolean
}
