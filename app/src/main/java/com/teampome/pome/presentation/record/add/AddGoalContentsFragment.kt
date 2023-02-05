package com.teampome.pome.presentation.record.add

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.teampome.pome.R
import com.teampome.pome.databinding.FragmentAddGoalContentsBinding
import com.teampome.pome.util.CommonUtil
import com.teampome.pome.util.base.BaseFragment
import com.teampome.pome.viewmodel.record.AddGoalContentsViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.DecimalFormat
import java.text.NumberFormat

@AndroidEntryPoint
class AddGoalContentsFragment : BaseFragment<FragmentAddGoalContentsBinding>(R.layout.fragment_add_goal_contents) {
    private val args: AddGoalContentsFragmentArgs by navArgs()

    private val viewModel: AddGoalContentsViewModel by viewModels()

    // Todo : viewModel 처리
    private lateinit var startDate: String
    private lateinit var endDate: String

    // price value
    private val df = DecimalFormat("#,###.##").apply {
        isDecimalSeparatorAlwaysShown = false
    }
    private val dfnd = DecimalFormat("#,###")
    private var hasFractionalPart: Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        startDate = args.startDate
        endDate = args.endDate
    }

    override fun initView() {
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
                p0?.let {
                    viewModel.setCategory(it.toString())
                }
            }
        })

        binding.addGoalContentsPromiseAet.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                p0?.let {
                    viewModel.setOneLineMind(it.toString())
                }
            }
        })

        // Price 형태 작업 진행
        binding.addGoalContentsAmountAet.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                p0?.let {
                    hasFractionalPart = it.toString().contains(df.decimalFormatSymbols.groupingSeparator.toString())
                }
            }
            override fun afterTextChanged(p0: Editable?) {
                p0?.let {
                    binding.addGoalContentsAmountAet.removeTextChangedListener(this)

                    val iniLen = binding.addGoalContentsAmountAet.text?.length ?: 0

                    val s = it.toString().replace(df.decimalFormatSymbols.groupingSeparator.toString(), "")
                    val n = df.parse(s)

                    val cp = binding.addGoalContentsAmountAet.selectionStart

                    if(hasFractionalPart) {
                        binding.addGoalContentsAmountAet.setText(df.format(n))
                    } else {
                        binding.addGoalContentsAmountAet.setText(dfnd.format(n))
                    }

                    val endLen = binding.addGoalContentsAmountAet.text?.length ?: 0
                    val sel = (cp + (endLen - iniLen))

                    if(sel > 0 && sel <= (binding.addGoalContentsAmountAet.text?.length ?: 1)) {
                        binding.addGoalContentsAmountAet.setSelection(sel)
                    } else {
                        binding.addGoalContentsAmountAet.setSelection(
                            (binding.addGoalContentsAmountAet.text?.length ?: 1) - 1
                        )
                    }

                    binding.addGoalContentsAmountAet.addTextChangedListener(this)

                    viewModel.setGoalPrice(it.toString().replace(",",""))
                }
            }
        })

        // switch listener
        binding.addGoalContentsPrivateSwitchSc.setOnCheckedChangeListener { _, b ->
            if(b) {
                viewModel.setPrivate(true)
            } else {
                viewModel.setPrivate(false)
            }
        }

        viewModel.goalCategory.observe(viewLifecycleOwner) {
            binding.category = it
            binding.executePendingBindings()
        }

        viewModel.oneLineMind.observe(viewLifecycleOwner) {
            binding.mind = it
            binding.executePendingBindings()
        }

        viewModel.goalPrice.observe(viewLifecycleOwner) {
            binding.price = it
            binding.executePendingBindings()
        }
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