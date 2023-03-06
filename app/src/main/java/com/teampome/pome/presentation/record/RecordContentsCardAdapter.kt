package com.teampome.pome.presentation.record

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.teampome.pome.databinding.ItemRecordEmotionCardBinding
import com.teampome.pome.model.RecordData
import com.teampome.pome.util.OnItemClickListener

class RecordContentsCardAdapter : PagingDataAdapter<RecordData, RecordContentsCardAdapter.RecordContentsCardViewHolder>(
    RecordContentsCardDiffCallback()
) {
    private lateinit var binding: ItemRecordEmotionCardBinding
    // 절대 Adapter 내부에 lateinit var을 넣지 말자.
    // bind를 lateinit하고 onCreateViewHolder에서 재사용하고 있어 메모리 누수가 발생됨
    // ex) RecyclerView에서 아이템이 추가될 때 마다 Binding 인스턴스가 할당되고 이전에 할당된 인스턴스가 GC로 회수되지 않을 가능성이 매우 큼
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

        Log.d("lifecycle", " call onCreateViewHolder")

        return RecordContentsCardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecordContentsCardViewHolder, position: Int) {
        getItem(position)?.let {
            Log.d("test", "data is $it")
            holder.bind(it)
        }
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