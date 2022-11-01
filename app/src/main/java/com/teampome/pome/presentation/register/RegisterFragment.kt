package com.teampome.pome.presentation.register

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
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
//        // 2초 뒤 register로 move
//        GlobalScope.launch(context = Dispatchers.Main) {
//            delay(3000)
//            moveToRecord()
//        }
    }

    private fun moveToRecord() {
        val registerToRecordAction = RegisterFragmentDirections.actionRegisterFragmentToRecordFragment()
        findNavController().navigate(registerToRecordAction)
    }
}