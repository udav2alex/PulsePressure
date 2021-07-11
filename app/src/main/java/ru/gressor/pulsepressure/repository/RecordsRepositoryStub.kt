package ru.gressor.pulsepressure.repository

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import ru.gressor.pulsepressure.entities.Record
import java.util.*

class RecordsRepositoryStub : RecordsRepository {
    private val recordSet = mutableSetOf<Record>(
        Record(Date().time - 600_000, 120, 80, 60),
        Record(Date().time - 3_600_000, 120, 80, 63),
        Record(Date().time - 73_200_000, 129, 90, 58),
        Record(Date().time - 113_400_000, 128, 85, 69),
        Record(Date().time - 10_700_000, 123, 82, 70),
        Record(Date().time - 53_100_000, 139, 95, 79),
        Record(Date().time - 39_900_000, 120, 80, 81),
        Record(Date().time - 23_800_000, 118, 78, 73),
        Record(Date().time - 93_300_000, 120, 80, 71),
        Record(Date().time - 133_500_000, 124, 81, 70),
    )
    private lateinit var callback: Callback

    @ExperimentalCoroutinesApi
    override fun records(): Flow<Set<Record>> = callbackFlow {
        channel.trySend(recordSet)

        callback = object : Callback {
            override fun addNewRecord(value: Record) {
                recordSet.add(value)
                channel.trySend(recordSet)
            }

            override fun onApiError(cause: Throwable) {
                cancel(CancellationException("API Error", cause))
            }

            override fun onCompleted() = channel.close()
        }

        awaitClose { }
    }

    override fun saveRecord(record: Record) {
        callback.addNewRecord(record)
    }

    interface Callback {
        fun addNewRecord(value: Record)
        fun onApiError(cause: Throwable)
        fun onCompleted(): Boolean
    }
}