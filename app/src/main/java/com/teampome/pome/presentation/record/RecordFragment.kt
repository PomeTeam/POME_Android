package com.teampome.pome.presentation.record

import android.annotation.SuppressLint
import android.app.Dialog
import android.util.Log
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.RawRes
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.*
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.teampome.pome.R
import com.teampome.pome.util.base.BaseFragment
import com.teampome.pome.databinding.FragmentRecordBinding
import com.teampome.pome.databinding.PomeAlertDialogBinding
import com.teampome.pome.databinding.PomeRecordMoreGoalBottomSheetDialogBinding
import com.teampome.pome.databinding.PomeRegisterBottomSheetDialogBinding
import com.teampome.pome.databinding.PomeRemoveDialogBinding
import com.teampome.pome.databinding.TopImgNoticeDialogBinding
import com.teampome.pome.model.RecordData
import com.teampome.pome.model.goal.GoalCategory
import com.teampome.pome.model.goal.GoalData
import com.teampome.pome.presentation.record.recyclerview.*
import com.teampome.pome.presentation.record.recyclerview.adapter.RecordCategoryAdapter
import com.teampome.pome.presentation.record.recyclerview.adapter.RecordContentsCardAdapter
import com.teampome.pome.presentation.record.recyclerview.adapter.RecordViewType
import com.teampome.pome.presentation.remind.OnCategoryItemClickListener
import com.teampome.pome.util.common.CommonUtil
import com.teampome.pome.util.common.GoalState
import com.teampome.pome.util.common.OnItemClickListener
import com.teampome.pome.util.base.ApiResponse
import com.teampome.pome.util.base.CoroutineErrorHandler
import com.teampome.pome.viewmodel.record.RecordViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecordFragment : BaseFragment<FragmentRecordBinding>(R.layout.fragment_record) {

    private val viewModel: RecordViewModel by viewModels()

    // 목표 더보기 클릭 다이얼로그
    private lateinit var goalMoreBottomSheetDialogBinding: PomeRecordMoreGoalBottomSheetDialogBinding
    private lateinit var goalMoreBottomSheetDialog: BottomSheetDialog

    // 목표 삭제 클릭 다이얼로그
    private lateinit var removeGoalDialogBinding: PomeRemoveDialogBinding
    private lateinit var removeGoalDialog: Dialog

    // 기록 삭제 클릭 다이얼로그
    private lateinit var removeCardDialogBinding: PomeRemoveDialogBinding
    private lateinit var removeCardDialog: Dialog

    // 수정 삭제하기 다이얼로그
    private lateinit var recordDialogBinding: PomeRegisterBottomSheetDialogBinding
    private lateinit var recordDialog: BottomSheetDialog

    // 불가 다이얼로그
    private lateinit var noticeDialogBinding: TopImgNoticeDialogBinding
    private lateinit var noticeDialog: Dialog

    // 목표 완료 경고 다이얼로그
    private lateinit var finishGoalAlertDialogBinding: PomeAlertDialogBinding
    private lateinit var finishGoalAlertDialog: Dialog

    // 선택한 RecordData
    private lateinit var selectRecordData: RecordData

    override fun initView() {
        // 초기 목표 데이터 요청 (같이 요청을 하긴 하는데 showLoading은 언제까지?)
        // 초기 목표 데이터 요청 -> 일주일 감정 조회 요청 -> 기록 뷰 요청
        viewModel.findAllGoalByUser(object : CoroutineErrorHandler {
            override fun onError(message: String) {
                Log.e("error", "findAllGoalByUser error $message")
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            }
        })

        makeBottomSheetDialog()
        makeRecordDialog()
        makeGoalRemoveDialog()
        makeCardRemoveDialog()
        makeWarningDialog()
        makeFinishGoalAlertDialog()

        setInitAdapter()
    }

    override fun initListener() {
        setInitViewModelDataListener()

        binding.recordNoticeBellAiv.setOnClickListener {
            moveToRecordAlarms()
        }

        // 목표 + 클릭
        binding.recordCategoryPlusTv.setOnClickListener {
            viewModel.goalCategorys.value?.let {
                if(it.size > 10) {
                    alertWarningDialog(
                        R.drawable.number_10,
                        "목표는 10개를 넘을 수 없어요",
                        "포미는 사용자가 무리하지 않고 즐겁게 목표를 달성할 수 있도록 응원하고 있어요!"
                    )
                } else {
                    moveToAddGoal()
                }
            } ?: run {
                moveToAddGoal()
            }
        }

        // float button 클릭
        binding.recordWriteButtonCl.setOnClickListener {
            viewModel.goalDetails.value?.get(viewModel.curGoal.value?.pos ?: 0)?.let {
                moveToConsume()
            } ?: run {
                alertWarningDialog(
                    R.drawable.writing_warning_alert_3d_component,
                    "지금은 씀씀이를 기록할 수 없어요",
                    "나만의 소비 목표를 설정하고\n기록을 시작해보세요!"
                )
            }
        }
    }

    private fun setInitAdapter() {
        // 카테고리 어댑터 설정
        binding.recordCategoryChipsRv.adapter =
            RecordCategoryAdapter().apply {
                setOnItemClickListener(object : OnCategoryItemClickListener {
                    override fun onCategoryItemClick(item: GoalCategory, position: Int) {
                        viewModel.setCurrentGoal(item, position)
                    }
                })
            }

        // Record Contents 어댑터 설정
        binding.recordEmotionRv.adapter = RecordContentsCardAdapter().apply {
            // 기록 클릭
            setOnRecordBodyClickListener(object: OnRecordItemClickListener {
                override fun onRecordItemClick(item: RecordData) {
                    alertWarningDialog(
                        R.drawable.pen_pink,
                        "아직은 감정을 기록할 수 없어요",
                        "일주일이 지나야 감정을 남길 수 있어요\n나중에 다시 봐요!"
                    )
                }
            })
            // 기록 더보기 클릭
            setOnMoreRecordItemClickListener(object : OnMoreItemClickListener {
                override fun onMoreIconClick(item: RecordData) {
                    selectRecordData = item
                    recordDialog.show()
                }
            })
            // 감정 남기기 클릭
            setOnLeaveEmotionCardClickListener(object : OnItemClickListener {
                override fun itemClick() {
                    moveToRecordLeaveEmotion()
                }
            })
            // 목표 더보기 클릭
            setOnMoreGoalItemClickListener(object : OnItemClickListener {
                override fun itemClick() {
                    goalMoreBottomSheetDialog.show()
                }
            })
            // 목표 없을 때 클릭
            setOnNoGoalCardClickListener(object : OnItemClickListener {
                override fun itemClick() {
                    moveToAddGoal()
                }
            })
            // 목표 완료 시 클릭
            setOnGoalCompleteClickListener(object : OnItemClickListener {
                override fun itemClick() {
                    if((binding.recordEmotionRv.adapter as RecordContentsCardAdapter).itemCount-2 <= 0) {
                        moveToRecordGoalFinish()
                    } else {
                        finishGoalAlertDialog.show()
                    }
                }
            })

            addOnPagesUpdatedListener {
                if(binding.recordSize != itemCount - 2) {
                    binding.recordSize = itemCount - 2
                    binding.executePendingBindings()
                }
            }
        }
    }

    private fun setInitViewModelDataListener() {
        // 모든 목표 Response observe
        viewModel.findAllGoalByUserResponse.observe(viewLifecycleOwner) {
            when(it) {
                is ApiResponse.Success -> {

                    if(it.data.data?.content.isNullOrEmpty()) {
                        lifecycleScope.launch {
                            (binding.recordEmotionRv.adapter as RecordContentsCardAdapter).apply {
                                val item = makeRecordPagingData(
                                    null,
                                    RecordData(
                                        null, null, null, null, null, null, null, null,
                                        viewType = RecordViewType.Goal,
                                        oneWeekCount = null,
                                        null,
                                        null
                                    ),
                                    RecordData(
                                        null, null, null, null, null, null, null, null,
                                        viewType = RecordViewType.OneWeek,
                                        null,
                                        null,
                                        null
                                    )
                                )

                                submitData(item)
                            }
                        }
                    }

                    hideLoading()
                }
                is ApiResponse.Failure -> {
                    Toast.makeText(requireContext(), it.errorMessage, Toast.LENGTH_SHORT).show()

                    hideLoading()
                }
                is ApiResponse.Loading -> { showLoading() }
            }
        }

        viewModel.curGoal.observe(viewLifecycleOwner) { category ->
            // 현재 목표에 따라 일주일 감정 기록 확인
            viewModel.getOneWeekRecordByGoalId(category.goalId, object : CoroutineErrorHandler {
                override fun onError(message: String) {
                    Log.e("record", "record error $message")
                }
            })
        }

        // goal Details Observe 등록
        viewModel.goalDetails.observe(viewLifecycleOwner) {}

        // category listener - category를 주입
        viewModel.goalCategorys.observe(viewLifecycleOwner) {
            if(!it.isNullOrEmpty()) {
                // 초기에 category를 받으면 0번을 기반으로 데이터 초기화
                viewModel.setCurrentGoal(it[0]!!, 0)

                (binding.recordCategoryChipsRv.adapter as RecordCategoryAdapter).submitList(
                    it
                )
            }
        }

        // 목표에 따른 recordPagingData 처리
        viewModel.pagingRecordData.observe(viewLifecycleOwner) { pagingData ->
            lifecycleScope.launch {
                (binding.recordEmotionRv.adapter as RecordContentsCardAdapter).apply {
                    val item = makeRecordPagingData(
                        pagingData,
                        RecordData(
                            null, null, null, null, null, null, null, null,
                            viewType = RecordViewType.Goal,
                            null,
                            viewModel.goalDetails.value?.get(viewModel.curGoal.value?.pos ?: 0),
                            setGoalState(viewModel.goalDetails.value?.get(viewModel.curGoal.value?.pos ?: 0))
                        ),
                        RecordData(
                            null, null, null, null, null, null, null, null,
                            viewType = RecordViewType.OneWeek,
                            oneWeekCount = viewModel.oneWeekCount.value,
                            null,
                            null
                        )
                    )

                    submitData(item)
                }
            }
        }

        viewModel.getOneWeekRecordByGoalIdResponse.observe(viewLifecycleOwner) {
            when(it) {
                is ApiResponse.Success -> {

                    Log.d("test", "data is ${it.data.data}")
                    viewModel.setOneWeekCount(it.data.data?.content?.size ?: 0)

                    hideLoading()

                    // 기록 페이징 요청
                    viewModel.curGoal.value?.let { category ->
                        viewModel.getRecordPagingData(category.goalId)
                    }
                }
                is ApiResponse.Failure -> {
                    Toast.makeText(requireContext(), it.errorMessage, Toast.LENGTH_SHORT).show()
                    Log.d("recordData", "failure RecordData : $it")

                    hideLoading()
                }
                is ApiResponse.Loading -> { showLoading() }
            }
        }

        // UI용으로 짜여진 oneWeekRecords observe
        viewModel.oneWeekRecords.observe(viewLifecycleOwner) {
            Log.d("data", "data is $it")
        }

        // 삭제하기 response
        viewModel.deleteGoalResponse.observe(viewLifecycleOwner) {
            when(it) {
                is ApiResponse.Success -> {
                    Toast.makeText(requireContext(), "목표 삭제가 완료되었습니다.", Toast.LENGTH_SHORT).show()
                    hideLoading()
                    refresh()
                    removeGoalDialog.dismiss()
                }
                is ApiResponse.Failure -> {
                    Toast.makeText(requireContext(), it.errorMessage, Toast.LENGTH_SHORT).show()
                    hideLoading()
                    removeGoalDialog.dismiss()
                }
                is ApiResponse.Loading -> { showLoading() }
            }
        }

        viewModel.deleteRecordResponse.observe(viewLifecycleOwner) {
            when(it) {
                is ApiResponse.Success -> {
                    // refresh
                    refresh()
                }
                is ApiResponse.Failure -> {
                    Log.e("error", "deleteRecord failure by ${it.errorMessage}")
                }
                is ApiResponse.Loading -> { showLoading() }
            }
        }
    }

    // RecordData용 pagingData 만드는 메소드
    private fun makeRecordPagingData(
        pagingData: PagingData<RecordData>?,
        goalCardHeader: RecordData,
        oneWeekCardHeader: RecordData
    ): PagingData<RecordData> {
        val pageData = pagingData ?: PagingData.empty()

        val item = pageData.insertHeaderItem(
            terminalSeparatorType = TerminalSeparatorType.FULLY_COMPLETE,
            oneWeekCardHeader
        )

        return item.insertHeaderItem(
            terminalSeparatorType = TerminalSeparatorType.FULLY_COMPLETE,
            goalCardHeader
        )
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

        CommonUtil.makePomeDialog(removeGoalDialog)

        // 삭제하기 버튼 클릭
        removeGoalDialogBinding.removeYesTextAtv.setOnClickListener {
            viewModel.goalDetails.value?.let { list ->
                viewModel.curGoal.value?.let { goal ->
                    list[goal.pos]?.let { goalData ->
                        viewModel.deleteGoal(
                            goalData.id,
                            object : CoroutineErrorHandler {
                                override fun onError(message: String) {
                                    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                                }
                            }
                        )
                    } ?: run {
                        Toast.makeText(requireContext(), "목표 삭제 중 에러가 발생했습니다.", Toast.LENGTH_SHORT).show()
                        hideLoading()
                        removeGoalDialog.dismiss()
                    }
                } ?: run {
                    Toast.makeText(requireContext(), "목표 삭제 중 에러가 발생했습니다.", Toast.LENGTH_SHORT).show()
                    hideLoading()
                    removeGoalDialog.dismiss()
                }
            } ?: run {
                Toast.makeText(requireContext(), "목표 삭제 중 에러가 발생했습니다.", Toast.LENGTH_SHORT).show()
                hideLoading()
                removeGoalDialog.dismiss()
            }
        }

        // 아니요 버튼 클릭
        removeGoalDialogBinding.removeNoTextAtv.setOnClickListener {
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

            moveToModifyRecordCard(selectRecordData)
        }

        // 삭제 클릭
        recordDialogBinding.pomeBottomSheetDialogTrashTv.setOnClickListener {
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

        CommonUtil.makePomeDialog(removeCardDialog)

        // 삭제하기 버튼 클릭
        removeCardDialogBinding.removeYesTextAtv.setOnClickListener {
            // 삭제 api
            selectRecordData.id?.let { recordId ->
                viewModel.deleteRecordByRecordId(recordId, object : CoroutineErrorHandler {
                    override fun onError(message: String) {
                        Log.d("error", "deleteRecord error by $message")
                    }
                })
            }

            removeCardDialog.dismiss()
        }

        // 아니요 버튼 클릭
        removeCardDialogBinding.removeNoTextAtv.setOnClickListener {
            removeCardDialog.dismiss()
        }
    }

    // notice dialog 만들기
    private fun makeWarningDialog() {
        noticeDialogBinding = TopImgNoticeDialogBinding.inflate(layoutInflater, null, false)
        noticeDialog = Dialog(requireContext())

        noticeDialog.setContentView(noticeDialogBinding.root)

        CommonUtil.makePomeDialog(noticeDialog)

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
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun makeFinishGoalAlertDialog() {
        finishGoalAlertDialogBinding = PomeAlertDialogBinding.inflate(layoutInflater, null, false)

        finishGoalAlertDialog = Dialog(requireContext())
        finishGoalAlertDialog.setContentView(finishGoalAlertDialogBinding.root)

        CommonUtil.makePomeDialog(finishGoalAlertDialog)

        finishGoalAlertDialogBinding.alertCloseButtonIv.setOnClickListener {
            finishGoalAlertDialog.dismiss()
        }
    }

    private fun setGoalState(goalData: GoalData?) : GoalState {
        return goalData?.let {
            if(CommonUtil.calDiffDate(it.endDate) > 0) {
                GoalState.InProgress
            } else {
                GoalState.End
            }
        } ?: GoalState.Empty
    }

    // record view refresh 작업 => 뷰 데이터 처리 반영 시 사용
    private fun refresh() {
        val id = findNavController().currentDestination?.id
        findNavController().popBackStack(id!!, true)
        findNavController().navigate(id)
    }

    private fun moveToModifyRecordCard(recordData: RecordData) {
        viewModel.goalCategorys.value?.let { list ->
            viewModel.curGoal.value?.let { goal ->
                list[goal.pos]?.let { goalCategory ->
                    val action = RecordFragmentDirections.actionRecordFragmentToModifyRecordCardFragment(
                        recordData,
                        goalCategory.goalId,
                        goal.name
                    )

                    findNavController().navigate(action)
                }
            }
        }
    }

    private fun moveToRecordAlarms() {
        val action = RecordFragmentDirections.actionRecordFragmentToRecordAlarmsFragment()

        findNavController().navigate(action)
    }

    private fun moveToAddGoal() {
        val action = RecordFragmentDirections.actionRecordFragmentToAddGoalCalendarFragment()

        findNavController().navigate(action)
    }

    private fun moveToConsume() {
        viewModel.goalCategorys.value?.let { list ->
            viewModel.curGoal.value?.let { goal ->
                list[goal.pos]?.let { goalCategory ->
                    val changeList : List<GoalCategory> = list.map {
                        it?.let { gc ->
                            GoalCategory(
                                id = gc.goalId,
                                name = gc.name,
                                goalId = gc.goalId
                            )
                        } ?: GoalCategory(
                            id = -1,
                            name = "",
                            goalId = -1
                        )
                    }

                    val action = RecordFragmentDirections.actionRecordFragmentToConsumeRecordFragment(
                        goalCategory = goalCategory,
                        listGoal = changeList.toTypedArray()
                    )

                    findNavController().navigate(action)
                }
            }
        }
    }

    private fun moveToRecordLeaveEmotion() {
        viewModel.goalDetails.value?.let { list ->
            viewModel.curGoal.value?.let { goal ->
                list[goal.pos]?.let { goalData ->
                    val action = RecordFragmentDirections.actionRecordFragmentToRecordLeaveEmotionFragment(
                        goalData
                    )

                    findNavController().navigate(action)
                }
            }
        }
    }

    private fun moveToRecordGoalFinish() {
        viewModel.goalDetails.value?.let { list ->
            viewModel.curGoal.value?.let { goal ->
                list[goal.pos]?.let { goalData ->
                    val action = RecordFragmentDirections.actionRecordFragmentToRecordGoalFinishFragment(goalData)

                    findNavController().navigate(action)
                }
            }
        }
    }
}