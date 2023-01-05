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
import com.teampome.pome.util.base.BaseFragment

class ModifyRecordCardFragment : BaseFragment<FragmentModifyRecordCardBinding>(R.layout.fragment_modify_record_card) {

    private val args: ModifyRecordCardFragmentArgs by navArgs()

    private lateinit var goalBottomSheetDialog: BottomSheetDialog
    private lateinit var goalBottomSheetDialogBinding: PomeTextListBottomSheetDialogBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        makeGoalBottomSheetDialog()

        Log.d("test", "args : ${args.recordWeekItem}")
    }

    override fun initListener() {
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
        goalBottomSheetDialogBinding.textListRv.adapter = TextListAdapter()
        (goalBottomSheetDialogBinding.textListRv.adapter as TextListAdapter).submitList(
            args.categoryList.asList()
        )

        goalBottomSheetDialogBinding.executePendingBindings()
    }
}