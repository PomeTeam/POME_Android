package com.teampome.pome.presentation.record

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.prolificinteractive.materialcalendarview.*
import com.teampome.pome.R
import com.teampome.pome.databinding.FragmentModifyRecordCardBinding
import com.teampome.pome.databinding.PomeCalendarBottomSheetDialogBinding
import com.teampome.pome.util.CommonUtil
import com.teampome.pome.util.base.ApiResponse
import com.teampome.pome.util.base.BaseFragment
import com.teampome.pome.util.base.CoroutineErrorHandler
import com.teampome.pome.viewmodel.record.ModifyRecordCardViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Date

@AndroidEntryPoint
class ModifyRecordCardFragment : BaseFragment<FragmentModifyRecordCardBinding>(R.layout.fragment_modify_record_card) {
    private val viewModel: ModifyRecordCardViewModel by viewModels()

    private val args: ModifyRecordCardFragmentArgs by navArgs()

    // calendar bottom sheet
    private lateinit var calendarBottomSheetDialog: BottomSheetDialog
    private lateinit var calendarBottomSheetDialogBinding: PomeCalendarBottomSheetDialogBinding

    // 나중에 viewModel로 이동
    private var curDate: Date? = null

    @SuppressLint("SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun initView() {
        binding.modifyRecordGoalAet.setText(args.currentCategory)

        args.recordData.useDate?.let { useDate ->
            binding.modifyRecordDateAet.setText(useDate)
            viewModel.setDate(useDate)

            makeCalendarBottomSheetDialog(useDate)
        }

        args.recordData.usePrice?.let { usePrice ->
            binding.modifyRecordPriceAet.setText(usePrice.toString())
            viewModel.setPrice(usePrice.toString())
        }

        args.recordData.useComment?.let {useComment ->
            binding.modifyRecordContentAet.setText(useComment)
            viewModel.setUseComment(useComment)
        }


        binding.content = args.recordData.useComment
        binding.price = args.recordData.usePrice.toString()

        binding.executePendingBindings()
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
            Log.d("click", "click 수정")
            args.recordData.id?.let { recordId ->
                viewModel.updateRecord(
                    recordId,
                    args.goalId,
                    viewModel.useComment.value ?: "",
                    viewModel.date.value ?: "23.01.01",
                    (viewModel.price.value?.toLong() ?: "0") as Long,
                    object : CoroutineErrorHandler {
                        override fun onError(message: String) {
                            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                        }
                    }
                )
            }
        }

        // 금액 EditTextListener
        binding.modifyRecordPriceAet.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
            override fun afterTextChanged(s: Editable?) {
                viewModel.setPrice(s?.toString() ?: "")
            }
        })

        // 기록 EditTextListener
        binding.modifyRecordContentAet.addTextWatcher(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
            override fun afterTextChanged(s: Editable?) {
                viewModel.setUseComment(s?.toString() ?: "")
            }
        })

        viewModel.date.observe(viewLifecycleOwner) {
        }

        // price view data update
        viewModel.price.observe(viewLifecycleOwner) {
            binding.price = it
            binding.executePendingBindings()
        }

        // comment view data update
        viewModel.useComment.observe(viewLifecycleOwner) {
            binding.content = it
            binding.executePendingBindings()
        }

        viewModel.updateRecordResponse.observe(viewLifecycleOwner) {
            when(it) {
                is ApiResponse.Success -> {
                    // 수정 완료
                    Toast.makeText(requireContext(), "수정이 완료됐어요", Toast.LENGTH_SHORT).show()
                    hideLoading()

                    findNavController().popBackStack()
                }
                is ApiResponse.Failure -> {
                    Log.e("updateRecord", "update Record Error $it")
                    Toast.makeText(requireContext(), it.errorMessage, Toast.LENGTH_SHORT).show()
                    hideLoading()
                }
                is ApiResponse.Loading -> { showLoading() }
            }
        }
    }

    private fun makeCalendarBottomSheetDialog(useDate: String) {
        calendarBottomSheetDialog = BottomSheetDialog(requireContext()).apply {
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
        calendarBottomSheetDialogBinding = PomeCalendarBottomSheetDialogBinding.inflate(layoutInflater, null, false)

        calendarBottomSheetDialog.setContentView(calendarBottomSheetDialogBinding.root)

        CommonUtil.settingAlreadySelectedCalendarBottomSheetDialog(
            requireContext(),
            calendarBottomSheetDialogBinding.calendarMcv,
            calendarBottomSheetDialogBinding.calendarSelectAtb,
            CommonUtil.stringToLocalDate(useDate),
            { date, str ->
                curDate = date
                viewModel.setDate(str)
                binding.modifyRecordDateAet.setText(str)
            }
        ) {
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