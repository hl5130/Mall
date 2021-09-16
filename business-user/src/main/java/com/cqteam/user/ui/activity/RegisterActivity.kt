package com.cqteam.user.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.cqteam.user.R
import com.cqteam.user.databinding.ActivityRegisterBinding
import com.cqteam.user.vm.RegisterViewModel

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val viewModel by viewModels<RegisterViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSendCode.setOnClickListener {
            Toast.makeText(this,"验证码：123456",Toast.LENGTH_SHORT).show()
        }

        binding.btnRegister.setOnClickListener {
            viewModel.register("","","")
        }

        viewModel.registerResult.observe(this, {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
    }
}