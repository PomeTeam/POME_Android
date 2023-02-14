package com.teampome.pome.presentation.record

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.teampome.pome.databinding.ItemRecordEmotionCardBinding
import com.teampome.pome.model.RecordData
import com.teampome.pome.util.OnItemClickListener

class RecordContentsCardAdapter : ListAdapter<RecordData, RecordContentsCardAdapter.RecordContentsCardViewHolder>(
    RecordContentsCardDiffCallback()
) {
    private lateinit var binding: ItemRecordEmotionCardBinding
    private var moreItemClickListener: OnMoreItemClickListener? = null

    private var bodyClickListener: OnRecordItemClickListener? = null

    fun setOnMoreItemClickListener(listener: OnMoreItemClickListener) {
        moreItemClickListener = listener
    }

    fun setOnBodyClickListener(listener: OnRecordItemClickListener) {
        bodyClickListener = listener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecordContentsCardViewHolder {
        binding = ItemRecordEmotionCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return RecordContentsCardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecordContentsCardViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class RecordContentsCardViewHolder(
        binding : ItemRecordEmotionCardBinding
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
}

class RecordContentsCardDiffCallback : DiffUtil.ItemCallback<RecordData>() {
    override fun areItemsTheSame(oldItem: RecordData, newItem: RecordData): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: RecordData, newItem: RecordData): Boolean {
        return oldItem == newItem
    }
}