package com.teampome.pome.util.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.teampome.pome.MainActivity

abstract class BaseFragment<T: ViewDataBinding>(@LayoutRes private val layoutId: Int): Fragment() {
    private var backPressedCallback: OnBackPressedCallback? = null
    protected lateinit var binding: T

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner

        initView()
        initListener()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        backPressedCallback?.let {
            requireActivity().onBackPressedDispatcher.addCallback(this, it)
        }
    }

    override fun onDetach() {
        super.onDetach()
        backPressedCallback?.remove()
    }

    // View 초기화 작업
    protected abstract fun initView()

    // Observing 작업 및 listener setting 작업
    protected abstract fun initListener()

    fun settingBackPressedCallback(callback: OnBackPressedCallback) {
        this.backPressedCallback = callback

        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    fun showLoading() {
        (requireActivity() as MainActivity).showLoadingProgress()
    }

    fun hideLoading() {
        (requireActivity() as MainActivity).hideLoadingProgress()
    }
}