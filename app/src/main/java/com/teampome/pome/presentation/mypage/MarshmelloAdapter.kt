package com.teampome.pome.presentation.mypage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.teampome.pome.databinding.ItemMypageCardBinding
import com.teampome.pome.model.mytab.MyTabMarshmello

/// 마이탭 마시멜로우 어댑터
class MarshmelloAdapter : ListAdapter<MyTabMarshmello, MarshmelloAdapter.MyTabMarshmelloViewHolder>(BookDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyTabMarshmelloViewHolder {
        return MyTabMarshmelloViewHolder(
            ItemMypageCardBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        )
    }

    override fun onBindViewHolder(holder: MyTabMarshmelloViewHolder, position: Int) {
        val marshmello = currentList[position]
        holder.bind(marshmello)

    }

    inner class MyTabMarshmelloViewHolder(
        private val binding : ItemMypageCardBinding,
    ) : RecyclerView.ViewHolder(binding.root){

        fun bind(myTabMarshmello: MyTabMarshmello){
            binding.myTabMarshmello = myTabMarshmello
        }

    }

    companion object{
        private val BookDiffCallback = object : DiffUtil.ItemCallback<MyTabMarshmello>(){
            override fun areItemsTheSame(oldItem: MyTabMarshmello, newItem: MyTabMarshmello): Boolean {
                return oldItem.recordMarshmelloLv == newItem.recordMarshmelloLv
            }

            override fun areContentsTheSame(oldItem: MyTabMarshmello, newItem: MyTabMarshmello): Boolean {
                return oldItem == newItem
            }
        }
    }
}