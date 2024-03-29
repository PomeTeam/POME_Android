package com.teampome.pome.presentation.record.recyclerview.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.teampome.pome.databinding.ItemLeaveEmotionCardBinding
import com.teampome.pome.util.common.OnItemClickListener

class LeaveEmotionCardViewHolder(
    private val binding: ItemLeaveEmotionCardBinding,
    private val onItemClickListener: OnItemClickListener?
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(count: Int) {
        binding.countOneWeekRecord = count

        binding.executePendingBindings()

        binding.recordWriteEmotionContainerCl.setOnClickListener {
            onItemClickListener?.itemClick()
        }
    }
}