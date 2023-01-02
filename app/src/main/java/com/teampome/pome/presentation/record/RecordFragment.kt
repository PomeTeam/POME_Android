package com.teampome.pome.presentation.record

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.teampome.pome.R
import com.teampome.pome.util.base.BaseFragment
import com.teampome.pome.databinding.FragmentRecordBinding
import com.teampome.pome.databinding.PomeRecordMoreGoalBottomSheetDialogBinding
import com.teampome.pome.databinding.PomeRemoveDialogBinding
import com.teampome.pome.model.RemindCategoryData
import com.teampome.pome.presentation.remind.OnCategoryItemClickListener
import com.teampome.pome.viewmodel.RecordViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecordFragment : BaseFragment<FragmentRecordBinding>(R.layout.fragment_record) {

    private val viewModel: RecordViewModel by viewModels()

    // 목표 더보기 클릭 다이얼로그
    private lateinit var goalMoreBottomSheetDialogBinding: PomeRecordMoreGoalBottomSheetDialogBinding
    private lateinit var goalMoreBottomSheetDialog: BottomSheetDialog

    // 목표 삭제 클릭 다이얼로그
    private lateinit var removeDialogBinding: PomeRemoveDialogBinding
    private lateinit var removeDialog: Dialog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        makeBottomSheetDialog()

        binding.recordCategoryChipsRv.adapter =
            RecordCategoryAdapter().apply {
                setOnItemClickListener(object : OnCategoryItemClickListener {
                    override fun onCategoryItemClick(item: RemindCategoryData, position: Int) {
                        binding.recordGoalItem = viewModel.recordTestData.value?.recordGoalData?.get(position)
                        binding.executePendingBindings()
                    }
                })
            }

        binding.recordEmotionRv.adapter = RecordContentsCardAdapter()

        binding.recordAmountProgressAsb.apply {
            isEnabled = false

            binding.recordAmountProgressTextTv.text = getString(R.string.record_progress_percent).format(progress)

            viewTreeObserver.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    val thumbBounds = thumb.bounds
                    val progressTextWidth = binding.recordAmountProgressTextTv.width

                    Log.d("progress", "thumbBounds center : ${thumbBounds.exactCenterX()}, progressTextWidth : $progressTextWidth")

                    // thumb의 중간 - (progressTv 길이 / 2) -1f => 1f는 살짝 왼쪽으로 조정하는 값
                    binding.recordAmountProgressTextTv.x = thumbBounds.exactCenterX() - (progressTextWidth.toFloat() / 2f) - 1f

//                    viewTreeObserver.removeOnGlobalLayoutListener(this)
                }
            })
        }

        // 목표의 더보기 클릭
        binding.recordGoalMoreAiv.setOnClickListener {
            goalMoreBottomSheetDialog.show()
        }
    }

    override fun initListener() {
        viewModel.recordTestData.observe(viewLifecycleOwner) { recordTestData ->
            recordTestData?.let {

                // category Adapter data 주입
                it.recordGoalData?.let { recordGoalItemList ->
                    (binding.recordCategoryChipsRv.adapter as RecordCategoryAdapter).submitList(
                        recordGoalItemList.map { recordGoalItem ->
                            RemindCategoryData(
                                recordGoalItem.category
                            )
                        }
                    )

                    // 초기 값은 0번
                    binding.recordGoalItem = recordGoalItemList[0]
                }

                // content card data 주입
                (binding.recordEmotionRv.adapter as RecordContentsCardAdapter).submitList(
                    it.recordWeekData?.recordWeekItem
                )

                binding.recordWeekData = it.recordWeekData
                binding.executePendingBindings()
            }
        }
    }

    private fun makeBottomSheetDialog() {
        goalMoreBottomSheetDialog = BottomSheetDialog(requireContext())
        goalMoreBottomSheetDialogBinding = PomeRecordMoreGoalBottomSheetDialogBinding.inflate(layoutInflater, null, false)

        goalMoreBottomSheetDialog.setContentView(goalMoreBottomSheetDialogBinding.root)

        // bottom sheet 삭제하기 클릭
        goalMoreBottomSheetDialogBinding.goalMoreTrashTv.setOnClickListener {
            Toast.makeText(requireContext(), "삭제하기 클릭", Toast.LENGTH_SHORT).show()

            makeRemoveDialog()

            removeDialog.show()
        }
    }

    private fun makeRemoveDialog() {
        removeDialog = Dialog(requireContext())
        removeDialogBinding = PomeRemoveDialogBinding.inflate(layoutInflater, null, false)
        
        removeDialog.setContentView(removeDialogBinding.root)

        // round background 적용을 위해, root view의 코너를 가리기 위해 투명처리
        removeDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        removeDialogBinding.removeDialogTitleAtv.text = "종료된 목표를 삭제하시겠어요?"
        removeDialogBinding.removeDialogSubtitleAtv.text = "지금까지 작성한 기록들은 모두 사라져요"
        
        removeDialogBinding.removeYesTextAtv.setOnClickListener {
            Toast.makeText(requireContext(), "Yes", Toast.LENGTH_SHORT).show()
        }
        
        removeDialogBinding.removeNoTextAtv.setOnClickListener {
            Toast.makeText(requireContext(), "No", Toast.LENGTH_SHORT).show()
        }
    }
}