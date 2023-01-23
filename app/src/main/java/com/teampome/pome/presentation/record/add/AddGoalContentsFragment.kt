package com.teampome.pome.presentation.record.add

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.fragment.findNavController
import com.teampome.pome.R
import com.teampome.pome.databinding.FragmentAddGoalContentsBinding
import com.teampome.pome.util.CommonUtil
import com.teampome.pome.util.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddGoalContentsFragment : BaseFragment<FragmentAddGoalContentsBinding>(R.layout.fragment_add_goal_contents) {
    // Todo : viewModel 처리
    private var category = ""
    private var promise = ""
    private var amount = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
                        moveToRecord()
                    }
                ) {

                }
            }
        })
    }

    override fun initListener() {
        // 자연스러운 키보드 처리를 위해
        binding.addGoalContentsContainerCl.setOnClickListener {
            CommonUtil.hideKeyboard(requireActivity())
        }

        binding.addGoalContentsBackButtonIv.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.addGoalContentsPrivateSwitchSc.setOnCheckedChangeListener { _, b ->
            // 스위치 기본 애니메이션 제거를 위해
            binding.addGoalContentsPrivateSwitchSc.jumpDrawablesToCurrentState()

            if(b) {
                binding.addGoalContentsPrivateContainerCl.background = ResourcesCompat.getDrawable(resources, R.drawable.item_grey1_r8_background, null)
            } else {
                binding.addGoalContentsPrivateContainerCl.background = ResourcesCompat.getDrawable(resources, R.drawable.item_pink10_r8_background, null)
            }
        }

        binding.addGoalContentsCheckButtonAcb.setOnClickListener {
            moveToAddGoalComplete()
        }

        // edittext listener
        binding.addGoalContentsCategoryAet.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
            override fun afterTextChanged(p0: Editable?) {
                category = p0?.toString() ?: ""

                if(isCheckButtonEnabled()) {
                    CommonUtil.enabledPomeBtn(binding.addGoalContentsCheckButtonAcb)
                } else {
                    CommonUtil.disabledPomeBtn(binding.addGoalContentsCheckButtonAcb)
                }
            }
        })

        binding.addGoalContentsPromiseAet.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                promise = p0?.toString() ?: ""

                if(isCheckButtonEnabled()) {
                    CommonUtil.enabledPomeBtn(binding.addGoalContentsCheckButtonAcb)
                } else {
                    CommonUtil.disabledPomeBtn(binding.addGoalContentsCheckButtonAcb)
                }
            }
        })

        binding.addGoalContentsAmountAet.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun afterTextChanged(p0: Editable?) {
                amount = p0?.toString() ?: ""

                if(isCheckButtonEnabled()) {
                    CommonUtil.enabledPomeBtn(binding.addGoalContentsCheckButtonAcb)
                } else {
                    CommonUtil.disabledPomeBtn(binding.addGoalContentsCheckButtonAcb)
                }
            }
        })
    }

    private fun isCheckButtonEnabled() : Boolean {
        return !(category.isEmpty() || promise.isEmpty() || amount.isEmpty())
    }

    private fun moveToAddGoalComplete() {
        val action = AddGoalContentsFragmentDirections.actionAddGoalContentsFragmentToAddGoalCompleteFragment()

        findNavController().navigate(action)
    }

    private fun moveToRecord() {
        val action = AddGoalContentsFragmentDirections.actionAddGoalContentsFragmentToRecordFragment()

        findNavController().navigate(action)
    }
}