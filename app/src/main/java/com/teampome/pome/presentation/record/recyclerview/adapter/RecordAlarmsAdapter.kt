package com.teampome.pome.presentation.record.recyclerview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.teampome.pome.databinding.ItemRecordAlarmsBinding
import com.teampome.pome.model.TestAlarmsData

class RecordAlarmsAdapter : ListAdapter<TestAlarmsData, RecordAlarmsAdapter.RecordAlarmsViewHolder>(
    RecordAlarmsDiffCallback()
) {
    private lateinit var binding: ItemRecordAlarmsBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordAlarmsViewHolder {
        binding = ItemRecordAlarmsBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return RecordAlarmsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecordAlarmsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class RecordAlarmsViewHolder(val bind: ItemRecordAlarmsBinding) : RecyclerView.ViewHolder(bind.root) {
        fun bind(item: TestAlarmsData) {
            binding.testAlarmsData = item
            binding.executePendingBindings()
        }
    }
}

class RecordAlarmsDiffCallback() : DiffUtil.ItemCallback<TestAlarmsData>() {
    override fun areItemsTheSame(oldItem: TestAlarmsData, newItem: TestAlarmsData): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: TestAlarmsData, newItem: TestAlarmsData): Boolean {
        return oldItem == newItem
    }

}