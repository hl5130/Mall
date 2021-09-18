package com.cqteam.user.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.cqteam.baselibrary.common.AppManager
import com.cqteam.baselibrary.ext.enable
import com.cqteam.baselibrary.ui.activity.BaseActivity
import com.cqteam.baselibrary.widgets.ToastUtils
import com.cqteam.user.R
import com.cqteam.user.databinding.ActivityLoginBinding
import com.cqteam.user.vm.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity<LoginViewModel>(), View.OnClickListener {

    override val viewModel: LoginViewModel by viewModels()

    private lateinit var binding: ActivityLoginBinding
    private var pressTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        initData()
    }

    private fun initView() {

        // 监听四个输入框，判断注册按钮是否可以点击
        binding.mLoginBtn.enable(binding.mMobileEt) { isBtnEnable() }
        binding.mLoginBtn.enable(binding.mPwdEt) { isBtnEnable() }

        // 点击事件注册
        binding.mLoginBtn.setOnClickListener(this)
        binding.mForgetPwdTv.setOnClickListener(this)
        binding.mHeaderBar.mRightTv.setOnClickListener(this)
    }

    private fun initData() {
        // 注册结果
        viewModel.loginResult.observe(this, {
            ToastUtils.show("登录成功")
        })
    }

    private fun login() {
        viewModel.login(
                binding.mMobileEt.text.toString(),
                binding.mPwdEt.text.toString(),
                ""
        )
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
            R.id.mLoginBtn -> {
                login()
            }
            R.id.mForgetPwdTv -> {
                startActivity(Intent(this,ForgetPwdActivity::class.java))
            }
            R.id.mRightTv -> {
                startActivity(Intent(this,RegisterActivity::class.java))
            }
        }
    }

    /**
     *  按钮是否可用
     */
    private fun isBtnEnable(): Boolean {
        return binding.mMobileEt.text.isNullOrEmpty().not() &&
                binding.mPwdEt.text.isNullOrEmpty().not()
    }
}