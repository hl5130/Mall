package com.cqteam.user.data.repository

import com.cqteam.baselibrary.data.Result
import com.cqteam.user.data.protocol.UserInfo
import com.cqteam.user.data.source.UserDataSource

/**
 * Author：      小小亮
 * CreateTime：  9/16/21 11:06 PM
 * Email：       281332545@qq.com
 * Introduce：：
 **/
class DefaultUserRepository(
    private val userLocalDataSource: UserDataSource,
    private val userRemoteDataSource: UserDataSource
    ): UserRepository {

    override suspend fun register(mobile: String, pwd: String, verifyCode: String): Result<String> {
        return userRemoteDataSource.register(mobile, pwd, verifyCode)!!
    }

    override suspend fun login(mobile: String, pwd: String, pushId: String): Result<UserInfo> {
        return userRemoteDataSource.login(mobile, pwd, pushId)!!
    }

    override suspend fun forgetPwd(mobile: String, verifyCode: String): Result<String> {
        return userRemoteDataSource.forgetPwd(mobile, verifyCode)!!
    }

    override suspend fun resetPwd(mobile: String, pwd: String): Result<String> {
        return userRemoteDataSource.resetPwd(mobile, pwd)!!
    }
}