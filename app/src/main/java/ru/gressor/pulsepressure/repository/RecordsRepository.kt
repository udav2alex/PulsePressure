package ru.gressor.pulsepressure.repository

import kotlinx.coroutines.flow.Flow
import ru.gressor.pulsepressure.entities.Record

interface RecordsRepository {
    fun records(): Flow<Set<Record>>
    fun saveRecord(record: Record)
}