package com.teampome.pome.presentation.record

import android.os.Bundle
import android.view.View
import com.teampome.pome.R
import com.teampome.pome.util.base.BaseFragment
import com.teampome.pome.databinding.FragmentRecordBinding
import com.teampome.pome.model.RemindCategoryData
import com.teampome.pome.presentation.remind.OnCategoryItemClickListener

class RecordFragment : BaseFragment<FragmentRecordBinding>(R.layout.fragment_record) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recordCategoryChipsRv.adapter =
            RecordCategoryAdapter().apply {
                setOnItemClickListener(object : OnCategoryItemClickListener {
                    override fun onCategoryItemClick(item: RemindCategoryData, position: Int) {

                    }
                })
            }

        (binding.recordCategoryChipsRv.adapter as RecordCategoryAdapter).submitList(
            listOf(
                RemindCategoryData(
                    category = "test"
                ),
                RemindCategoryData(
                    category = "test2"
                ),
                RemindCategoryData(
                    category = "test3"
                )
            )
        )
    }

    override fun initListener() {

    }
}