package com.cqteam.mall.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.cqteam.mall.R
import com.cqteam.mall.databinding.ActivityMainBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mBottomNavBar.checkMsgBadge(false)
        binding.mBottomNavBar.checkCartBadge(20)

        // 2秒后改变购物车数量，1秒后再次改变消息状态
        lifecycleScope.launch {
            delay(2000)
            binding.mBottomNavBar.checkCartBadge(0)

            launch {
                delay(1000)
                binding.mBottomNavBar.checkMsgBadge(true)
            }
        }
    }
}