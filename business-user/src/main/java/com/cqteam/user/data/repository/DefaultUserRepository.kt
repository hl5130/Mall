package com.cqteam.user.data.repository

import com.cqteam.baselibrary.data.Result
import com.cqteam.baselibrary.data.net.RetrofitFactory
import com.cqteam.user.data.api.UserApi
import com.cqteam.user.data.protocol.RegisterReq
import com.cqteam.user.data.source.UserRemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

/**
 * Author：      小小亮
 * CreateTime：  9/16/21 11:06 PM
 * Email：       281332545@qq.com
 * Introduce：：
 **/
class DefaultUserRepository: UserRepository {

    private val userRemoteDataSource = UserRemoteDataSource()

    override suspend fun register(mobile: String, pwd: String, verifyCode: String): Result<String> {
        return userRemoteDataSource.register(mobile, pwd, verifyCode)
    }
}