package com.teampome.pome.presentation.record.add

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.teampome.pome.R
import com.teampome.pome.databinding.FragmentAddGoalCalendarBinding
import com.teampome.pome.databinding.PomeCalendarBottomSheetDialogBinding
import com.teampome.pome.util.CommonUtil
import com.teampome.pome.util.base.BaseFragment
import com.teampome.pome.viewmodel.record.AddGoalCalendarViewModel
import dagger.hilt.android.AndroidEntryPoint

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
            binding.executePendingBindings()
        }

        viewModel.endDate.observe(viewLifecycleOwner) {
            binding.endDate = it
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

    private fun moveToAddGoalContents() {
        val action = AddGoalCalendarFragmentDirections.actionAddGoalCalendarFragmentToAddGoalContentsFragment(
            viewModel.startDate.value ?: "2023.01.01",
            viewModel.endDate.value ?: "2023.01.01"
        )

        findNavController().navigate(action)
    }
}