package ru.gressor.pulsepressure.entities

data class Record(
    val time: Long,
    val systolic: Int,
    val diastolic: Int,
    val pulse: Int
)