package ru.gressor.pulsepressure.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import ru.gressor.pulsepressure.databinding.ItemDayBinding
import ru.gressor.pulsepressure.databinding.ItemRecordBinding

class MainRecyclerAdapter : RecyclerView.Adapter<MainRecyclerAdapter.DataViewHolder>() {
    private val mutableList = mutableListOf<MainRecyclerRow>()

    fun populate(collection: Collection<MainRecyclerRow>) {
        mutableList.clear()
        mutableList.addAll(collection)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
        VIEW_TYPE_DAY -> DayViewHolder(
            ItemDayBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
        else -> RecordViewHolder(
            ItemRecordBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemViewType(position: Int) = when (mutableList[position]) {
        is MainRecyclerRow.DayRow -> VIEW_TYPE_DAY
        else -> VIEW_TYPE_RECORD
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        when (mutableList[position]) {
            is MainRecyclerRow.DayRow ->
                (holder as DayViewHolder).bind(mutableList[position] as MainRecyclerRow.DayRow)
            else ->
                (holder as RecordViewHolder).bind(mutableList[position] as MainRecyclerRow.RecordRow)
        }
    }

    override fun getItemCount(): Int = mutableList.size

    abstract class DataViewHolder(protected val binding: ViewBinding) :
        RecyclerView.ViewHolder(binding.root)

    inner class DayViewHolder(
        binding: ItemDayBinding
    ) : DataViewHolder(binding) {

        fun bind(dayRow: MainRecyclerRow.DayRow) {
            with(binding as ItemDayBinding) {
                dateTextView.text = dayRow.date
            }
        }
    }

    inner class RecordViewHolder(
        binding: ItemRecordBinding
    ) : DataViewHolder(binding) {

        fun bind(recordRow: MainRecyclerRow.RecordRow) {
            with(binding as ItemRecordBinding) {
                timeTextView.text = recordRow.time
                systolicTextView.text = recordRow.systolic
                diastolicTextView.text = recordRow.diastolic
                pulseTextView.text = recordRow.pulse
            }
        }
    }

    companion object {
        private const val VIEW_TYPE_DAY = 0
        private const val VIEW_TYPE_RECORD = 1
    }
}