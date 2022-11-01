package com.teampome.pome

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.annotation.ColorRes
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.teampome.pome.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
                    changeStatusBarColor(R.color.background)
                    binding.activityMainBnv.visibility = View.VISIBLE
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
                R.id.register_fragment -> {
                    binding.activityMainBnv.visibility = View.GONE
                    changeStatusBarColor(R.color.background)
                }
                else -> {
                    binding.activityMainBnv.visibility = View.GONE
                }
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
}