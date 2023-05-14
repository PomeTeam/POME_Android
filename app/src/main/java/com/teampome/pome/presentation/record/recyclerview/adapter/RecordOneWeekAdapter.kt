package com.teampome.pome.presentation.record.recyclerview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.teampome.pome.databinding.ItemRecordEmotionCardBinding
import com.teampome.pome.model.RecordData
import com.teampome.pome.presentation.record.recyclerview.OnRecordItemClickListener

class RecordOneWeekAdapter : ListAdapter<RecordData, RecordOneWeekAdapter.RecordOneWeekCardViewHolder>(
    RecordContentsCardDiffCallback()
) {

    private var recordBodyClickListener: OnRecordItemClickListener? = null

    fun setOnRecordBodyClickListener(listener: OnRecordItemClickListener) {
        recordBodyClickListener = listener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecordOneWeekCardViewHolder {
            val binding = ItemRecordEmotionCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)

            return RecordOneWeekCardViewHolder(binding)
        }

    override fun onBindViewHolder(holder: RecordOneWeekCardViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    inner class RecordOneWeekCardViewHolder(
        private val binding : ItemRecordEmotionCardBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: RecordData) {
            binding.recordData = item

            binding.executePendingBindings()
        }
    }
}