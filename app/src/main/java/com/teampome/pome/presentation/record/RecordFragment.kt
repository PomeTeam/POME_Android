package com.teampome.pome.presentation.record

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.view.Window
import android.view.WindowManager.LayoutParams
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.RawRes
import androidx.core.view.marginStart
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.teampome.pome.R
import com.teampome.pome.util.base.BaseFragment
import com.teampome.pome.databinding.FragmentRecordBinding
import com.teampome.pome.databinding.PomeRecordMoreGoalBottomSheetDialogBinding
import com.teampome.pome.databinding.PomeRegisterBottomSheetDialogBinding
import com.teampome.pome.databinding.PomeRemoveDialogBinding
import com.teampome.pome.databinding.TopImgNoticeDialogBinding
import com.teampome.pome.model.RecordWeekItem
import com.teampome.pome.model.RemindCategoryData
import com.teampome.pome.presentation.remind.OnCategoryItemClickListener
import com.teampome.pome.util.CommonUtil
import com.teampome.pome.util.OnItemClickListener
import com.teampome.pome.viewmodel.RecordViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecordFragment : BaseFragment<FragmentRecordBinding>(R.layout.fragment_record) {

    private val viewModel: RecordViewModel by viewModels()

    // 목표 더보기 클릭 다이얼로그
    private lateinit var goalMoreBottomSheetDialogBinding: PomeRecordMoreGoalBottomSheetDialogBinding
    private lateinit var goalMoreBottomSheetDialog: BottomSheetDialog

    // 목표 삭제 클릭 다이얼로그
    private lateinit var removeGoalDialogBinding: PomeRemoveDialogBinding
    private lateinit var removeGoalDialog: Dialog

    // 목표 삭제 클릭 다이얼로그
    private lateinit var removeCardDialogBinding: PomeRemoveDialogBinding
    private lateinit var removeCardDialog: Dialog

    // Todo : 공통 다이얼로그로 변경
    private lateinit var recordDialogBinding: PomeRegisterBottomSheetDialogBinding
    private lateinit var recordDialog: BottomSheetDialog

    // 불가 다이얼로그
    private lateinit var noticeDialogBinding: TopImgNoticeDialogBinding
    private lateinit var noticeDialog: Dialog

    // Todo: send item 저장, data를 여기에 저장하는 것이 맞나? -> 임시 데이터면 생명주기와 연관 x?
    private lateinit var recordWeekItem: RecordWeekItem
    private lateinit var categoryList: List<String>
    private lateinit var currentCategory: String
    private var currentCategoryPosition: Int = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        makeBottomSheetDialog()
        makeRecordDialog()
        makeGoalRemoveDialog()
        makeCardRemoveDialog()
        makeWarningDialog()

        binding.recordCategoryChipsRv.adapter =
            RecordCategoryAdapter().apply {
                setOnItemClickListener(object : OnCategoryItemClickListener {
                    override fun onCategoryItemClick(item: RemindCategoryData, position: Int) {
                        binding.recordGoalItem = viewModel.recordTestData.value?.recordGoalData?.get(position)
                        binding.executePendingBindings()

                        currentCategory = item.category
                        currentCategoryPosition = position
                    }
                })
            }

        binding.recordEmotionRv.adapter = RecordContentsCardAdapter().apply {
            setOnBodyClickListener(object : OnItemClickListener {
                override fun itemClick() {
                    alertWarningDialog(
                        R.drawable.pen_pink,
                        "아직은 감정을 기록할 수 없어요",
                        "일주일이 지나야 감정을 남길 수 있어요\n나중에 다시 봐요!"
                    )
                }
            })
        }

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

        // 목표 + 클릭
        binding.recordCategoryPlusTv.setOnClickListener {
            moveToAddGoal()
        }

        // 목표 없을 때 목표 만들기 클릭
        binding.recordNoGoalSubtitleContainerCl.setOnClickListener {
            moveToAddGoal()
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

                    // categoryList 데이터 주입
                    categoryList = recordGoalItemList.map { recordGoalItem ->
                        recordGoalItem.category
                    }
                    currentCategory = recordGoalItemList[0].category

                    // 초기 값은 0번
                    binding.recordGoalItem = recordGoalItemList[0]
                }

                // content card data 주입
                (binding.recordEmotionRv.adapter as RecordContentsCardAdapter).submitList(
                    it.recordWeekData?.recordWeekItem
                )

                // content card more 버튼 클릭 리스너 등록
                (binding.recordEmotionRv.adapter as RecordContentsCardAdapter).setOnMoreItemClickListener(object : OnMoreItemClickListener {
                    override fun onMoreIconClick(item: RecordWeekItem) {
                        recordWeekItem = item

                        recordDialog.show()
                    }
                })

                binding.recordWeekData = it.recordWeekData
                binding.executePendingBindings()
            }
        }

        // floating button click
        binding.recordWriteButtonCl.setOnClickListener {
            viewModel.recordTestData.value?.let { recordTestData ->
                // 목표가 없는데 기록카드 쓰기 버튼을 누른 경우
                if(recordTestData.recordGoalData.isNullOrEmpty()) {
                    alertWarningDialog(
                        R.drawable.writing_warning_alert_3d_component,
                        "지금은 씀씀이를 기록할 수 없어요",
                        "나만의 소비 목표를 설정하고\n기록을 시작해보세요!"
                    )
                }
            }
        }

        // category plus button click
        binding.recordCategoryPlusTv.setOnClickListener {
            // Todo: 조건은 목표가 10개 이상일 때, Alert지만 임시로 클릭시 바로 alert발생
            alertWarningDialog(
                R.drawable.number_10,
                "목표는 10개를 넘을 수 없어요",
                "포미는 사용자가 무리하지 않고 즐겁게 목표를 달성할 수 있도록 응원하고 있어요!"
            )
        }

        binding.recordNoticeBellAiv.setOnClickListener {
            moveToRecordAlarms()
        }
    }

    // 목표 카드 더보기 클릭
    private fun makeBottomSheetDialog() {
        goalMoreBottomSheetDialog = BottomSheetDialog(requireContext())
        goalMoreBottomSheetDialogBinding = PomeRecordMoreGoalBottomSheetDialogBinding.inflate(layoutInflater, null, false)

        goalMoreBottomSheetDialog.setContentView(goalMoreBottomSheetDialogBinding.root)

        // bottom sheet 삭제하기 클릭
        goalMoreBottomSheetDialogBinding.goalMoreTrashTv.setOnClickListener {
            goalMoreBottomSheetDialog.dismiss()

            removeGoalDialog.show()
        }
    }

    // 목표 카드 삭제하기 클릭
    private fun makeGoalRemoveDialog() {
        removeGoalDialog = Dialog(requireContext())
        removeGoalDialogBinding = PomeRemoveDialogBinding.inflate(layoutInflater, null, false)
        
        removeGoalDialog.setContentView(removeGoalDialogBinding.root)

        removeGoalDialogBinding.removeDialogTitleAtv.text = "종료된 목표를 삭제하시겠어요?"
        removeGoalDialogBinding.removeDialogSubtitleAtv.text = "지금까지 작성한 기록들은 모두 사라져요"

        // round background 적용을 위해, root view의 코너를 가리기 위해 투명처리
        removeGoalDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        // 삭제하기 버튼 클릭
        removeGoalDialogBinding.removeYesTextAtv.setOnClickListener {
            Toast.makeText(requireContext(), "목표 삭제하기 Yes", Toast.LENGTH_SHORT).show()

            removeGoalDialog.dismiss()
        }

        // 아니요 버튼 클릭
        removeGoalDialogBinding.removeNoTextAtv.setOnClickListener {
            Toast.makeText(requireContext(), "목표 삭제하기 No", Toast.LENGTH_SHORT).show()

            removeGoalDialog.dismiss()
        }
    }

    // 카드 더보기 클릭
    private fun makeRecordDialog() {
        recordDialog = BottomSheetDialog(requireContext())
        recordDialogBinding = PomeRegisterBottomSheetDialogBinding.inflate(layoutInflater, null, false)

        recordDialog.setContentView(recordDialogBinding.root)

        recordDialogBinding.bottomSheetTitleTv.text = "기록 편집"

        // 수정하기 클릭
        recordDialogBinding.pomeBottomSheetDialogPencilTv.setOnClickListener {
            recordDialog.dismiss()

            moveToModifyRecordCard()
        }

        // 삭제 클릭
        recordDialogBinding.pomeBottomSheetDialogTrashTv.setOnClickListener {
            Toast.makeText(requireContext(), "기록 카드 삭제하기", Toast.LENGTH_SHORT).show()

            recordDialog.dismiss()

            removeCardDialog.show()
        }
    }

    // 카드 삭제하기 클릭
    private fun makeCardRemoveDialog() {
        removeCardDialog = Dialog(requireContext())
        removeCardDialogBinding = PomeRemoveDialogBinding.inflate(layoutInflater, null, false)

        removeCardDialog.setContentView(removeCardDialogBinding.root)

        removeCardDialogBinding.removeDialogTitleAtv.text = "기록을 삭제하시겠어요?"
        removeCardDialogBinding.removeDialogSubtitleAtv.text = "삭제한 내용은 다시 되돌릴 수 없어요"

        // round background 적용을 위해, root view의 코너를 가리기 위해 투명처리
        removeCardDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        // 삭제하기 버튼 클릭
        removeCardDialogBinding.removeYesTextAtv.setOnClickListener {
            Toast.makeText(requireContext(), "카드 삭제 Yes", Toast.LENGTH_SHORT).show()

            removeCardDialog.dismiss()
        }

        // 아니요 버튼 클릭
        removeCardDialogBinding.removeNoTextAtv.setOnClickListener {
            Toast.makeText(requireContext(), "카드 삭제 No", Toast.LENGTH_SHORT).show()

            removeCardDialog.dismiss()
        }
    }

    // notice dialog 만들기
    private fun makeWarningDialog() {
        noticeDialogBinding = TopImgNoticeDialogBinding.inflate(layoutInflater, null, false)
        noticeDialog = Dialog(requireContext())

        noticeDialog.setContentView(noticeDialogBinding.root)

        // close 버튼 정의
        noticeDialogBinding.noticeCloseButtonAiv.setOnClickListener {
            noticeDialog.dismiss()
        }
    }

    // Warning 다이얼로그 띄우기
    @SuppressLint("UseCompatLoadingForDrawables")
    private fun alertWarningDialog(
        @RawRes @DrawableRes drawable: Int,
        title: String,
        subTitle: String
    ) {
        noticeDialogBinding.apply {
            // top 이미지 변경
            Glide.with(noticeImageAiv)
                .load(drawable)
                .into(noticeImageAiv)

            // title 변경
            noticeTitleAtv.text = title

            // subTitle 변경
            noticeSubtitleAtv.text = subTitle
        }

        noticeDialog.show()

        // dialog의 window가 wrap_content이기 때문에 화면에 꽉 안차게 나옴
        noticeDialog.window?.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)

        val deviceSize = CommonUtil.getDeviceSize(requireContext())
        noticeDialog.window?.let {
            // dialog 둥글게 처리
            it.setBackgroundDrawable(resources.getDrawable(R.drawable.white_r8_background, null))

            val params = it.attributes
            params.width = (deviceSize[0] * 0.8).toInt()
            it.attributes = params as LayoutParams
        }
    }

    private fun moveToModifyRecordCard() {
        val action = RecordFragmentDirections.actionRecordFragmentToModifyRecordCardFragment(
            recordWeekItem,
            categoryList.toTypedArray(),
            currentCategory
        )

        findNavController().navigate(action)
    }

    private fun moveToRecordAlarms() {
        val action = RecordFragmentDirections.actionRecordFragmentToRecordAlarmsFragment()

        findNavController().navigate(action)
    }

    private fun moveToAddGoal() {
        val action = RecordFragmentDirections.actionRecordFragmentToAddGoalCalendarFragment()

        findNavController().navigate(action)
    }
}