package com.cqteam.user.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.cqteam.baselibrary.ext.enable
import com.cqteam.baselibrary.ui.activity.BaseActivity
import com.cqteam.baselibrary.widgets.ToastUtils
import com.cqteam.user.R
import com.cqteam.user.databinding.ActivityForgetPwdBinding
import com.cqteam.user.vm.ForgetPwdViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 *  忘记密码页面
 */
@AndroidEntryPoint
class ForgetPwdActivity : BaseActivity<ForgetPwdViewModel>(), View.OnClickListener {

    override val viewModel: ForgetPwdViewModel by viewModels()

    private lateinit var binding: ActivityForgetPwdBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgetPwdBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        initData()
    }

    private fun initData() {
        // 忘记密码结果
        viewModel.forgetPwdResult.observe(this, {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            val intent = Intent(this,ResetPwdActivity::class.java)
            intent.putExtra("mobile",binding.mMobileEt.text.toString())
            startActivity(intent)
        })
    }

    private fun initView() {

        // 监听四个输入框，判断注册按钮是否可以点击
        binding.mNextBtn.enable(binding.mMobileEt) { isBtnEnable() }
        binding.mNextBtn.enable(binding.mVerifyCodeEt) { isBtnEnable() }

        // 点击事件注册
        binding.mNextBtn.setOnClickListener(this)
        binding.mVerifyCodeBtn.setOnClickListener(this)
    }

    private fun forgetPwd() {
        viewModel.forgetPwd(binding.mMobileEt.text.toString(),binding.mVerifyCodeEt.text.toString())
    }

    override fun onClick(v: View) {
        when(v.id) {
            R.id.mVerifyCodeBtn -> {
                binding.mVerifyCodeBtn.requestSendVerifyNumber()
                ToastUtils.show("验证码：123456")
            }
            R.id.mNextBtn -> {
                forgetPwd()
            }
        }
    }

    /**
     *  按钮是否可用
     */
    private fun isBtnEnable(): Boolean {
        return binding.mMobileEt.text.isNullOrEmpty().not() &&
                binding.mVerifyCodeEt.text.isNullOrEmpty().not()
    }
}