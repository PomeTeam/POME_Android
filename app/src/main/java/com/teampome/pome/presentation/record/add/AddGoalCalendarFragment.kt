package com.teampome.pome.presentation.record.add

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.teampome.pome.R
import com.teampome.pome.databinding.FragmentAddGoalCalendarBinding
import com.teampome.pome.databinding.PomeCalendarBottomSheetDialogBinding
import com.teampome.pome.util.CommonUtil
import com.teampome.pome.util.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddGoalCalendarFragment: BaseFragment<FragmentAddGoalCalendarBinding>(R.layout.fragment_add_goal_calendar) {
    private lateinit var startCalendarBinding: PomeCalendarBottomSheetDialogBinding
    private lateinit var startCalendarDialog: BottomSheetDialog

    private lateinit var endCalendarBinding: PomeCalendarBottomSheetDialogBinding
    private lateinit var endCalendarDialog: BottomSheetDialog

    private var startDate: String = ""
    private var endDate: String = ""

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
                        findNavController().navigateUp()
                    }
                ) {

                }
            }
        })
    }

    override fun initListener() {
        // 뒤로가기 버튼 클릭
        binding.addGoalBackButtonIv.setOnClickListener {
            findNavController().navigateUp()
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

        binding.addGoalStartDateAet.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                if(startDate.isNotEmpty() && endDate.isNotEmpty()) {
                    CommonUtil.enabledPomeBtn(binding.addGoalCheckButtonAcb)
                } else {
                    CommonUtil.disabledPomeBtn(binding.addGoalCheckButtonAcb)
                }
            }
        })

        binding.addGoalEndDateAet.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                if(startDate.isNotEmpty() && endDate.isNotEmpty()) {
                    CommonUtil.enabledPomeBtn(binding.addGoalCheckButtonAcb)
                } else {
                    CommonUtil.disabledPomeBtn(binding.addGoalCheckButtonAcb)
                }
            }
        })
    }

    private fun makeStartCalenderView() {
        startCalendarDialog = BottomSheetDialog(requireContext())
        startCalendarBinding = PomeCalendarBottomSheetDialogBinding.inflate(layoutInflater, null, false)

        startCalendarDialog.setContentView(startCalendarBinding.root)

        CommonUtil.settingCalendarBottomSheetDialog(
            requireContext(),
            startCalendarBinding.calendarMcv,
            startCalendarBinding.calendarSelectAtb,
            { _, str ->
                startDate = str
            }
        ) {
            binding.addGoalStartDateAet.setText(startDate)
            startCalendarDialog.dismiss()
        }
    }

    private fun makeEndCalendarView() {
        endCalendarDialog = BottomSheetDialog(requireContext())
        endCalendarBinding = PomeCalendarBottomSheetDialogBinding.inflate(layoutInflater, null, false)

        endCalendarDialog.setContentView(endCalendarBinding.root)

        CommonUtil.settingCalendarBottomSheetDialog(
            requireContext(),
            endCalendarBinding.calendarMcv,
            endCalendarBinding.calendarSelectAtb,
            { _, str ->
                endDate = str
            }
        ) {
            binding.addGoalEndDateAet.setText(endDate)
            endCalendarDialog.dismiss()
        }
    }

    private fun moveToAddGoalContents() {
        val action = AddGoalCalendarFragmentDirections.actionAddGoalCalendarFragmentToAddGoalContentsFragment()

        findNavController().navigate(action)
    }
}