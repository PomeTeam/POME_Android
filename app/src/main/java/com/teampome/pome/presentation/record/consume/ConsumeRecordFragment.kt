package com.teampome.pome.presentation.record.consume

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.teampome.pome.R
import com.teampome.pome.databinding.FragmentConsumeRecordBinding
import com.teampome.pome.databinding.PomeCalendarBottomSheetDialogBinding
import com.teampome.pome.databinding.PomeTextListBottomSheetDialogBinding
import com.teampome.pome.model.goal.GoalCategoryResponse
import com.teampome.pome.presentation.record.OnGoalCategoryClickListener
import com.teampome.pome.presentation.record.TextListAdapter
import com.teampome.pome.util.CommonUtil
import com.teampome.pome.util.CommonUtil.dateToLocalDate
import com.teampome.pome.util.CommonUtil.getCurrentDate
import com.teampome.pome.util.CommonUtil.getCurrentDateString
import com.teampome.pome.util.base.BaseFragment
import com.teampome.pome.viewmodel.record.ConsumeRecordViewModel
import com.teampome.pome.viewmodel.record.RecordViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.DecimalFormat
import java.util.Date

// Todo : 일단 navArgs를 통해 category 목록을 전달 -> activityViewModel로 관리하는게 편하지 않을까?
@AndroidEntryPoint
class ConsumeRecordFragment : BaseFragment<FragmentConsumeRecordBinding>(R.layout.fragment_consume_record) {
    private val viewModel: ConsumeRecordViewModel by viewModels()

    private val navArgs: ConsumeRecordFragmentArgs by navArgs()

    private lateinit var goalBottomSheetDialogBinding: PomeTextListBottomSheetDialogBinding
    private lateinit var goalBottomSheetDialog: BottomSheetDialog

    private lateinit var calendarBottomSheetDialogBinding: PomeCalendarBottomSheetDialogBinding
    private lateinit var calendarBottomSheetDialog: BottomSheetDialog

    private var selectedDate: Date? = null
    private var selectedDateStr: String = ""

    private lateinit var currentGoal: GoalCategoryResponse
    private lateinit var goalList: List<GoalCategoryResponse>

    // for price
    // price value
    private val df = DecimalFormat("#,###.##").apply {
        isDecimalSeparatorAlwaysShown = false
    }
    private val dfnd = DecimalFormat("#,###")
    private var hasFractionalPart: Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        currentGoal = navArgs.goalCategoryResponse
        goalList = navArgs.listGoal.toList()

        super.onViewCreated(view, savedInstanceState)
    }

    override fun initView() {
        makeGoalBottomSheetDialog()
        makeCalendarBottomSheetDialog()

        // 현재 날짜로 항상 시작
        binding.consumeRecordDateAet.setText(getCurrentDateString())

        // 목표도 이전 페이지의 목표로 고정
        binding.consumeRecordGoalAet.setText(currentGoal.name)

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

        // 소비 금액 작성
        binding.consumeRecordPriceAet.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                p0?.let {
                    hasFractionalPart = it.toString().contains(df.decimalFormatSymbols.groupingSeparator.toString())
                }
            }

            override fun afterTextChanged(p0: Editable?) {
                p0?.let {
                    binding.consumeRecordPriceAet.removeTextChangedListener(this)

                    val iniLen = binding.consumeRecordPriceAet.text?.length ?: 0

                    val s = it.toString().replace(df.decimalFormatSymbols.groupingSeparator.toString(), "")
                    val n = df.parse(s)

                    val cp = binding.consumeRecordPriceAet.selectionStart

                    if(hasFractionalPart) {
                        binding.consumeRecordPriceAet.setText(df.format(n))
                    } else {
                        binding.consumeRecordPriceAet.setText(dfnd.format(n))
                    }

                    val endLen = binding.consumeRecordPriceAet.text?.length ?: 0
                    val sel = (cp + (endLen - iniLen))

                    if(sel > 0 && sel <= (binding.consumeRecordPriceAet.text?.length ?: 1)) {
                        binding.consumeRecordPriceAet.setSelection(sel)
                    } else {
                        binding.consumeRecordPriceAet.setSelection(
                            (binding.consumeRecordPriceAet.text?.length ?: 1) - 1
                        )
                    }

                    binding.consumeRecordPriceAet.addTextChangedListener(this)

                    viewModel.setPrice(it.toString().replace(",", "").toInt())
                }
            }
        })

        // 소비 기록 작성
        binding.consumeRecordContentPet.addTextWatcher(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun afterTextChanged(p0: Editable?) {
                p0?.let {
                    viewModel.setRecord(it.toString())
                }
            }
        })

        viewModel.consumePrice.observe(viewLifecycleOwner) {
            binding.price = it
            binding.executePendingBindings()
        }

        viewModel.consumeRecord.observe(viewLifecycleOwner) {
            binding.record = it
            binding.executePendingBindings()
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
    }

    private fun makeGoalBottomSheetDialog() {
        goalBottomSheetDialog = BottomSheetDialog(requireContext())
        goalBottomSheetDialogBinding = PomeTextListBottomSheetDialogBinding.inflate(layoutInflater, null, false)

        goalBottomSheetDialog.setContentView(goalBottomSheetDialogBinding.root)

        goalBottomSheetDialogBinding.apply {
            title = "목표"
            textListRv.adapter = TextListAdapter().apply {
                // 목표 리스트 받아서 적용
                submitList(goalList.map {
                    it.name
                })

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