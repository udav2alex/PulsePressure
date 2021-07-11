package ru.gressor.pulsepressure.repository

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import ru.gressor.pulsepressure.entities.Record

class RecordsRepositoryFirestore : RecordsRepository {

    private val firestoreInstance = FirebaseFirestore.getInstance()
    private val recordsCollection: CollectionReference
        get() = firestoreInstance.collection("records")

    @ExperimentalCoroutinesApi
    override fun records(): Flow<Set<Record>> = callbackFlow {
        val subscription = recordsCollection
            .addSnapshotListener { snapshot, _ ->
                snapshot?.run {
                    val resultSet = mutableSetOf<Record>()

                    documents.forEach { document ->
                        document.data?.toRecord()?.let { resultSet.add(it) }
                    }

                    channel.trySend(resultSet)
                }
            }

        awaitClose { subscription.remove() }
    }

    override fun saveRecord(record: Record) {
        recordsCollection
            .document(record.time.toString())
            .set(record.toMap())
    }

    private fun Record.toMap(): Map<String, Any> = mapOf(
        "time" to time, "systolic" to systolic, "diastolic" to diastolic, "pulse" to pulse
    )

    private fun Map<String, Any>.toRecord() = Record(
        this["time"] as Long,
        (this["systolic"] as Long).toInt(),
        (this["diastolic"] as Long).toInt(),
        (this["pulse"] as Long).toInt()
    )
}