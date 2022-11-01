package com.teampome.pome.presentation.register

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.teampome.pome.databinding.FragmentRegisterBinding
import com.teampome.pome.R
import com.teampome.pome.base.BaseFragment
import kotlinx.coroutines.*

class RegisterFragment : BaseFragment<FragmentRegisterBinding>(R.layout.fragment_register) {

    private lateinit var pomeBottomSheetView: View
    private lateinit var pomeBottomSheetDialog: BottomSheetDialog

    @SuppressLint("InflateParams")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pomeBottomSheetView = layoutInflater.inflate(R.layout.pome_bottom_sheet_dialog, null)
        pomeBottomSheetDialog = BottomSheetDialog(requireContext())
        pomeBottomSheetDialog.setContentView(pomeBottomSheetView)


    }

    override fun initListener() {
        binding.registerProfileAiv.setOnClickListener {
            pomeBottomSheetDialog.show()
        }

        // 닉네임 변경 글자 감지
        binding.registerProfileNameEt.addTextChangedListener(object: TextWatcher {
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

                    if(name.length <= 3) { // 일단 테스트용으로 텍스트가 3이하일 때, 불가능 처리
                        disableName()
                    } else {
                        enableName()
                    }
                } ?: kotlin.run { // 만약 값이 비어있다면, 밑에 desc를 표기하면 안됨
                    // GONE이 아니고 INVISIBLE 처리 => 공간을 차지해야 constraintLayout 알맞게 동작
                    binding.registerProfileNameCheckTv.visibility = View.INVISIBLE
                    disableName()
                }
            }
        })

        // 확인 버튼
        binding.registerProfileCheckBtn.setOnClickListener {
            Toast.makeText(requireContext(), "만들었어요.", Toast.LENGTH_SHORT).show()
        }

//        // 2초 뒤 register로 move
//        GlobalScope.launch(context = Dispatchers.Main) {
//            delay(3000)
//            moveToRecord()
//        }
    }

    /**
     *  name check desc text 사용 가능으로 변경
     *  name check desc text color main으로 변경
     *  button 활성화
     */
    private fun enableName() {
        binding.registerProfileNameCheckTv.text = resources.getText(R.string.pome_name_check_impossible_text)
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
        binding.registerProfileNameCheckTv.text = resources.getText(R.string.pome_name_check_impossible_text)
        binding.registerProfileNameCheckTv.setTextColor(resources.getColor(R.color.red, null))
        binding.registerProfileCheckBtn.setBackgroundResource(R.drawable.register_profile_name_check_disable_btn_background)
        binding.registerProfileCheckBtn.isClickable = false
    }

    private fun moveToRecord() {
        val registerToRecordAction = RegisterFragmentDirections.actionRegisterFragmentToRecordFragment()
        findNavController().navigate(registerToRecordAction)
    }
}