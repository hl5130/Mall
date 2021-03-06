package com.cqteam.user.data.source

import com.cqteam.baselibrary.common.ResultCode
import com.cqteam.baselibrary.data.Result
import com.cqteam.baselibrary.data.net.RetrofitFactory
import com.cqteam.baselibrary.exception.BusinessException
import com.cqteam.user.data.api.UserApi
import com.cqteam.user.data.protocol.*
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

    private val service = RetrofitFactory.instance.create(UserApi::class.java)

    override suspend fun register(mobile: String, pwd: String, verifyCode: String): Result<String> {
        return withContext(Dispatchers.IO) {
            try {
                val resp = service.register(RegisterReq(mobile, pwd, verifyCode))
                if (resp.status == ResultCode.SUCCESS) {
                    return@withContext Result.Success(resp.message?:"注册成功")
                }
                return@withContext Result.Error(BusinessException(resp.message))
            }catch (e: Exception) {
                e.printStackTrace()
                return@withContext Result.Error(e)
            }
        }
    }

    override suspend fun login(mobile: String, pwd: String, pushId: String): Result<UserInfo> {
        return withContext(Dispatchers.IO) {
            try {
                val resp = service.login(LoginReq(mobile, pwd, pushId))
                if (resp.status == ResultCode.SUCCESS) {
                    if (resp.data != null) {
                        return@withContext Result.Success(resp.data!!)
                    } else {
                        return@withContext Result.Error(BusinessException(resp.message))
                    }
                }
                return@withContext Result.Error(BusinessException(resp.message))
            }catch (e: Exception) {
                e.printStackTrace()
                return@withContext Result.Error(e)
            }
        }
    }

    override suspend fun forgetPwd(mobile: String, verifyCode: String): Result<String> {
        return withContext(Dispatchers.IO) {
            try {
                val resp = service.forgetPwd(ForgetPwdReq(mobile, verifyCode))
                if (resp.status == ResultCode.SUCCESS) {
                    return@withContext Result.Success(resp.message?:"")
                }
                return@withContext Result.Error(BusinessException(resp.message))
            }catch (e: Exception) {
                e.printStackTrace()
                return@withContext Result.Error(e)
            }
        }
    }

    override suspend fun resetPwd(mobile: String, pwd: String): Result<String> {
        return withContext(Dispatchers.IO) {
            try {
                val resp = service.resetPwd(ResetPwdReq(mobile, pwd))
                if (resp.status == ResultCode.SUCCESS) {
                    return@withContext Result.Success(resp.message?:"重置成功")
                }
                return@withContext Result.Error(BusinessException(resp.message))
            }catch (e: Exception) {
                e.printStackTrace()
                return@withContext Result.Error(e)
            }
        }
    }

    override suspend fun editUser(
        userIcon: String,
        userName: String,
        gender: String,
        sign: String
    ): Result<UserInfo> {
        return withContext(Dispatchers.IO) {
            try {
                val resp = service.editUser(EditUserReq(userIcon, userName, gender, sign))
                if (resp.status == ResultCode.SUCCESS) {
                    return@withContext Result.Success(resp.data!!)
                }
                return@withContext Result.Error(BusinessException(resp.message))
            }catch (e: Exception) {
                e.printStackTrace()
                return@withContext Result.Error(e)
            }
        }
    }
}