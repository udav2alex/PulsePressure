package ru.gressor.pulsepressure.ui.editor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.gressor.pulsepressure.databinding.FragmentEditorBinding
import ru.gressor.pulsepressure.entities.Record
import ru.gressor.pulsepressure.ui.BaseFragment
import java.text.SimpleDateFormat
import java.util.*

class EditorFragment : BaseFragment<FragmentEditorBinding>() {
    private var listener: SubmitRecordListener? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        listener = context as? SubmitRecordListener

        val date = Date()
        val df = SimpleDateFormat("d MMMM yyyy, HH:mm", Locale.getDefault())
        binding.dateTimeTextView.text = df.format(date)

        binding.commitButton.setOnClickListener {
            try {
                val sys = binding.systolicEditText.text.toString().toInt()
                val dia = binding.diastolicEditText.text.toString().toInt()
                val pulse = binding.pulseEditText.text.toString().toInt()

                listener?.submitRecord(
                    Record(date.time, sys, dia, pulse)
                )
            } catch (e: Throwable) {
                showError(e)
            }
        }
    }

    override fun getBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentEditorBinding.inflate(inflater, container, false)
}