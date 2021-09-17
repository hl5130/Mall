package com.cqteam.user.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cqteam.baselibrary.data.Result
import com.cqteam.user.data.repository.DefaultUserRepository
import com.cqteam.user.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.launch
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
): ViewModel() {

    private val _registerResult = MutableLiveData<String>()
    val registerResult: LiveData<String> = _registerResult

    fun register(mobile: String, pwd: String, verifyCode: String) {
        viewModelScope.launch {
            val result =userRepository.register(mobile, pwd, verifyCode)
            when(result) {
                is Result.Success-> {
                    _registerResult.value = result.data!!
                }
                is Result.Error-> {
                    _registerResult.value = result.exception.message
                }
            }
        }
    }

}