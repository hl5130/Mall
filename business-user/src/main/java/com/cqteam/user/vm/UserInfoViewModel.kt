package com.cqteam.user.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cqteam.baselibrary.data.Result
import com.cqteam.baselibrary.vm.BaseViewModel
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
class UserInfoViewModel @Inject constructor(
    private val userRepository: UserRepository
): BaseViewModel() {

    private val _resentPwdResult = MutableLiveData<String>()
    val resentPwdResult: LiveData<String> = _resentPwdResult

    fun resetPwd(mobile: String, pwd: String) {
        launchUI {
            showLoading.value = "loading"
            val result =userRepository.resetPwd(mobile, pwd)
            when(result) {
                is Result.Success-> {
                    hideLoading.value = "loading"
                    _resentPwdResult.value = result.data!!
                }
                is Result.Error-> {
                    hideLoading.value = "hide"
                    error.value = result.exception.message
                }
            }
        }
    }
}