package com.cqteam.baselibrary.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Author： 洪亮
 * Time： 2021/9/17 - 8:18 PM
 * Email：281332545@qq.com
 * <p>
 * 描述：
 */
abstract class BaseViewModel: ViewModel() {

    val error = MutableLiveData<String>()

    val showLoading = MutableLiveData<String>()

    val hideLoading = MutableLiveData<String>()


    fun launchUI(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch {
            block()
        }
    }

}