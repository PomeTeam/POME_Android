@file:Suppress("DEPRECATION")

package com.teampome.pome.util

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.view.LayoutInflater
import android.view.WindowInsets
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
import org.threeten.bp.*
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

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
    private fun getDeviceSize(context: Context): IntArray {
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val windowMetrics = windowManager.currentWindowMetrics
            val windowInsets = windowMetrics.windowInsets

            val insets = windowInsets.getInsetsIgnoringVisibility(
                WindowInsets.Type.navigationBars() or WindowInsets.Type.displayCutout()
            )
            val insetsWidth = insets.right + insets.left
            val insetsHeight = insets.top + insets.bottom

            val bounds = windowMetrics.bounds

            return intArrayOf(bounds.width() - insetsWidth, bounds.height() - insetsHeight)
        } else {
            val display = windowManager.defaultDisplay
            val size = Point()

            display?.getSize(size)

            return intArrayOf(size.x, size.y)
        }
    }

    fun dateToLocalDate(date: Date) : LocalDate {
        return Instant.ofEpochMilli(date.time).atZone(ZoneId.systemDefault()).toLocalDate()
    }

    fun getCurrentDate(): Date {
        val now = System.currentTimeMillis()

        return Date(now)
    }

    fun getCurrentDateString(): String {
        val now = System.currentTimeMillis()
        val date = Date(now)
        val sdf = SimpleDateFormat("yy.MM.dd")

        return sdf.format(date)
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

        makePomeDialog(backButtonDialog)

        backButtonDialog.show()
    }

    fun makePomeDialog(dialog: Dialog) {
        val deviceSize = getDeviceSize(dialog.context)

        dialog.window?.let {
            it.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
            it.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            it.setBackgroundDrawable(ResourcesCompat.getDrawable(dialog.context.resources, R.drawable.white_r8_background, null))

            val params = it.attributes
            params.width = (deviceSize[0] * 0.75).toInt()
            it.attributes = params
        }
    }

    /**
     *  Random Text를 만드는 메소드
     */
    private val randomChar = "a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z".split(",")

    fun getRandomString(len: Int) : String {
        val sb = java.lang.StringBuilder()

        for(i in 0 until len) {
            sb.append(randomChar[(randomChar.indices).random(Random(System.currentTimeMillis()))])
        }

        return sb.toString()
    }

    /**
     *  image file to byteArray
     */
    @SuppressLint("Recycle")
    fun getImageByteArray(context: Context, uri: Uri) : ByteArray? {
        return context.contentResolver.openInputStream(uri)?.buffered()?.use { it.readBytes() }
    }
}