package com.teampome.pome.presentation.record

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.teampome.pome.databinding.ItemRecordCategoryChipBinding
import com.teampome.pome.model.RemindCategoryData
import com.teampome.pome.presentation.remind.OnCategoryItemClickListener

class RecordCategoryAdapter : ListAdapter<RemindCategoryData, RecordCategoryAdapter.RecordCategoryViewHolder>(RecordCategoryDiffCallback()) {
    lateinit var bind: ItemRecordCategoryChipBinding

    // category click 관리 변수
    private var onCategoryItemClickListener: OnCategoryItemClickListener? = null
    private var selectedPosition = 0

    fun setOnItemClickListener(categoryItemClickListener: OnCategoryItemClickListener) {
        onCategoryItemClickListener = categoryItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordCategoryViewHolder {
        bind = ItemRecordCategoryChipBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return RecordCategoryViewHolder(bind)
    }

    override fun onBindViewHolder(holder: RecordCategoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class RecordCategoryViewHolder(
        val binding: ItemRecordCategoryChipBinding) : RecyclerView.ViewHolder(binding.root) {

            fun bind(item: RemindCategoryData) {
                binding.remindCategoryData = item

                currentList[adapterPosition].isSelected = selectedPosition == adapterPosition
                submitList(currentList)

                onCategoryItemClickListener?.let { listener ->
                    binding.remindCategoryChipTv.setOnClickListener {
                        listener.onCategoryItemClick(item, adapterPosition)
                        if(selectedPosition != adapterPosition) {
                            selectedPosition = adapterPosition
                            notifyDataSetChanged()
                        }
                    }
                }

                binding.executePendingBindings()
            }
        }
}

class RecordCategoryDiffCallback : DiffUtil.ItemCallback<RemindCategoryData>() {
    override fun areItemsTheSame(
        oldItem: RemindCategoryData,
        newItem: RemindCategoryData
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: RemindCategoryData,
        newItem: RemindCategoryData
    ): Boolean {
        return oldItem == newItem
    }
}