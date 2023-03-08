package com.teampome.pome.presentation.record

import androidx.recyclerview.widget.RecyclerView
import com.teampome.pome.databinding.ItemRecordEmotionCardBinding
import com.teampome.pome.model.RecordData

class RecordCardViewHolder(
    private val binding : ItemRecordEmotionCardBinding,
    private val moreItemClickListener: OnMoreItemClickListener?,
    private val bodyClickListener: OnRecordItemClickListener?
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: RecordData) {
        binding.recordData = item

        binding.recordContentsCardMoreAiv.setOnClickListener {
            moreItemClickListener?.onMoreIconClick(item)
        }

        binding.recordContentsCardContainerCv.setOnClickListener {
            bodyClickListener?.onRecordItemClick(item)
        }

        binding.executePendingBindings()
    }
}