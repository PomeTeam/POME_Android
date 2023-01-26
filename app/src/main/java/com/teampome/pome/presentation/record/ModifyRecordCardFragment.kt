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
    }

    override fun initView() {
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

        // 수정했어요 클릭
        binding.modifyRecordCheckAcb.setOnClickListener {
            findNavController().navigateUp()
        }
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

        CommonUtil.settingAlreadySelectedCalendarBottomSheetDialog(
            requireContext(),
            calendarBottomSheetDialogBinding.calendarMcv,
            calendarBottomSheetDialogBinding.calendarSelectAtb,
            curDate?.let{Instant.ofEpochMilli(it.time).atZone(ZoneId.systemDefault()).toLocalDate()},
            { date, str ->
                curDate = date
                dateData = str
            }
        ) {
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