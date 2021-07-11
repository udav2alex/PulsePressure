package ru.gressor.pulsepressure.ui.editor

import ru.gressor.pulsepressure.entities.Record

interface SubmitRecordListener {
    fun submitRecord(record: Record)
}