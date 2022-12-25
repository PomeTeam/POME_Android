package com.teampome.pome.util

import android.app.Activity
import android.content.Context
import android.view.WindowManager.LayoutParams
import android.view.inputmethod.InputMethodManager
import com.teampome.pome.viewmodel.Emotion

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

    // Todo : 화면밀기 동작 안함
    /**
     *  keyboard가 화면을 밀게 설정
     */
    fun inputModePan(activity: Activity) {
        activity.window.setSoftInputMode(LayoutParams.SOFT_INPUT_ADJUST_PAN)
    }

    /**
     *  keyboard가 화면을 못밀게 설정
     */
    fun inputModeNothing(activity: Activity) {
        activity.window.setSoftInputMode(LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
    }

    /**
     *  emotion 정보를 가져오기
     */
    fun getEmotionData(emotionStr: String) : Emotion? {
        return when(emotionStr) {
            Constants.HAPPY_EMOTION -> {
                Emotion.HAPPY_EMOTION
            }
            Constants.WHAT_EMOTION -> {
                Emotion.WHAT_EMOTION
            }
            Constants.SAD_EMOTION -> {
                Emotion.SAD_EMOTION
            }
            else -> {
                null
            }
        }
    }

    fun getPixelToDp(context: Context, wantDp: Int): Int {
        val scale = context.resources.displayMetrics.density

        return (wantDp * scale + 0.5f).toInt()
    }
}