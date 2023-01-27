package com.teampome.pome.presentation.register

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions.bitmapTransform
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.teampome.pome.R
import com.teampome.pome.databinding.FragmentRegisterProfileBinding
import com.teampome.pome.databinding.PomeRegisterBottomSheetDialogBinding
import com.teampome.pome.util.base.BaseFragment
import com.teampome.pome.util.CommonUtil
import com.teampome.pome.util.base.ApiResponse
import com.teampome.pome.util.base.CoroutineErrorHandler
import com.teampome.pome.viewmodel.RegisterProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.glide.transformations.MaskTransformation
import kotlinx.coroutines.*

// Todo : ViewModel data 구분 짓기
@Suppress("DEPRECATION")
@AndroidEntryPoint
class RegisterProfileFragment : BaseFragment<FragmentRegisterProfileBinding>(R.layout.fragment_register_profile) {

    private val viewModel: RegisterProfileViewModel by viewModels()

    private lateinit var pomeBottomSheetDialog: BottomSheetDialog
    private lateinit var pomeBottomSheetDialogBinding: PomeRegisterBottomSheetDialogBinding

    @SuppressLint("InflateParams")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun initView() {
        // pomeBottomSheetDialog 뷰 인플레이션 과정
        pomeBottomSheetDialog = BottomSheetDialog(requireContext())
        pomeBottomSheetDialogBinding = PomeRegisterBottomSheetDialogBinding.inflate(layoutInflater, null, false)
        pomeBottomSheetDialog.setContentView(pomeBottomSheetDialogBinding.root)
    }

    // onTouchListener에 performClick을 정의하지 않아서 Lint skip 작업
    @SuppressLint("ClickableViewAccessibility", "UseCompatLoadingForDrawables")
    override fun initListener() {
        viewModel.presignedImageUrlResponse.observe(viewLifecycleOwner) {
            when(it) {
                is ApiResponse.Success -> {
                    Log.d("test", "success data : ${it.data}")
                }
                is ApiResponse.Failure -> {
                    Log.d("test", "failure data : ${it.errorMessage}")
                }
                is ApiResponse.Loading -> {
                }
            }
        }

        viewModel.nicknameResponse.observe(viewLifecycleOwner) {
            when(it) {
                is ApiResponse.Success -> {
                    Log.d("test", "data : ${it.data}")

                    binding.registerProfileNameCheckTv.text = it.data.message

                    if(it.data.data == true) {
                        enableName()
                    } else {
                        disableName()
                    }
                }
                is ApiResponse.Failure -> {
                    binding.registerProfileNameCheckTv.text = it.errorMessage
                    disableName()
                }
                is ApiResponse.Loading -> {

                }
            }
        }

        // 키보드 자연스럽게 처리
        binding.registerProfileCl.setOnTouchListener { _, _ ->
            CommonUtil.hideKeyboard(requireActivity())
            false
        }

        binding.registerProfileAiv.setOnClickListener {
            pomeBottomSheetDialog.show()
        }

        // 닉네임 변경 글자 감지
        binding.registerNameAet.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // 사용 x
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // 사용 x
            }
            override fun afterTextChanged(editable: Editable?) {
                // 텍스트가 완전히 변경될때, 닉네임 검증 필요
                // Todo : 서버 연결하여 닉네임 유효값 검증작업 진행
                editable?.let { name ->
                    binding.registerProfileNameCheckTv.visibility = View.VISIBLE

                    if(name.length < 6 || name.length > 18) { // 일단 테스트용으로 텍스트가 6이하일 때, 불가능 처리
                        binding.registerProfileNameCheckTv.text = "닉네임은 6~18글자의 영소문자, 숫자, 한글만 가능합니다."

                        disableName()
                    } else {
                        viewModel.userName.value = name.toString()

                        viewModel.checkNickname(object : CoroutineErrorHandler {
                            override fun onError(message: String) {
                                Toast.makeText(requireContext(), "error : $message", Toast.LENGTH_SHORT).show()
                            }
                        })
                    }
                } ?: kotlin.run { // 만약 값이 비어있다면, 밑에 desc를 표기하면 안됨
                    // GONE이 아니고 INVISIBLE 처리 => 공간을 차지해야 constraintLayout 알맞게 동작
                    binding.registerProfileNameCheckTv.visibility = View.INVISIBLE
                    binding.registerProfileNameCheckTv.text = "닉네임은 6~18글자의 영소문자, 숫자, 한글만 가능합니다."

                    disableName()
                }
            }
        })

        // 확인 버튼
        binding.registerProfileCheckBtn.setOnClickListener {
            Toast.makeText(requireContext(), "만들었어요.", Toast.LENGTH_SHORT).show()
            moveToAddFriends()
        }

        binding.registerProfileAiv.setOnClickListener {
            pomeBottomSheetDialog.show()
        }

        // dialog 수정 click
        pomeBottomSheetDialogBinding.pomeBottomSheetDialogPencilTv.setOnClickListener {
            Toast.makeText(requireContext(), "수정하기", Toast.LENGTH_SHORT).show()
            openGallery()
        }

        // dialog 삭제 click
        pomeBottomSheetDialogBinding.pomeBottomSheetDialogTrashTv.setOnClickListener {
            Toast.makeText(requireContext(), "삭제하기", Toast.LENGTH_SHORT).show()
            binding.registerProfileAiv.setImageDrawable(resources.getDrawable(R.drawable.user_profile_empty_160, null))
            binding.registerProfilePlusAiv.visibility = View.VISIBLE
            pomeBottomSheetDialog.dismiss()
        }

        // close Button 처리
        binding.registerProfileNameDelAiv.setOnClickListener {
            binding.registerNameAet.setText("")
        }
    }

    /**
     *  name check desc text 사용 가능으로 변경
     *  name check desc text color main으로 변경
     *  button 활성화
     */
    private fun enableName() {
        binding.registerProfileNameCheckTv.setTextColor(resources.getColor(R.color.main, null))
        binding.registerProfileCheckBtn.setBackgroundResource(R.drawable.register_profile_name_check_available_btn_background)
        binding.registerProfileCheckBtn.isClickable = true
    }

    /**
     *  name check desc text 사용 불가능으로 변경
     *  name check desc text color red로 변경
     *  button 비활성화
     */
    private fun disableName() {
        binding.registerProfileNameCheckTv.setTextColor(resources.getColor(R.color.red, null))
        binding.registerProfileCheckBtn.setBackgroundResource(R.drawable.register_profile_name_check_disable_btn_background)
        binding.registerProfileCheckBtn.isClickable = false
    }

    /**
     *  galleryLauncher를 이용하여 사용자 이미지를 가져오는 작업
     */
    private val galleryLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val imageUri = result.data?.data

                // 이미지 url 요청
                viewModel.getPresignedImageUrl(object : CoroutineErrorHandler {
                    override fun onError(message: String) {
                        Toast.makeText(requireContext(), "error : $message", Toast.LENGTH_SHORT).show()
                        Log.d("test", "error : $message")
                    }
                })

                try {
                    if(Build.VERSION.SDK_INT < 28) {
                        val bitmap = MediaStore.Images.Media.getBitmap(
                            activity?.contentResolver,
                            imageUri
                        )
                        binding.registerProfileAiv.setImageBitmap(bitmap)
                    } else {
                        imageUri?.let {
                            Glide.with(requireContext())
                                .load(it)
                                .apply(
                                    bitmapTransform(MultiTransformation(CenterCrop(),
                                    MaskTransformation(R.drawable.user_profile_empty_160)))
                                ).into(binding.registerProfileAiv)
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                // 다 끝나면 바텀시트 닫고 플러스 버튼 가리기
                binding.registerProfilePlusAiv.visibility = View.INVISIBLE
                pomeBottomSheetDialog.dismiss()
            }
        }

    private fun openGallery() {
        val intent = Intent()
        intent.apply {
            type = "image/*"
            action = Intent.ACTION_GET_CONTENT
        }
        galleryLauncher.launch(Intent.createChooser(intent, "Select Picture"))
    }

    private fun moveToAddFriends() {
        val registerProfileToAddFriendsAction = RegisterProfileFragmentDirections.actionRegisterProfileFragmentToAddFriendsNoticeFragment()
        findNavController().navigate(registerProfileToAddFriendsAction)
    }
}