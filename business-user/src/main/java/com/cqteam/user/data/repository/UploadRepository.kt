package com.cqteam.user.data.repository

import com.cqteam.baselibrary.data.Result

/**
 * Author： 洪亮
 * Time： 2021/9/23 - 2:49 PM
 * Email：281332545@qq.com
 * <p>
 * 描述：
 */
interface UploadRepository {
    suspend fun getUploadToken(): Result<String>?
}