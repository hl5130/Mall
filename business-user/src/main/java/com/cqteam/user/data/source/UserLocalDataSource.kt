package com.cqteam.user.data.source

import com.cqteam.baselibrary.data.Result

/**
 * Author： 洪亮
 * Time： 2021/9/17 - 5:26 PM
 * Email：281332545@qq.com
 * <p>
 * 描述：
 */
class UserLocalDataSource: UserDataSource {
    override suspend fun register(mobile: String, pwd: String, verifyCode: String): Result<String>? {
        return null
    }
}