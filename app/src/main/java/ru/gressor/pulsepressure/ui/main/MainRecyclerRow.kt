package ru.gressor.pulsepressure.ui.main

sealed class MainRecyclerRow {

    data class DayRow(val date: String) : MainRecyclerRow()

    data class RecordRow(
        val time: String,
        val systolic: String,
        val diastolic: String,
        val pulse: String
    ) : MainRecyclerRow()
}