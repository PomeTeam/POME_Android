package com.teampome.pome.presentation.mypage.recyclerview.main

sealed class MyPageViewType {
    object UserView: MyPageViewType()
    object GoalStoreView: MyPageViewType()
    object MarshmallowTitleView: MyPageViewType()
    object MarshmallowContentView: MyPageViewType()
}