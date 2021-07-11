package ru.gressor.pulsepressure.ui.editor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.gressor.pulsepressure.databinding.FragmentEditorBinding
import ru.gressor.pulsepressure.entities.Record
import ru.gressor.pulsepressure.ui.BaseFragment
import java.util.*

class EditorFragment : BaseFragment<FragmentEditorBinding>() {
    private var listener: SubmitRecordListener? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        listener = context as? SubmitRecordListener

        binding.commitButton.setOnClickListener {
            try {
                val sys = binding.systolicEditText.text.toString().toInt()
                val dia = binding.diastolicEditText.text.toString().toInt()
                val pulse = binding.pulseEditText.text.toString().toInt()

                listener?.submitRecord(
                    Record(Date().time, sys, dia, pulse)
                )
            } catch (e: Throwable) {
                showError(e)
            }
        }
    }

    override fun getBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentEditorBinding.inflate(inflater, container, false)
}