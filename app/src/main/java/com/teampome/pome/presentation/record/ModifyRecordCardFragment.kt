package com.teampome.pome.presentation.record

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.prolificinteractive.materialcalendarview.*
import com.prolificinteractive.materialcalendarview.format.ArrayWeekDayFormatter
import com.prolificinteractive.materialcalendarview.format.MonthArrayTitleFormatter
import com.prolificinteractive.materialcalendarview.format.TitleFormatter
import com.teampome.pome.R
import com.teampome.pome.databinding.FragmentModifyRecordCardBinding
import com.teampome.pome.databinding.PomeCalendarBottomSheetDialogBinding
import com.teampome.pome.databinding.PomeTextListBottomSheetDialogBinding
import com.teampome.pome.util.CommonUtil
import com.teampome.pome.util.base.BaseFragment
import org.threeten.bp.DateTimeUtils
import org.threeten.bp.DayOfWeek
import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import java.text.SimpleDateFormat
import java.util.Date

class ModifyRecordCardFragment : BaseFragment<FragmentModifyRecordCardBinding>(R.layout.fragment_modify_record_card) {

    private val args: ModifyRecordCardFragmentArgs by navArgs()

    // goal bottom sheet
    private lateinit var goalBottomSheetDialog: BottomSheetDialog
    private lateinit var goalBottomSheetDialogBinding: PomeTextListBottomSheetDialogBinding

    // calendar bottom sheet
    private lateinit var calendarBottomSheetDialog: BottomSheetDialog
    private lateinit var calendarBottomSheetDialogBinding: PomeCalendarBottomSheetDialogBinding

    // 나중에 viewModel로 이동
    private var dateData: String? = null
    private var curDate: Date? = null

    @SuppressLint("SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.modifyRecordGoalAet.setText(args.currentCategory)

        // 일단 현재 시간으로...
        val now = System.currentTimeMillis()
        val date = Date(now)
        val sdf = SimpleDateFormat("yy.MM.dd")

        dateData = sdf.format(date)
        curDate = date

        binding.modifyRecordDateAet.setText(sdf.format(date))

        makeGoalBottomSheetDialog()
        makeCalendarBottomSheetDialog()
    }

    override fun initListener() {
        // 키보드 자연스럽게 처리
        binding.modifyRecordCardContainerCl.setOnClickListener {
            CommonUtil.hideKeyboard(requireActivity())
        }

        binding.modifyRecordLeftArrowAiv.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.modifyRecordDownArrowAiv.setOnClickListener {
            goalBottomSheetDialog.show()
        }

        binding.modifyRecordCalenderAiv.setOnClickListener {
            calendarBottomSheetDialog.show()
        }

        // 글자수 체크
        binding.modifyRecordContentAet.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                binding.modifyRecordContentCharacterCountAtv.text = "${p0?.length}/150"
            }
        })
    }

    private fun makeGoalBottomSheetDialog() {
        goalBottomSheetDialog = BottomSheetDialog(requireContext())
        goalBottomSheetDialogBinding = PomeTextListBottomSheetDialogBinding.inflate(layoutInflater, null, false)

        goalBottomSheetDialog.setContentView(goalBottomSheetDialogBinding.root)

        goalBottomSheetDialogBinding.title = "목표"
        goalBottomSheetDialogBinding.textListRv.adapter = TextListAdapter().apply {
            submitList(args.categoryList.asList())
            setOnGoalCategoryClickListener(object : OnGoalCategoryClickListener {
                override fun categoryClick(category: String) {
                    binding.modifyRecordGoalAet.setText(category)

                    goalBottomSheetDialog.dismiss()
                }
            })
        }
        goalBottomSheetDialogBinding.executePendingBindings()
    }

    private fun makeCalendarBottomSheetDialog() {
        calendarBottomSheetDialog = BottomSheetDialog(requireContext())
        calendarBottomSheetDialogBinding = PomeCalendarBottomSheetDialogBinding.inflate(layoutInflater, null, false)

        calendarBottomSheetDialog.setContentView(calendarBottomSheetDialogBinding.root)

        settingCustomCalendar()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun settingCustomCalendar() {
        calendarBottomSheetDialogBinding.calendarMcv.apply {
            // 첫 시작 요일 - 월요일
            state().edit().setFirstDayOfWeek(DayOfWeek.MONDAY).commit()

            // 한글 설정
            setTitleFormatter(MonthArrayTitleFormatter(resources.getTextArray(R.array.custom_months)))
            setWeekDayFormatter(ArrayWeekDayFormatter(resources.getTextArray(R.array.custom_weekdays)))

            // 헤더 폰트 설정
            setHeaderTextAppearance(R.style.Pome_SemiBold_16)

            // 선택시 드로어블 적용
            addDecorators(DayDecorator(requireContext()))

            isDynamicHeightEnabled = true

            // 현재 선택중인 날짜 설정
            val localDate = curDate?.let{Instant.ofEpochMilli(it.time).atZone(ZoneId.systemDefault()).toLocalDate()}
            setSelectedDate(localDate)

            setOnDateChangedListener { _, date, _ ->
                Toast.makeText(requireContext(), "date : $date", Toast.LENGTH_SHORT).show()
                val sdf = SimpleDateFormat("yy.MM.dd")
                val realDate = DateTimeUtils.toDate(date.date.atStartOfDay(ZoneId.systemDefault()).toInstant())

                dateData = sdf.format(realDate)
                curDate = realDate
            }
        }

        calendarBottomSheetDialogBinding.calendarMcv.setTitleFormatter(object : TitleFormatter{
            override fun format(day: CalendarDay?): CharSequence {
                day?.let {
                    val calendarElement = it.date.toString().split("-")
                    return "${calendarElement[0]}년 ${calendarElement[1]}월"
                } ?: return ""
            }
        })

        calendarBottomSheetDialogBinding.calendarSelectAtb.setOnClickListener {
            binding.modifyRecordDateAet.setText(dateData)
            calendarBottomSheetDialog.dismiss()
        }
    }
}

class DayDecorator(private val ctx: Context) : DayViewDecorator {
    override fun shouldDecorate(day: CalendarDay?): Boolean {
        return true
    }

    override fun decorate(view: DayViewFacade?) {
        ContextCompat.getDrawable(ctx, R.drawable.selector_calendar)
            ?.let { view?.setSelectionDrawable(it) }
    }
}