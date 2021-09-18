package com.cqteam.user.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.cqteam.baselibrary.common.AppManager
import com.cqteam.baselibrary.ext.enable
import com.cqteam.baselibrary.ui.activity.BaseActivity
import com.cqteam.baselibrary.widgets.ToastUtils
import com.cqteam.user.R
import com.cqteam.user.databinding.ActivityRegisterBinding
import com.cqteam.user.vm.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterActivity : BaseActivity<RegisterViewModel>(), View.OnClickListener {

    override val viewModel: RegisterViewModel by viewModels()

    private lateinit var binding: ActivityRegisterBinding
    private var pressTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        initData()
    }

    private fun initData() {
        // 注册结果
        viewModel.registerResult.observe(this, {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
    }

    private fun initView() {

        // 监听四个输入框，判断注册按钮是否可以点击
        binding.mRegisterBtn.enable(binding.mMobileEt) { isBtnEnable() }
        binding.mRegisterBtn.enable(binding.mVerifyCodeEt) { isBtnEnable() }
        binding.mRegisterBtn.enable(binding.mPwdConfirmEt) { isBtnEnable() }
        binding.mRegisterBtn.enable(binding.mPwdEt) { isBtnEnable() }

        // 点击事件注册
        binding.mRegisterBtn.setOnClickListener(this)
        binding.mVerifyCodeBtn.setOnClickListener(this)
    }

    private fun register() {
        val pwd = binding.mPwdEt.text.toString()
        val pwdSure = binding.mPwdConfirmEt.text.toString()

        if (pwd == pwdSure) {
            viewModel.register(binding.mMobileEt.text.toString(),
                    pwd,
                    binding.mVerifyCodeEt.text.toString())
        } else {
            ToastUtils.show("两次密码不正确")
        }
    }

    override fun onBackPressed() {
        val time = System.currentTimeMillis()
        if (time - pressTime > 2000) {
            ToastUtils.show("再按一次退出程序")
            pressTime = time
        } else {
            AppManager.instance.exitApp(this)
        }
    }

    override fun onClick(v: View) {
        when(v.id) {
            R.id.mVerifyCodeBtn -> {
                binding.mVerifyCodeBtn.requestSendVerifyNumber()
                ToastUtils.show("验证码：123456")
            }
            R.id.mRegisterBtn -> {
                register()
            }
        }
    }

    /**
     *  按钮是否可用
     */
    private fun isBtnEnable(): Boolean {
        return binding.mMobileEt.text.isNullOrEmpty().not() &&
                binding.mPwdConfirmEt.text.isNullOrEmpty().not() &&
                binding.mPwdEt.text.isNullOrEmpty().not() &&
                binding.mVerifyCodeEt.text.isNullOrEmpty().not()
    }
}