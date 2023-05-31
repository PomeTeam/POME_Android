package com.teampome.pome.presentation.mypage.recyclerview.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teampome.pome.databinding.ItemMypageGoalStoreViewBinding
import com.teampome.pome.databinding.ItemMypageMarshmallowContentViewBinding
import com.teampome.pome.databinding.ItemMypageMarshmallowTitleViewBinding
import com.teampome.pome.databinding.ItemMypageUserViewBinding
import com.teampome.pome.model.mytab.MyPageView

/**
 *  data를 어떻게 하면 좋을까? -> 모든 데이터를 받는 하나의 모델을 만들까? 각자의 뷰에 맞게 타입 검사로 다양한 데이터들을 넣을까?
 *  ListAdapter : RecyclerView의 Data를 background thread로 업데이트, diffCallback을 자동으로 달아줌
 *
 */
class MyPageViewAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var pageData = listOf<MyPageView>()

    fun addPageDataList(data: List<MyPageView>) {
        pageData = data
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return pageData[position].viewType.type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(MyPageViewType.getViewType(viewType)) { // sealed class를 통해 else문 안쓰도록 사용
            MyPageViewType.UserView -> {
                val binding = ItemMypageUserViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)

                MyPageUserViewHolder(binding)
            }
            MyPageViewType.GoalStoreView -> {
                val binding = ItemMypageGoalStoreViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)

                MyPageGoalStoreViewHolder(binding)
            }
            MyPageViewType.MarshmallowTitleView -> {
                val binding = ItemMypageMarshmallowTitleViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)

                MyPageMarshMallowTitleViewHolder(binding)
            }
            MyPageViewType.MarshmallowContentView -> {
                val binding = ItemMypageMarshmallowContentViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)

                MyPageMarshMallowContentViewHolder(binding)
            }
        }
    }

    override fun getItemCount(): Int {
        return pageData.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is MyPageMarshMallowContentViewHolder -> {
                // 이떄 중첩 RecyclerView 적용
                (holder as MyPageMarshMallowContentViewHolder).bind()
            }
        }
    }
}