package com.teampome.pome.util

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Point
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.WindowManager
import android.view.WindowManager.LayoutParams
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import androidx.annotation.DrawableRes
import androidx.annotation.RawRes
import androidx.core.content.res.ResourcesCompat
import com.bumptech.glide.Glide
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.format.ArrayWeekDayFormatter
import com.prolificinteractive.materialcalendarview.format.MonthArrayTitleFormatter
import com.prolificinteractive.materialcalendarview.format.TitleFormatter
import com.teampome.pome.R
import com.teampome.pome.databinding.PomeRemoveDialogBinding
import com.teampome.pome.presentation.record.DayDecorator
import com.teampome.pome.viewmodel.Emotion
import org.threeten.bp.DateTimeUtils
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate
import org.threeten.bp.ZoneId
import java.text.SimpleDateFormat
import java.util.*

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

    // device size
    fun getDeviceSize(context: Context): IntArray {
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay
        val size = Point()

        display.getSize(size)

        return intArrayOf(size.x, size.y)
    }

    // setting calendar
    fun settingCalendarBottomSheetDialog(
        context: Context,
        calendar : MaterialCalendarView,
        checkBtn : Button,
        dateCallback: (Date, String) -> Unit,
        btnCallback: () -> Unit
    ) {
        var isButtonEnabled = false

        // 초기 캘린더 버튼 설정
        disabledPomeBtn(checkBtn)

        calendar.apply {
            // 첫 시작 요일 - 월요일
            state().edit().setFirstDayOfWeek(DayOfWeek.MONDAY).commit()

            // 한글 설정
            setTitleFormatter(MonthArrayTitleFormatter(resources.getTextArray(R.array.custom_months)))
            setWeekDayFormatter(ArrayWeekDayFormatter(resources.getTextArray(R.array.custom_weekdays)))

            // 헤더 폰트 설정
            setHeaderTextAppearance(R.style.Pome_SemiBold_16)

            // 선택시 드로어블 적용
            addDecorators(DayDecorator(context))

            isDynamicHeightEnabled = true

            setOnDateChangedListener { _, date, _ ->
                val sdf = SimpleDateFormat("yy.MM.dd")
                val realDate = DateTimeUtils.toDate(date.date.atStartOfDay(ZoneId.systemDefault()).toInstant())

                val dateStr = sdf.format(realDate)

                dateCallback(realDate, dateStr)

                if(!isButtonEnabled) {
                    isButtonEnabled = true

                    enabledPomeBtn(checkBtn)
                }
            }

            setTitleFormatter(object : TitleFormatter {
                override fun format(day: CalendarDay?): CharSequence {
                    day?.let {
                        val calendarElement = it.date.toString().split("-")
                        return "${calendarElement[0]}년 ${calendarElement[1]}월"
                    } ?: return ""
                }
            })
        }

        checkBtn.setOnClickListener {
            btnCallback()
        }
    }

    fun enabledPomeBtn(
        button: Button
    ) {
        button.isClickable = true
        button.isEnabled = true
        button.setBackgroundResource(R.drawable.register_profile_name_check_available_btn_background)
    }

    fun disabledPomeBtn(
        button: Button
    ) {
        button.isClickable = false
        button.isEnabled = false
        button.setBackgroundResource(R.drawable.register_profile_name_check_disable_btn_background)
    }

    fun settingAlreadySelectedCalendarBottomSheetDialog(
        context: Context,
        calendar: MaterialCalendarView,
        checkBtn: Button,
        localDate: LocalDate?,
        dateCallback: (Date, String) -> Unit,
        btnCallback: () -> Unit
    ) {
        settingCalendarBottomSheetDialog(
            context,
            calendar,
            checkBtn,
            dateCallback,
            btnCallback
        )

        // 현재 선택중인 날짜 설정
        calendar.setSelectedDate(localDate)
    }


    fun showBackButtonDialog(
        context: Context,
        title: String,
        subtitle: String,
        @DrawableRes @RawRes imgDrawable: Int,
        yesText: String,
        noText: String,
        onYesClick: () -> Unit,
        onNoClick: () -> Unit
    ) {
        // Todo : 일단 pome_remove_dialog를 재사용 : trash랑 yes와 No가 반대임
        val backButtonDialogBinding = PomeRemoveDialogBinding.inflate(LayoutInflater.from(context), null, false)
        val backButtonDialog = Dialog(context)

        backButtonDialog.setContentView(backButtonDialogBinding.root)

        backButtonDialogBinding.apply {
            removeDialogTitleAtv.text = title
            removeDialogSubtitleAtv.text = subtitle
            removeYesTextAtv.text = noText
            removeYesTextAtv.setTextColor(ResourcesCompat.getColor(context.resources, R.color.grey_5, null))
            removeNoTextAtv.text = yesText

            Glide.with(removeTrashAiv)
                .load(imgDrawable)
                .into(removeTrashAiv)

            removeYesTextAtv.setOnClickListener {
                onNoClick()
                backButtonDialog.dismiss()
            }

            removeNoTextAtv.setOnClickListener {
                onYesClick()
                backButtonDialog.dismiss()
            }
        }

        backButtonDialog.show()
    }
}