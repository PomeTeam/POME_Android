package com.teampome.pome.presentation.record

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.RawRes
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
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
import com.teampome.pome.model.RecordWeekItem
import com.teampome.pome.model.goal.GoalCategory
import com.teampome.pome.model.goal.GoalCategoryResponse
import com.teampome.pome.model.goal.GoalData
import com.teampome.pome.presentation.remind.OnCategoryItemClickListener
import com.teampome.pome.util.CommonUtil
import com.teampome.pome.util.OnItemClickListener
import com.teampome.pome.util.base.ApiResponse
import com.teampome.pome.util.base.CoroutineErrorHandler
import com.teampome.pome.viewmodel.record.RecordViewModel
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.internal.notify

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

    // Todo : 공통 다이얼로그로 변경
    private lateinit var recordDialogBinding: PomeRegisterBottomSheetDialogBinding
    private lateinit var recordDialog: BottomSheetDialog

    // 불가 다이얼로그
    private lateinit var noticeDialogBinding: TopImgNoticeDialogBinding
    private lateinit var noticeDialog: Dialog

    // 목표 완료 경고 다이얼로그
    private lateinit var finishGoalAlertDialogBinding: PomeAlertDialogBinding
    private lateinit var finishGoalAlertDialog: Dialog

    // Todo: send item 저장, data를 여기에 저장하는 것이 맞나? -> 임시 데이터면 생명주기와 연관 x?
    private lateinit var recordData: RecordData
    private var currentCategory: String? = null
    private var currentCategoryPosition: Int? = null

    // 임시 클릭 리스너
    private val itemClickListener = object: OnRecordItemClickListener {
        override fun onRecordItemClick(item: RecordData) {
            alertWarningDialog(
                R.drawable.pen_pink,
                "아직은 감정을 기록할 수 없어요",
                "일주일이 지나야 감정을 남길 수 있어요\n나중에 다시 봐요!"
            )
        }
    }

    private val moreItemClickListener = object : OnMoreItemClickListener {
        override fun onMoreIconClick(item: RecordData) {
            recordData = item
            recordDialog.show()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun initView() {
        // default binding
//        settingDefaultBinding()

        // 초기 목표 데이터 요청 (같이 요청을 하긴 하는데 showLoading은 언제까지?)
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

        // 카테고리 어댑터 설정
        binding.recordCategoryChipsRv.adapter =
            RecordCategoryAdapter().apply {
                setOnItemClickListener(object : OnCategoryItemClickListener {
                    override fun onCategoryItemClick(item: GoalCategory, position: Int) {
                        currentCategory = item.name
                        currentCategoryPosition = position
                        viewModel.getRecordByGoalId(item.goalId, object : CoroutineErrorHandler {
                            override fun onError(message: String) {
                                Log.e("record", "record error $message")
                            }
                        })

                        viewModel.getOneWeekRecordByGoalId(item.goalId, object : CoroutineErrorHandler {
                            override fun onError(message: String) {
                                Log.e("record", "record error $message")
                            }
                        })

                        binding.goalDetails = viewModel.goalDetails.value?.get(position)
                        binding.currentGoalState = setGoalState(viewModel.goalDetails.value?.get(position))
                        binding.executePendingBindings()
                    }
                })
            }

        // Record Contents 어댑터 설정
        binding.recordEmotionRv.adapter = RecordContentsCardAdapter().apply {
            setOnBodyClickListener(itemClickListener)
            setOnMoreItemClickListener(moreItemClickListener)
        }
    }

    override fun initListener() {
        // 모든 목표 Response observe
        viewModel.findAllGoalByUserResponse.observe(viewLifecycleOwner) {
            when(it) {
                is ApiResponse.Success -> {
                    it.data.data?.content?.let { list ->
                        currentCategoryPosition?.let { pos ->
                            binding.goalDetails = list[pos]
                            binding.currentGoalState = setGoalState(list[pos])
                            binding.executePendingBindings()
                        }
                    } ?: run {
                        binding.goalDetails = null
                        binding.currentGoalState = GoalState.Empty
                        binding.executePendingBindings()
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

        // goal Details Observe 등록
        viewModel.goalDetails.observe(viewLifecycleOwner) {
        }

        // category listener - category를 주입
        viewModel.goalCategory.observe(viewLifecycleOwner) {
            if(!it.isNullOrEmpty()) {
                // 초기에 category를 받으면 0번을 기반으로 데이터 초기화
                currentCategory = it[0]!!.name
                currentCategoryPosition = 0

                // 카테고리 데이터 받은 후 목표 가져오는 작업 진행
                viewModel.getRecordByGoalId(it[0]!!.goalId, object : CoroutineErrorHandler {
                    override fun onError(message: String) {
                        Log.e("record", "record error $message")
                    }
                })

                viewModel.getOneWeekRecordByGoalId(it[0]!!.goalId, object : CoroutineErrorHandler {
                    override fun onError(message: String) {
                        Log.e("record", "record error $message")
                    }
                })

                (binding.recordCategoryChipsRv.adapter as RecordCategoryAdapter).submitList(
                    it
                )
            }
        }

        viewModel.getRecordByGoalIdResponse.observe(viewLifecycleOwner) { it ->
            when(it) {
                is ApiResponse.Success -> {
                    it.data.data?.let { contents ->
                        binding.recordData = contents.content
                    }

                    binding.executePendingBindings()

//                    submit list하는 list는 다른 값인데, 이전 값이 currentList임...
//                    submitList의 list는 정상, 계속 카테고리를 변경하다보면 list값이 아예 섞임..
//                    debuging시에는 정상? 그러면 충분한 업데이트 시간이 없어서?
//                    1 - 2 - 1 - 3 정상, 3 - 2 - 3 비정상 => null을 받을 때, submitList가 이상하게 동작?
//                    notifyDataSetChanged, invalidateAll, executePendingBinding 다 동작 x
//                    currentList는 정상..
                    // 임시 처리 : 데이터 변경 시 새로운 어댑터 객체 생성
                    binding.recordEmotionRv.adapter = RecordContentsCardAdapter().apply {
                        setOnBodyClickListener(itemClickListener)
                        setOnMoreItemClickListener(moreItemClickListener)
                    }

                    (binding.recordEmotionRv.adapter as RecordContentsCardAdapter).submitList(
                        it.data.data?.content?.toMutableList() ?: mutableListOf()
                    )

                    hideLoading()
                }
                is ApiResponse.Failure -> {
                    Toast.makeText(requireContext(), it.errorMessage, Toast.LENGTH_SHORT).show()
                    Log.d("recordData", "failure RecordData : $it")

                    hideLoading()
                }
                is ApiResponse.Loading -> { showLoading() }
            }
        }

        viewModel.getOneWeekRecordByGoalIdResponse.observe(viewLifecycleOwner) {
            when(it) {
                is ApiResponse.Success -> {
                    binding.countOneWeekRecord = it.data.data?.content?.size ?: 0
                    binding.executePendingBindings()

                    hideLoading()
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

        binding.recordNoticeBellAiv.setOnClickListener {
            moveToRecordAlarms()
        }

        // 목표의 더보기 클릭
        binding.recordGoalMoreAiv.setOnClickListener {
            goalMoreBottomSheetDialog.show()
        }

        // 목표 + 클릭
        binding.recordCategoryPlusTv.setOnClickListener {
            viewModel.goalCategory.value?.let {
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

        // 목표 없을 때 목표 만들기 클릭
        binding.recordNoGoalSubtitleContainerCl.setOnClickListener {
            moveToAddGoal()
        }

        // float button 클릭
        binding.recordWriteButtonCl.setOnClickListener {
            if(binding.currentGoalState is GoalState.Empty) {
                alertWarningDialog(
                    R.drawable.writing_warning_alert_3d_component,
                    "지금은 씀씀이를 기록할 수 없어요",
                    "나만의 소비 목표를 설정하고\n기록을 시작해보세요!"
                )
            } else {
                moveToConsume()
            }
        }

        binding.recordWriteEmotionContainerCl.setOnClickListener {
            moveToRecordLeaveEmotion(

            )
        }

        binding.recordGoalCompleteCl.setOnClickListener {
            moveToRecordGoalFinish()
        }
    }

    // 첫 기본 바인딩 설정
    private fun settingDefaultBinding() {
        binding.goalDetails = null
        binding.currentGoalState = GoalState.Empty
        binding.executePendingBindings()
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
                currentCategoryPosition?.let { pos ->
                    list[pos]?.let { goalData ->
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

            moveToModifyRecordCard(recordData)
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

        CommonUtil.makePomeDialog(removeCardDialog)

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
        viewModel.goalCategory.value?.let { list ->
            currentCategoryPosition?.let { pos ->
                currentCategory?.let { category ->
                    list[pos]?.let { goalCategory ->
                        val action = RecordFragmentDirections.actionRecordFragmentToModifyRecordCardFragment(
                            recordData,
                            goalCategory.goalId,
                            category
                        )

                        findNavController().navigate(action)
                    }
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
        viewModel.goalCategory.value?.let { list ->
            currentCategoryPosition?.let { pos ->
                list[pos]?.let { goalCategory ->
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
            currentCategoryPosition?.let { pos ->
                list[pos]?.let { goalData ->
                    val action = RecordFragmentDirections.actionRecordFragmentToRecordLeaveEmotionFragment(
                        goalData
                    )

                    findNavController().navigate(action)
                }
            }
        }
    }

    private fun moveToRecordGoalFinish() {
        val action = RecordFragmentDirections.actionRecordFragmentToRecordGoalFinishFragment()

        findNavController().navigate(action)
    }
}