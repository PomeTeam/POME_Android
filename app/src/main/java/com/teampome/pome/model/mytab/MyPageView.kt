package com.teampome.pome.model.mytab

import com.teampome.pome.presentation.mypage.recyclerview.main.MyPageViewType

data class MyPageView(
    val viewType: MyPageViewType = MyPageViewType.GoalStoreView,
    val marshmello: List<MyTabMarshmello>? = null,
    val userName: String? = null,
    val userProfileUrl: String? = null
)