package com.teampome.pome.presentation.record

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.fragment.app.viewModels
import com.teampome.pome.R
import com.teampome.pome.util.base.BaseFragment
import com.teampome.pome.databinding.FragmentRecordBinding
import com.teampome.pome.model.RemindCategoryData
import com.teampome.pome.presentation.remind.OnCategoryItemClickListener
import com.teampome.pome.viewmodel.RecordViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecordFragment : BaseFragment<FragmentRecordBinding>(R.layout.fragment_record) {

    private val viewModel: RecordViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recordCategoryChipsRv.adapter =
            RecordCategoryAdapter().apply {
                setOnItemClickListener(object : OnCategoryItemClickListener {
                    override fun onCategoryItemClick(item: RemindCategoryData, position: Int) {

                    }
                })
            }

        binding.recordEmotionRv.adapter = RecordContentsCardAdapter()

        binding.recordAmountProgressAsb.apply {
            isEnabled = false

            progress = 48
            binding.recordAmountProgressTextTv.text = getString(R.string.record_progress_percent).format(progress)

            viewTreeObserver.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    val thumbBounds = thumb.bounds
                    val progressTextWidth = binding.recordAmountProgressTextTv.width

                    Log.d("progress", "thumbBounds center : ${thumbBounds.exactCenterX()}, progressTextWidth : $progressTextWidth")

                    // thumb의 중간 - (progressTv 길이 / 2) -1f => 1f는 살짝 왼쪽으로 조정하는 값
                    binding.recordAmountProgressTextTv.x = thumbBounds.exactCenterX() - (progressTextWidth.toFloat() / 2f) - 1f

                    viewTreeObserver.removeOnGlobalLayoutListener(this)
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

        (binding.recordEmotionRv.adapter as RecordContentsCardAdapter).submitList(
            listOf(
                "",
                "",
                "",
                "",
                "",
                "",
                ""
            )
        )
    }

    override fun initListener() {
        viewModel.recordTestData.observe(viewLifecycleOwner) {
            Log.d("test", "data is $it")
        }
    }
}