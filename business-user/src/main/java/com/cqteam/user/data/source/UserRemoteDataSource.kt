package com.cqteam.user.data.source

import com.cqteam.baselibrary.common.ResultCode
import com.cqteam.baselibrary.data.Result
import com.cqteam.baselibrary.data.net.RetrofitFactory
import com.cqteam.user.data.api.UserApi
import com.cqteam.user.data.protocol.RegisterReq
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

/**
 * Author：      小小亮
 * CreateTime：  9/16/21 11:25 PM
 * Email：       281332545@qq.com
 * Introduce：：
 **/
class UserRemoteDataSource: UserDataSource {
    override suspend fun register(mobile: String, pwd: String, verifyCode: String): Result<String> {
        return withContext(Dispatchers.IO) {
            try {
                val resp = RetrofitFactory.instance.create(UserApi::class.java).register(RegisterReq(mobile, pwd, verifyCode))
                if (resp.status == ResultCode.SUCCESS) {
                    return@withContext Result.Success(resp.message?:"注册成功")
                }
                return@withContext Result.Error(Exception(resp.message))
            }catch (e: Exception) {
                e.printStackTrace()
                return@withContext Result.Error(e)
            }
        }
    }
}