package com.teampome.pome.util

import android.app.Activity
import android.content.Context
import android.view.WindowManager.LayoutParams
import android.view.inputmethod.InputMethodManager

object CommonUtil {

    /**
     *  키보드 자연스럽게 처리를 위한 메소드 (키보드 바깥쪽 클릭시 키보드 hide)
     */
    fun hideKeyboard(activity: Activity) {
        if(activity.currentFocus != null) {
            val inputManager: InputMethodManager =
                activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

            inputManager.hideSoftInputFromWindow(
                activity.currentFocus!!.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }

    /**
     *  keyboard가 화면을 밀게 설정
     */
    fun inputModeResize(activity: Activity) {
        activity.window.setSoftInputMode(LayoutParams.SOFT_INPUT_ADJUST_PAN)
    }

    /**
     *  keyboard가 화면을 못밀게 설정
     */
    fun inputModeNothing(activity: Activity) {
        activity.window.setSoftInputMode(LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
    }
}