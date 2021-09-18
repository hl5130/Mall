package com.cqteam.user.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cqteam.baselibrary.data.Result
import com.cqteam.baselibrary.vm.BaseViewModel
import com.cqteam.user.data.protocol.UserInfo
import com.cqteam.user.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Author：      小小亮
 * CreateTime：  9/16/21 11:28 PM
 * Email：       281332545@qq.com
 * Introduce：：
 **/

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository
): BaseViewModel() {

    private val _loginResult = MutableLiveData<UserInfo>()
    val loginResult: LiveData<UserInfo> = _loginResult

    fun login(mobile: String, pwd: String, pushId: String) {
        launchUI {
            showLoading.value = "loading"
            val result = userRepository.login(mobile, pwd, pushId)
            when(result) {
                is Result.Success-> {
                    hideLoading.value = "loading"
                    _loginResult.value = result.data!!
                }
                is Result.Error-> {
                    hideLoading.value = "hide"
                    error.value = result.exception.message
                }
            }
        }
    }
}