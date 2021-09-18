package com.cqteam.user.vm

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.cqteam.baselibrary.data.Result
import com.cqteam.baselibrary.vm.BaseViewModel
import com.cqteam.user.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.internal.cache2.Relay.Companion.edit
import javax.inject.Inject

/**
 * Author：      小小亮
 * CreateTime：  9/16/21 11:28 PM
 * Email：       281332545@qq.com
 * Introduce：：
 **/

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val userRepository: UserRepository
): BaseViewModel() {

    private val _registerResult = MutableLiveData<String>()
    val registerResult: LiveData<String> = _registerResult

    fun register(mobile: String, pwd: String, verifyCode: String) {
        viewModelScope.launch {
            showLoading.value = "loading"
            val result =userRepository.register(mobile, pwd, verifyCode)
            when(result) {
                is Result.Success-> {
                    hideLoading.value = "loading"
                    _registerResult.value = result.data!!
                }
                is Result.Error-> {
                    hideLoading.value = "hide"
                    error.value = result.exception.message
                }
            }
        }
    }
}