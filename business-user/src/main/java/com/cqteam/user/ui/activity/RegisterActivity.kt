package com.cqteam.user.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.cqteam.baselibrary.common.AppManager
import com.cqteam.baselibrary.ui.activity.BaseActivity
import com.cqteam.baselibrary.widgets.ToastUtils
import com.cqteam.user.R
import com.cqteam.user.databinding.ActivityRegisterBinding
import com.cqteam.user.vm.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterActivity : BaseActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val viewModel by viewModels<RegisterViewModel>()

    private var pressTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSendCode.setOnClickListener {
            Toast.makeText(this,"验证码：123456",Toast.LENGTH_SHORT).show()
        }

        binding.btnRegister.setOnClickListener {
            register()
        }

        viewModel.registerResult.observe(this, {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
    }

    private fun register() {
        val phone = binding.mPhoneEt.text.toString()
        val verifyCode = binding.mVerifyCodeEt.text.toString()
        val pwd = binding.mPwdEt.text.toString()
        val pwdSure = binding.mPwdSureEt.text.toString()

        if (phone.isEmpty()) {
            ToastUtils.show("请输入手机号码")
            return
        }

        if (verifyCode.isEmpty()) {
            ToastUtils.show("请输入验证码")
            return
        }

        if (pwd.isEmpty()) {
            ToastUtils.show("请输入密码")
            return
        }

        if (pwd.isEmpty() || pwdSure != pwd) {
            ToastUtils.show("两次密码输入不正确")
            return
        }

        viewModel.register(phone,pwd,verifyCode)
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
}