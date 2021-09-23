package com.cqteam.user.data.repository

import com.cqteam.baselibrary.common.ResultCode
import com.cqteam.baselibrary.data.Result
import com.cqteam.baselibrary.data.net.RetrofitFactory
import com.cqteam.baselibrary.exception.BusinessException
import com.cqteam.user.data.api.UploadApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

/**
 * Author： 洪亮
 * Time： 2021/9/23 - 2:49 PM
 * Email：281332545@qq.com
 * <p>
 * 描述：
 */
class DefaultUploadRepository: UploadRepository {

    private val service = RetrofitFactory.instance.create(UploadApi::class.java)

    override suspend fun getUploadToken(): Result<String> {
        return withContext(Dispatchers.IO) {
            try {
                val result = service.getUploadToken()
                if (result.status == ResultCode.SUCCESS) {
                    if (!result.data.isNullOrEmpty())
                        return@withContext Result.Success(result.data!!)
                }
                return@withContext Result.Error(BusinessException(result.message?:"获取上传凭证失败"))
            }catch (e: Exception) {
                return@withContext Result.Error(e)
            }
        }
    }
}