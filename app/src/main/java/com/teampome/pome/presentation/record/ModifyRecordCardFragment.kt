package com.teampome.pome.presentation.record

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.fragment.navArgs
import com.teampome.pome.R
import com.teampome.pome.databinding.FragmentModifyRecordCardBinding
import com.teampome.pome.util.base.BaseFragment

class ModifyRecordCardFragment : BaseFragment<FragmentModifyRecordCardBinding>(R.layout.fragment_modify_record_card) {

    private val args: ModifyRecordCardFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("test", "args : ${args.recordWeekItem}")
    }

    override fun initListener() {

    }
}