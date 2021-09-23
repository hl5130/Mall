package com.cqteam.user.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cqteam.baselibrary.data.Result
import com.cqteam.baselibrary.utils.AppPrefsUtils
import com.cqteam.baselibrary.vm.BaseViewModel
import com.cqteam.user.data.protocol.UserInfo
import com.cqteam.user.data.repository.DefaultUploadRepository
import com.cqteam.user.data.repository.UploadRepository
import com.cqteam.user.data.repository.UserRepository
import com.cqteam.user.utils.UserPrefsUtils
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
    private val userRepository: UserRepository,
    private val uploadRepository: UploadRepository
): BaseViewModel() {

    // 获取七牛上传凭证
    private val _uploadTokenResult = MutableLiveData<String>()
    val uploadTokenResult: LiveData<String> = _uploadTokenResult

    // 用户修改信息
    private val _editUserInfoResult = MutableLiveData<UserInfo>()
    val editUserInfoResult: LiveData<UserInfo> = _editUserInfoResult

    fun getUploadToken() {
        launchUI {
            showLoading.value = "loading"
            val result = uploadRepository.getUploadToken()
            when (result) {
                is Result.Success -> {
                    hideLoading.value = "hide"
                    _uploadTokenResult.value = result.data!!
                }
                is Result.Error -> {
                    hideLoading.value = "hide"
                }
            }
        }
    }

    fun editUser(userIcon: String, userName: String, gender: String, sign: String) {
        launchUI {
            showLoading.value = "loading"
            val result = userRepository.editUser(userIcon, userName, gender, sign)
            when (result) {
                is Result.Success -> {
                    hideLoading.value = "hide"
                    UserPrefsUtils.putUserInfo(result.data!!)
                    _editUserInfoResult.value = result.data!!
                }
                is Result.Error -> {
                    hideLoading.value = "hide"
                }
            }
        }
    }
}