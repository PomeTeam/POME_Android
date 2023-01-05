package com.teampome.pome.presentation.record

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.teampome.pome.R
import com.teampome.pome.databinding.FragmentModifyRecordCardBinding
import com.teampome.pome.databinding.PomeTextListBottomSheetDialogBinding
import com.teampome.pome.util.CommonUtil
import com.teampome.pome.util.base.BaseFragment
import java.text.SimpleDateFormat
import java.util.Date

class ModifyRecordCardFragment : BaseFragment<FragmentModifyRecordCardBinding>(R.layout.fragment_modify_record_card) {

    private val args: ModifyRecordCardFragmentArgs by navArgs()

    private lateinit var goalBottomSheetDialog: BottomSheetDialog
    private lateinit var goalBottomSheetDialogBinding: PomeTextListBottomSheetDialogBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.modifyRecordGoalAet.setText(args.currentCategory)
        makeGoalBottomSheetDialog()

        // 일단 현재 시간으로...
        val now = System.currentTimeMillis()
        val date = Date(now)
        val sdf = SimpleDateFormat("yy.MM.dd")
        binding.modifyRecordDateAet.setText(sdf.format(date))

        Log.d("test", "args : ${args.recordWeekItem}")
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
}