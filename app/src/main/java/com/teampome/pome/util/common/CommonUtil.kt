package com.teampome.pome.util.common

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
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
import org.threeten.bp.*
import org.threeten.bp.format.DateTimeFormatter
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

object CommonUtil {

    /**
     *  check network
     */
    fun checkNetwork(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)

            capabilities?.let {
                if(capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    return true
                } else if(capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    return true
                } else return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN)
            } ?: run {
                return false
            }
        } else {
            val activeNetwork = connectivityManager.activeNetworkInfo

            return activeNetwork?.let {
                return activeNetwork.isConnectedOrConnecting
            } ?: run {
                return false
            }
        }
    }

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
                val sdf = SimpleDateFormat("yyyy.MM.dd")
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

    /**
     *  make price style
     */
    fun makePriceStyle(price: String?) : String {

        return if(!price.isNullOrEmpty()) {
            val df = DecimalFormat("#,###").apply {
                isDecimalSeparatorAlwaysShown = false
            }

            df.format(df.parse(price))
        } else {
            ""
        }
    }

    /**
     *  시간 차이 구하는 메소드
     *  end date - current date
     */
    fun calDiffDate(endDate: String?) : Int {
        endDate?.let {
            val sdf = SimpleDateFormat("yyyy.MM.dd")
            val ed = sdf.parse(endDate)
            val today = Calendar.getInstance().time.time

            val challengeDay = (ed.time - today) / (60 * 60 * 24 * 1000)

            return if(challengeDay >= 0) {
                challengeDay.toInt()
            } else {
                0
            }
        } ?: return 0
    }

    /**
     *  들어온 매개변수들이 한달 차이인지 확인하는 메소드
     *
     *  format = "yyyy.MM.dd"
     */
    fun isDiffLowerThanOneMonth(curDateStr: String?, endDateStr: String?): Boolean {
        val sdf = SimpleDateFormat("yyyy.MM.dd")

        curDateStr?.let {
            endDateStr?.let {
                val curDate = sdf.parse(curDateStr)
                val endDate = sdf.parse(endDateStr)

                val afterOneMonthDay = Calendar.getInstance()
                afterOneMonthDay.time = curDate

                afterOneMonthDay.add(Calendar.MONTH, 1)

                return (afterOneMonthDay.time >= endDate && curDate <= endDate)
            } ?: run {
                return false
            }
        } ?: run {
            return false
        }
    }

    /**
     *  서버 상호작용을 위한 num -> Emotion 변경
     */
    fun numToEmotion(num: Int?): Emotion {
        return num?.let {
            when(it) {
                0 -> {
                    Emotion.HAPPY_EMOTION
                }
                1 -> {
                    Emotion.WHAT_EMOTION
                }
                2 -> {
                    Emotion.SAD_EMOTION
                }
                else -> {
                    Emotion.EMPTY_EMOTION
                }
            }
        } ?: Emotion.EMPTY_EMOTION
    }

    /**
     *  서버 상호작용을 위한 Emotion -> Num 변경
     */
    fun emotionToNum(emotion: Emotion): Int {
        return when(emotion) {
            Emotion.HAPPY_EMOTION -> {
                0
            }
            Emotion.WHAT_EMOTION -> {
                1
            }
            Emotion.SAD_EMOTION -> {
                2
            }
            else -> {
                0
            }
        }
    }

    /**
     *  서버 상호작용을 위한 Emotion -> Num 변경
     *  Int? type 리턴
     */
    fun emotionToNum(emotion: Emotion?): Int? {
        return when(emotion) {
            Emotion.HAPPY_EMOTION -> {
                0
            }
            Emotion.WHAT_EMOTION -> {
                1
            }
            Emotion.SAD_EMOTION -> {
                2
            }
            else -> {
                null
            }
        }
    }

    /**
     *  오늘 기준으로 시간 표현을 위한 메소드
     *  1시간 전 -> ~분 전
     *  1시간 후 -> ~시간 전
     *  다음날부터 -> ~일 전
     *
     *  @param createdAt : createdAt=2023-02-08T09:52:42.034674
     */
    fun changeAfterTime(createdAt: String) : String {
        val date = Date(System.currentTimeMillis())
        val sdf = SimpleDateFormat("yyyy.MM.dd_HH:mm")

        val nowDate = sdf.format(date)
        var diffDate = createdAt
            .replace("-", ".")
            .replace("T", "_")

        val diffTmp = diffDate.lastIndexOf(":")

        if(diffTmp > 0) {
           diffDate = diffDate.substring(0, diffTmp)
        }

        val diffYear = nowDate.substring(0,4).toInt() - diffDate.substring(0,4).toInt()
        val diffMonth = nowDate.substring(5,7).toInt() - diffDate.substring(5,7).toInt()
        val diffDay = nowDate.substring(8,10).toInt() - diffDate.substring(8,10).toInt()
        val diffHour = nowDate.substring(11,13).toInt() - diffDate.substring(11,13).toInt()
        val diffMin = nowDate.substring(14,16).toInt() - diffDate.substring(14,16).toInt()

//        Log.d("test", "now : $nowDate , diff : $diffDate")
//        Log.d("test", "y : $diffYear , M : $diffMonth, d : $diffDay , h : $diffHour, m : $diffMin")

        return if(diffYear != 0) {
            "$diffYear" + "년 전"
        } else if(diffMonth != 0) {
            "$diffMonth" + "달 전"
        } else if(diffDay != 0) {
            "$diffDay" + "일 전"
        } else if(diffHour >= 2 || ((diffHour == 1) && (diffMin > 0))) {
            "$diffHour" + "시간 전"
        } else if(diffMin < 0) {
            "${60 + diffMin}" + "분 전"
        } else {
            "$diffMin" + "분 전"
        }
    }

    /**
     *  년 월 일 변환
     *  @param date : 23.02.08
     */
    fun changeStringDate(date: String) : String {
        val sdf = SimpleDateFormat("yy")

        val nowYear = sdf.format(Date(System.currentTimeMillis()))

        val dateArr = date.split(".").toMutableList()
        val useYearLen = dateArr[0].length
        if(useYearLen > 2) {
            dateArr[0] = dateArr[0].substring(useYearLen-2, useYearLen)
        }

//        Log.d("year", "now : $nowYear , date : ${dateArr[0]}")

        // 월과 일에 toInt처리를 해서 02 -> 2형식으로 변환
        return if(nowYear == dateArr[0]) {
            "${dateArr[1].toInt()}월 ${dateArr[2].toInt()}일"
        } else {
            "${dateArr[0]}년 ${dateArr[1].toInt()}월 ${dateArr[2].toInt()}일"
        }
    }

    /**
     *  String to LocalDate
     *  String 형태 -> yy.mm.dd(ex. 23.02.08)
     */
    fun stringToLocalDate(date: String) : LocalDate {
        return LocalDate.parse("20${date.replace(".", "-")}", DateTimeFormatter.ISO_DATE)
    }

    /**
     *  webPage 이동 메소드
     */
    fun goToWebPage(context: Context, page: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(page))
        context.startActivity(intent)
    }
}