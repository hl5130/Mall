package com.cqteam.mall.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cqteam.baselibrary.ui.fragment.BaseFragment
import com.cqteam.mall.common.*
import com.cqteam.mall.databinding.FragmentHomeBinding
import com.cqteam.mall.ui.adapter.ImageAdapter
import com.cqteam.mall.vm.HomeViewModel
import com.cqteam.nav_annotation.Destination
import com.youth.banner.indicator.CircleIndicator

/**
 * Author： 洪亮
 * Time： 2021/9/24 - 2:27 PM
 * Email：281332545@qq.com
 * <p>
 * 描述：
 */
@Destination(pageUrl = "main/tabs/home",asStarter = true)
class HomeFragment: BaseFragment<HomeViewModel, FragmentHomeBinding>(){
    override val viewModel: HomeViewModel get() = HomeViewModel()

    override fun setViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater,container,false)
    }

    override fun initView(view: View, savedInstanceState: Bundle?) {
        initBanner()
    }

    private fun initBanner() {
        val datas = arrayListOf<String>()
        datas.add(HOME_TOPIC_ONE)
        datas.add(HOME_TOPIC_TWO)
        datas.add(HOME_TOPIC_THREE)
        datas.add(HOME_TOPIC_FOUR)
        binding.mHomeBanner.setAdapter(ImageAdapter(datas))
            .addBannerLifecycleObserver(this)
            .indicator = CircleIndicator(requireContext())
    }

    override fun initData(view: View, savedInstanceState: Bundle?) {

    }

    override fun onStart() {
        super.onStart()
        binding.mHomeBanner.start()
    }

    override fun onStop() {
        super.onStop()
        binding.mHomeBanner.stop()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.mHomeBanner.destroy()
    }
}