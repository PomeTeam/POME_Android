package com.teampome.pome.presentation.record.recyclerview.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.teampome.pome.databinding.ItemRecordContentsCardBinding
import com.teampome.pome.databinding.ItemRecordEmotionCardBinding
import com.teampome.pome.model.RecordData
import com.teampome.pome.presentation.record.recyclerview.OnMoreItemClickListener
import com.teampome.pome.presentation.record.recyclerview.OnRecordItemClickListener

class RecordCardViewHolder(
    private val binding : ItemRecordContentsCardBinding,
    private val moreItemClickListener: OnMoreItemClickListener?,
    private val bodyClickListener: OnRecordItemClickListener?
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: RecordData) {
        binding.recordData = item

        binding.recordContentsMoreAiv.setOnClickListener {
            moreItemClickListener?.onMoreIconClick(item)
        }

        binding.recordContentsContainerCl.setOnClickListener {
            bodyClickListener?.onRecordItemClick(item)
        }

        binding.executePendingBindings()
    }
}