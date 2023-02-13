package com.teampome.pome.presentation.record

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.prolificinteractive.materialcalendarview.*
import com.teampome.pome.R
import com.teampome.pome.databinding.FragmentModifyRecordCardBinding
import com.teampome.pome.databinding.PomeCalendarBottomSheetDialogBinding
import com.teampome.pome.util.CommonUtil
import com.teampome.pome.util.base.BaseFragment
import java.util.Date

class ModifyRecordCardFragment : BaseFragment<FragmentModifyRecordCardBinding>(R.layout.fragment_modify_record_card) {

    private val args: ModifyRecordCardFragmentArgs by navArgs()

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
        binding.modifyRecordDateAet.setText(args.recordData.useDate)
        binding.modifyRecordPriceAet.setText(args.recordData.usePrice.toString())
        binding.modifyRecordContentAet.setText(args.recordData.useComment)

        binding.content = args.recordData.useComment
        binding.price = args.recordData.usePrice.toString()

        binding.executePendingBindings()

        makeCalendarBottomSheetDialog()
    }

    override fun initListener() {
        // 키보드 자연스럽게 처리
        binding.modifyRecordCardContainerCl.setOnClickListener {
            CommonUtil.hideKeyboard(requireActivity())
        }

        binding.modifyRecordLeftArrowAiv.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.modifyRecordCalenderAiv.setOnClickListener {
            calendarBottomSheetDialog.show()
        }

        // 수정했어요 클릭
        binding.modifyRecordCheckAcb.setOnClickListener {
//            findNavController().popBackStack()
        }
    }

    private fun makeCalendarBottomSheetDialog() {
        calendarBottomSheetDialog = BottomSheetDialog(requireContext()).apply {
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
        calendarBottomSheetDialogBinding = PomeCalendarBottomSheetDialogBinding.inflate(layoutInflater, null, false)

        calendarBottomSheetDialog.setContentView(calendarBottomSheetDialogBinding.root)

        CommonUtil.settingAlreadySelectedCalendarBottomSheetDialog(
            requireContext(),
            calendarBottomSheetDialogBinding.calendarMcv,
            calendarBottomSheetDialogBinding.calendarSelectAtb,
            CommonUtil.stringToLocalDate(args.recordData.useDate),
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