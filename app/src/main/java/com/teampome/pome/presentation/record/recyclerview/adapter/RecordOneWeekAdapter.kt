package com.teampome.pome.presentation.record.recyclerview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.teampome.pome.databinding.ItemRecordEmotionCardBinding
import com.teampome.pome.model.RecordData
import com.teampome.pome.presentation.record.recyclerview.OnRecordItemClickListener
import com.teampome.pome.presentation.record.recyclerview.viewholder.RecordCardViewHolder

class RecordOneWeekAdapter : ListAdapter<RecordData, RecordCardViewHolder>(
    RecordContentsCardDiffCallback()
) {

    private var recordBodyClickListener: OnRecordItemClickListener? = null

    fun setOnRecordBodyClickListener(listener: OnRecordItemClickListener) {
        recordBodyClickListener = listener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecordCardViewHolder {
            val binding = ItemRecordEmotionCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)

            return RecordCardViewHolder(binding, null, recordBodyClickListener)
        }

    override fun onBindViewHolder(holder: RecordCardViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }
}