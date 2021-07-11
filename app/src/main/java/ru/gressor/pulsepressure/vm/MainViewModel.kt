package ru.gressor.pulsepressure.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import ru.gressor.pulsepressure.entities.Record
import ru.gressor.pulsepressure.repository.RecordsRepository
import ru.gressor.pulsepressure.ui.main.MainRecyclerRow
import java.text.SimpleDateFormat
import java.util.*

class MainViewModel(
    private val recordsRepository: RecordsRepository
) : ViewModel() {

    val stateFlow = recordsRepository.records()
        .map { it.toViewState() }
        .stateIn(viewModelScope, SharingStarted.Eagerly, listOf())

    fun saveRecord(record: Record) {
        recordsRepository.saveRecord(record)
    }

    private fun Set<Record>.toViewState(): List<MainRecyclerRow> {
        val list = this.sortedBy { -it.time }
        val result = mutableListOf<MainRecyclerRow>()

        val df = SimpleDateFormat("HH:mm", Locale.getDefault())
        val date = Date()

        var currentDateString = ""
        list.forEach { record ->
            val nextDateString = record.time.toDateString()
            if (currentDateString != nextDateString) {
                currentDateString = nextDateString
                result.add(MainRecyclerRow.DayRow(currentDateString))
            }
            date.time = record.time
            result.add(MainRecyclerRow.RecordRow(
                df.format(date),
                record.systolic.toString(),
                record.diastolic.toString(),
                record.pulse.toString()
            ))
        }

        return result
    }

    private fun Long.toDateString(): String {
        val date = Date()
        date.time = this

        @Suppress("SpellCheckingInspection")
        val df = SimpleDateFormat("d MMMM yyyy", Locale.getDefault())

        return df.format(date)
    }
}