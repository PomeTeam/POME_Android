package com.teampome.pome.presentation.record.add

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.teampome.pome.R
import com.teampome.pome.databinding.FragmentAddGoalCalendarBinding
import com.teampome.pome.databinding.PomeCalendarBottomSheetDialogBinding
import com.teampome.pome.util.common.CommonUtil
import com.teampome.pome.util.base.BaseFragment
import com.teampome.pome.viewmodel.record.AddGoalCalendarViewModel
import dagger.hilt.android.AndroidEntryPoint
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter

@AndroidEntryPoint
class AddGoalCalendarFragment: BaseFragment<FragmentAddGoalCalendarBinding>(R.layout.fragment_add_goal_calendar) {
    private lateinit var startCalendarBinding: PomeCalendarBottomSheetDialogBinding
    private lateinit var startCalendarDialog: BottomSheetDialog

    private lateinit var endCalendarBinding: PomeCalendarBottomSheetDialogBinding
    private lateinit var endCalendarDialog: BottomSheetDialog

    private val viewModel: AddGoalCalendarViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun initView() {
        makeStartCalenderView()
        makeEndCalendarView()

        // back button 클릭시 back button dialog show
        settingBackPressedCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                CommonUtil.showBackButtonDialog(
                    requireContext(),
                    "작성을 그만두시겠어요?",
                    "지금까지 작성한 내용은 모두 사라져요",
                    R.drawable.pen_mint,
                    "그만둘래요",
                    "이어서 쓸래요",
                    {
                        findNavController().popBackStack()
                    }
                ) {

                }
            }
        })
    }

    override fun initListener() {
        // 뒤로가기 버튼 클릭
        binding.addGoalBackButtonIv.setOnClickListener {
            findNavController().popBackStack()
        }

        // start 캘린더 클릭
        binding.addGoalStartCalendarIv.setOnClickListener {
            startCalendarDialog.show()
        }

        // end 캘린더 클릭
        binding.addGoalEndCalendarIv.setOnClickListener {
            endCalendarDialog.show()
        }

        // 선택했어요 버튼 클릭
        binding.addGoalCheckButtonAcb.setOnClickListener {
            moveToAddGoalContents()
        }

        viewModel.startDate.observe(viewLifecycleOwner) {
            binding.startDate = it

            if(CommonUtil.isDiffLowerThanOneMonth(viewModel.startDate.value, viewModel.endDate.value)) {
                binding.addGoalDateCheckTv.visibility = View.GONE
                binding.isLowerThanOneMonth = true
            } else {
                binding.addGoalDateCheckTv.visibility = View.VISIBLE
                binding.isLowerThanOneMonth = false
            }

            binding.executePendingBindings()
        }

        viewModel.endDate.observe(viewLifecycleOwner) {
            binding.endDate = it

            if(CommonUtil.isDiffLowerThanOneMonth(viewModel.startDate.value, viewModel.endDate.value)) {
                binding.addGoalDateCheckTv.visibility = View.GONE
                binding.isLowerThanOneMonth = true
            } else {
                binding.addGoalDateCheckTv.visibility = View.VISIBLE
                binding.isLowerThanOneMonth = false
            }

            binding.executePendingBindings()
        }
    }

    private fun makeStartCalenderView() {
        startCalendarDialog = BottomSheetDialog(requireContext()).apply {
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
        startCalendarBinding = PomeCalendarBottomSheetDialogBinding.inflate(layoutInflater, null, false)

        startCalendarDialog.setContentView(startCalendarBinding.root)

        CommonUtil.settingCalendarBottomSheetDialog(
            requireContext(),
            startCalendarBinding.calendarMcv,
            startCalendarBinding.calendarSelectAtb,
            { _, str ->
                viewModel.setStartDate(str)
                setMinDate(endCalendarBinding.calendarMcv, viewModel.startDate.value)
            }
        ) {
            startCalendarDialog.dismiss()
        }
    }

    private fun makeEndCalendarView() {
        endCalendarDialog = BottomSheetDialog(requireContext()).apply {
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
        endCalendarBinding = PomeCalendarBottomSheetDialogBinding.inflate(layoutInflater, null, false)

        endCalendarDialog.setContentView(endCalendarBinding.root)

        CommonUtil.settingCalendarBottomSheetDialog(
            requireContext(),
            endCalendarBinding.calendarMcv,
            endCalendarBinding.calendarSelectAtb,
            { _, str ->
                viewModel.setEndDate(str)
            }
        ) {
            endCalendarDialog.dismiss()
        }
    }

    /**
     *  date format pattern : yyyy.MM.dd
     */
    private fun setMinDate(calendar: MaterialCalendarView, date: String?) {
        date?.let {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")
                val minDate = LocalDate.parse(date, formatter)

                calendar.state().edit().setMinimumDate(minDate).commit()
            }
        }
    }

    private fun moveToAddGoalContents() {
        val action = AddGoalCalendarFragmentDirections.actionAddGoalCalendarFragmentToAddGoalContentsFragment(
            viewModel.startDate.value ?: "2023.01.01",
            viewModel.endDate.value ?: "2023.01.01"
        )

        findNavController().navigate(action)
    }
}