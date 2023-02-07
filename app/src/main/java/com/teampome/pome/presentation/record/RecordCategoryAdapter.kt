package com.teampome.pome.presentation.record

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.teampome.pome.databinding.ItemRecordCategoryChipBinding
import com.teampome.pome.model.goal.GoalCategory
import com.teampome.pome.model.goal.GoalCategoryResponse
import com.teampome.pome.presentation.remind.OnCategoryItemClickListener

class RecordCategoryAdapter : ListAdapter<GoalCategory, RecordCategoryAdapter.RecordCategoryViewHolder>(RecordCategoryDiffCallback()) {
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

            // onCreateViewHolder가 여러번 불리기 때문에 init{}은 넣지 말아야함.

            fun bind(item: GoalCategory) {
                binding.goalCategory = item

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

class RecordCategoryDiffCallback : DiffUtil.ItemCallback<GoalCategory>() {
    override fun areItemsTheSame(
        oldItem: GoalCategory,
        newItem: GoalCategory
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: GoalCategory,
        newItem: GoalCategory
    ): Boolean {
        return oldItem == newItem
    }
}