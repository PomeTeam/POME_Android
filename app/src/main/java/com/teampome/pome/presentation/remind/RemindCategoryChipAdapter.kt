package com.teampome.pome.presentation.remind

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.teampome.pome.databinding.ItemRemindCategoryChipBinding

//class RemindCategoryChipAdapter : ListAdapter<RemindCategoryData, RemindCategoryChipAdapter.RemindCategoryChipViewHolder>(
//    RemindCategoryDiffCallback()
//) {
//    private lateinit var binding: ItemRemindCategoryChipBinding
//
//    // 카테고리 클릭 관리를 위한 변수
//    private var onCategoryItemClickListener: OnCategoryItemClickListener? = null
//    private var selectedPosition = 0
//
//    fun setOnItemClickListener(listener: OnCategoryItemClickListener) {
//        onCategoryItemClickListener = listener
//    }
//
//    override fun onCreateViewHolder(
//        parent: ViewGroup,
//        viewType: Int
//    ): RemindCategoryChipViewHolder {
//        binding = ItemRemindCategoryChipBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//
//        return RemindCategoryChipViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: RemindCategoryChipViewHolder, position: Int) {
//        holder.bind(getItem(position))
//    }
//
//    inner class RemindCategoryChipViewHolder(
//        val binding : ItemRemindCategoryChipBinding
//    ) : RecyclerView.ViewHolder(binding.root) {
//
//        fun bind(remindCategoryData: RemindCategoryData) {
//            binding.remindCategoryData = remindCategoryData
//
//            Log.d("test", "$selectedPosition -> $adapterPosition")
//
//            currentList[adapterPosition].isSelected = selectedPosition == adapterPosition
//            submitList(currentList)
//
//            onCategoryItemClickListener?.let { listener ->
//                binding.remindCategoryChipTv.setOnClickListener {
////                    listener.onCategoryItemClick(remindCategoryData, adapterPosition)
////                    if(selectedPosition != adapterPosition) {
////                        selectedPosition = adapterPosition
////                        notifyDataSetChanged()
////                    }
//                }
//            }
//        }
//    }
//}
//
//// 왜 상속인데 인스턴스를 넣는거지? => 인스턴스를 상속해야 부모 클래스의 구현체들을 이용할 수 있을 것 같음
//class RemindCategoryDiffCallback : DiffUtil.ItemCallback<RemindCategoryData>() {
//    override fun areItemsTheSame(oldItem: RemindCategoryData, newItem: RemindCategoryData): Boolean {
//        return oldItem == newItem
//    }
//
//    override fun areContentsTheSame(oldItem: RemindCategoryData, newItem: RemindCategoryData): Boolean {
//        return oldItem == newItem
//    }
//}