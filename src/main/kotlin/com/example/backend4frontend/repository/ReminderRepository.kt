package com.example.backend4frontend.repository

import com.example.backend4frontend.data.domain.entity.Reminder
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ReminderRepository : JpaRepository<Reminder, Long> {

    fun findReminderById(id: Long): Reminder

    fun findAllByIsDueTodayOrderByIdAsc(isDueToday: Boolean?): Set<Reminder>

    fun findAllByOrderByIdAsc(): Set<Reminder>

    fun existsByTitle(title: String): Boolean
}
