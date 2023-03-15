package com.teampome.pome

import android.app.Dialog
import android.content.IntentFilter
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.teampome.pome.databinding.ActivityMainBinding
import com.teampome.pome.databinding.PomeRemoveDialogBinding
import com.teampome.pome.util.common.CommonUtil
import com.teampome.pome.util.customview.PomeLoadingDialog
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    // loading 관련 dialog
    private lateinit var loadingDialog: PomeLoadingDialog

    // 목표 삭제 클릭 다이얼로그
    private lateinit var networkErrorDialogBinding: PomeRemoveDialogBinding
    private lateinit var networkErrorDialog: Dialog

    private val connectivityReceiver by lazy {
        PomeConnectivityReceiver()
    }

    private val connectivityReceiverIntentFilter by lazy {
        IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        registerReceiver(connectivityReceiver, connectivityReceiverIntentFilter)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // nav controller 찾고
        val navHost = supportFragmentManager.findFragmentById(R.id.activity_main_fcv) as NavHostFragment

        val navController = navHost.navController

        // nav controller와 Bottom Navigation View 연결하기
        binding.activityMainBnv.setupWithNavController(navController)

        // bottomNavigationView가 보이는 부분과 안보이는 부분 구분
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when(destination.id) {
                R.id.record_fragment -> {
                    binding.activityMainBnv.visibility = View.VISIBLE
                    changeStatusBarColor(R.color.white)
                }
                R.id.remind_fragment -> {
                    binding.activityMainBnv.visibility = View.VISIBLE
                }
                R.id.friend_fragment -> {
                    binding.activityMainBnv.visibility = View.VISIBLE
                }
                R.id.mypage_fragment -> {
                    binding.activityMainBnv.visibility = View.VISIBLE
                }
                R.id.splash_fragment -> {
                    binding.activityMainBnv.visibility = View.GONE
                    changeStatusBarColor(R.color.main)
                }
                R.id.splashLoginFragment -> {
                    binding.activityMainBnv.visibility = View.GONE
                    changeStatusBarColor(R.color.white)
                }
                R.id.register_profile_fragment -> {
                    binding.activityMainBnv.visibility = View.GONE
                }
                else -> {
                    binding.activityMainBnv.visibility = View.GONE
                }
            }
        }

        loadingDialog = PomeLoadingDialog(this)
        loadingDialog.window?.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
        loadingDialog.setCancelable(false)

        makeGoalRemoveDialog()

        PomeConnectivityReceiver.ConnectivityUtils.getLiveConnectivityState().observe(this) {
            if(!it.isConnected) {
                networkErrorDialog.show()
            }
        }
    }

    // status bar color 바꾸기
    private fun changeStatusBarColor(@ColorRes color: Int) {
        window?.apply {
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            statusBarColor = resources.getColor(color, null)
        }
    }

    fun showLoadingProgress() {
        loadingDialog.show()
    }

    fun hideLoadingProgress() {
        loadingDialog.dismiss()
    }

    override fun onDestroy() {
        super.onDestroy()

        unregisterReceiver(connectivityReceiver)
    }

    private fun makeGoalRemoveDialog() {
        networkErrorDialog = Dialog(this)
        networkErrorDialogBinding = PomeRemoveDialogBinding.inflate(layoutInflater, null, false)

        networkErrorDialog.setContentView(networkErrorDialogBinding.root)
        networkErrorDialog.setCancelable(false)

        networkErrorDialogBinding.removeDialogTitleAtv.text = "인터넷에 연결할 수 없어요"
        networkErrorDialogBinding.removeDialogSubtitleAtv.text = "다시 시도하거나 네트워크 설정을 확인해주세요"
        networkErrorDialogBinding.removeTrashAiv.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.ic_exclamation_24))

        CommonUtil.makePomeDialog(networkErrorDialog)

        // 삭제하기 버튼 클릭
        networkErrorDialogBinding.removeYesTextAtv.apply {
            text = "다시시도"

            setOnClickListener {
                networkErrorDialog.dismiss()
                recreate()
            }
        }
        // 아니요 버튼 클릭
        networkErrorDialogBinding.removeNoTextAtv.apply {
            text = "취소"

            setOnClickListener {
                networkErrorDialog.dismiss()
                finish()
            }
        }
    }
}