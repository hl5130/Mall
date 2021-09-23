package com.cqteam.user.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cqteam.baselibrary.data.Result
import com.cqteam.baselibrary.vm.BaseViewModel
import com.cqteam.user.data.repository.DefaultUploadRepository
import com.cqteam.user.data.repository.UploadRepository
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
    private val userRepository: UserRepository,
    private val uploadRepository: UploadRepository
): BaseViewModel() {

    private val _uploadTokenResult = MutableLiveData<String>()
    val uploadTokenResult: LiveData<String> = _uploadTokenResult

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
}