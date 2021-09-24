package com.cqteam.baselibrary.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.cqteam.baselibrary.utils.NetWorkUtils
import com.cqteam.baselibrary.vm.BaseViewModel
import com.cqteam.baselibrary.widgets.ProgressLoading
import com.cqteam.baselibrary.widgets.ToastUtils

/**
 * Author： 洪亮
 * Time： 2021/9/24 - 2:29 PM
 * Email：281332545@qq.com
 * <p>
 * 描述：
 */
abstract class BaseFragment<out VM: BaseViewModel, out VB: ViewBinding>: Fragment() {
    protected abstract val viewModel: VM

    private var _binding: VB? = null
    protected val binding get() = _binding!!

    private lateinit var mLoadingDialog: ProgressLoading

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = setViewBinding(inflater,container,savedInstanceState)
        return binding.root
    }

    abstract fun setViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): VB

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mLoadingDialog = ProgressLoading.create(requireContext())
        initView(view,savedInstanceState)
        initData(view,savedInstanceState)
        // 开启加载动画
        viewModel.showLoading.observe(viewLifecycleOwner, {
            if (NetWorkUtils.isNetWorkAvailable(requireContext())){
                // 网络可用状态时，才弹出加载框
                mLoadingDialog.showLoading()
            }
        })

        // 隐藏加载动画
        viewModel.hideLoading.observe(viewLifecycleOwner, {
            mLoadingDialog.hideLoading()
        })

        // 弹出错误信息
        viewModel.error.observe(viewLifecycleOwner, {
            ToastUtils.show(it)
        })
    }

    abstract fun initView(view: View, savedInstanceState: Bundle?)
    abstract fun initData(view: View, savedInstanceState: Bundle?)

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}