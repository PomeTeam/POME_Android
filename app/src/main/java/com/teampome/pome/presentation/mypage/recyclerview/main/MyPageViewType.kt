package com.teampome.pome.presentation.mypage.recyclerview.main

sealed class MyPageViewType(val type: Int) {
    object UserView: MyPageViewType(0)
    object GoalStoreView: MyPageViewType(1)
    object MarshmallowTitleView: MyPageViewType(2)
    object MarshmallowContentView: MyPageViewType(3)

    companion object {
        fun getViewType(type: Int) : MyPageViewType {
            return when(type) {
                0 -> UserView
                1 -> GoalStoreView
                2 -> MarshmallowTitleView
                3 -> MarshmallowContentView
                else -> throw NoSupportMyPageViewTypeException()
            }
        }
    }
}

class NoSupportMyPageViewTypeException: Exception() {
    override val message: String
        get() = "MyPageViewType을 벗어났습니다."
}