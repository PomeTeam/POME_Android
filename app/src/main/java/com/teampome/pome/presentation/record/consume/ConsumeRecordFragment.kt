package com.teampome.pome.presentation.record.consume

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.teampome.pome.R
import com.teampome.pome.databinding.FragmentConsumeRecordBinding
import com.teampome.pome.databinding.PomeCalendarBottomSheetDialogBinding
import com.teampome.pome.databinding.PomeTextListBottomSheetDialogBinding
import com.teampome.pome.presentation.record.OnGoalCategoryClickListener
import com.teampome.pome.presentation.record.TextListAdapter
import com.teampome.pome.util.CommonUtil
import com.teampome.pome.util.CommonUtil.dateToLocalDate
import com.teampome.pome.util.CommonUtil.getCurrentDate
import com.teampome.pome.util.CommonUtil.getCurrentDateString
import com.teampome.pome.util.base.BaseFragment
import java.util.Date


class ConsumeRecordFragment : BaseFragment<FragmentConsumeRecordBinding>(R.layout.fragment_consume_record) {
    private lateinit var goalBottomSheetDialogBinding: PomeTextListBottomSheetDialogBinding
    private lateinit var goalBottomSheetDialog: BottomSheetDialog

    private lateinit var calendarBottomSheetDialogBinding: PomeCalendarBottomSheetDialogBinding
    private lateinit var calendarBottomSheetDialog: BottomSheetDialog

    private var selectedDate: Date? = null
    private var selectedDateStr: String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        makeGoalBottomSheetDialog()
        makeCalendarBottomSheetDialog()

        // 현재 날짜로 항상 시작
        binding.consumeRecordDateAet.setText(getCurrentDateString())

        CommonUtil.settingAlreadySelectedCalendarBottomSheetDialog(
            requireContext(),
            calendarBottomSheetDialogBinding.calendarMcv,
            calendarBottomSheetDialogBinding.calendarSelectAtb,
            dateToLocalDate(getCurrentDate()),
            {date, str ->
                selectedDate = date
                selectedDateStr = str
            }
        ) {
            binding.consumeRecordDateAet.setText(selectedDateStr)
            calendarBottomSheetDialog.dismiss()
        }
    }

    override fun initListener() {
        // 키보드 부드럽게
        binding.consumeRecordContainerCl.setOnClickListener {
            CommonUtil.hideKeyboard(requireActivity())
        }

        // 뒤로가기 버튼 클릭
        binding.consumeRecordBackButtonIv.setOnClickListener {
            findNavController().navigateUp()
        }

        // 목표 아래 화살표 클릭
        binding.consumeRecordDownArrowAiv.setOnClickListener {
            goalBottomSheetDialog.show()
        }

        // 캘린더 아이콘 클릭
        binding.consumeRecordCalenderAiv.setOnClickListener {
            calendarBottomSheetDialog.show()
        }

        // 선택했어요 클릭
        binding.consumeRecordCheckAcb.setOnClickListener {
            moveToConsumeEmotion()
        }

        // 글자수 체크
        binding.consumeRecordContentAet.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun afterTextChanged(p0: Editable?) {
                binding.consumeRecordContentCharacterCountAtv.text = "${p0?.length}/150"
            }
        })
    }

    private fun makeGoalBottomSheetDialog() {
        goalBottomSheetDialog = BottomSheetDialog(requireContext())
        goalBottomSheetDialogBinding = PomeTextListBottomSheetDialogBinding.inflate(layoutInflater, null, false)

        goalBottomSheetDialog.setContentView(goalBottomSheetDialogBinding.root)

        goalBottomSheetDialogBinding.apply {
            title = "목표"
            textListRv.adapter = TextListAdapter().apply {
                // 일단 임시 값 출력
                submitList(listOf(
                    "커피",
                    "택시",
                    "아이스크림"
                ))

                setOnGoalCategoryClickListener(object : OnGoalCategoryClickListener {
                    override fun categoryClick(category: String) {
                        binding.consumeRecordGoalAet.setText(category)

                        goalBottomSheetDialog.dismiss()
                    }
                })
            }
        }

        goalBottomSheetDialogBinding.executePendingBindings()
    }

    private fun makeCalendarBottomSheetDialog() {
        calendarBottomSheetDialogBinding = PomeCalendarBottomSheetDialogBinding.inflate(layoutInflater, null, false)

        calendarBottomSheetDialog = BottomSheetDialog(requireContext())
        calendarBottomSheetDialog.setContentView(calendarBottomSheetDialogBinding.root)
    }

    private fun moveToConsumeEmotion() {
        val action = ConsumeRecordFragmentDirections.actionConsumeRecordFragmentToConsumeEmotionFragment()

        findNavController().navigate(action)
    }
}