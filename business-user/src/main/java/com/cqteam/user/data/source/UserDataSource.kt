package com.cqteam.user.data.source

import com.cqteam.baselibrary.data.Result

/**
 * Author：      小小亮
 * CreateTime：  9/16/21 11:24 PM
 * Email：       281332545@qq.com
 * Introduce：：
 **/
interface UserDataSource {
    suspend fun register(mobile: String, pwd: String, verifyCode: String): Result<String>
}