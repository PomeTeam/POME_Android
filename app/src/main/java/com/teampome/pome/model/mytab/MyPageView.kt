package com.teampome.pome.model.mytab

import com.teampome.pome.presentation.mypage.recyclerview.main.MyPageViewType

data class MyPageView(
    val viewType: MyPageViewType = MyPageViewType.GoalStoreView,
    val marshmello: MyTabMarshmello?
)