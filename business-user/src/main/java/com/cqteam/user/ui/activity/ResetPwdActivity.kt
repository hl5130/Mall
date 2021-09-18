package com.cqteam.user.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.cqteam.baselibrary.common.AppManager
import com.cqteam.baselibrary.ext.enable
import com.cqteam.baselibrary.ui.activity.BaseActivity
import com.cqteam.baselibrary.widgets.ToastUtils
import com.cqteam.user.R
import com.cqteam.user.databinding.ActivityResetPwdBinding
import com.cqteam.user.vm.ResetPwdViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 *  重置密码页面
 */
@AndroidEntryPoint
class ResetPwdActivity : BaseActivity<ResetPwdViewModel>(), View.OnClickListener {

    override val viewModel: ResetPwdViewModel by viewModels()

    private lateinit var binding: ActivityResetPwdBinding
    private lateinit var mMobile: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResetPwdBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        initData()
    }

    private fun initData() {
        mMobile = intent.getStringExtra("mobile")?:""

        // 密码重置结果
        viewModel.resentPwdResult.observe(this, {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            val intent = Intent(this,LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        })
    }

    private fun initView() {

        // 监听四个输入框，判断注册按钮是否可以点击
        binding.mConfirmBtn.enable(binding.mPwdEt) { isBtnEnable() }
        binding.mConfirmBtn.enable(binding.mPwdConfirmEt) { isBtnEnable() }

        // 点击事件注册
        binding.mConfirmBtn.setOnClickListener(this)
    }

    private fun resentPwd() {
        val pwd = binding.mPwdEt.text.toString()
        if ( pwd == binding.mPwdConfirmEt.text.toString()) {
            viewModel.resetPwd(mMobile,pwd)
        } else {
            ToastUtils.show("两次密码不一致")
        }
    }

    override fun onClick(v: View) {
        when(v.id) {
            R.id.mConfirmBtn -> {
                resentPwd()
            }
        }
    }

    /**
     *  按钮是否可用
     */
    private fun isBtnEnable(): Boolean {
        return binding.mPwdEt.text.isNullOrEmpty().not() &&
                binding.mPwdConfirmEt.text.isNullOrEmpty().not()
    }
}