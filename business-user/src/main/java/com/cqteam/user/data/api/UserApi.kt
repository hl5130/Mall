package com.cqteam.user.data.api

import com.cqteam.baselibrary.data.protocol.BaseResp
import com.cqteam.user.data.protocol.*
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Author：      小小亮
 * CreateTime：  9/16/21 10:58 PM
 * Email：       281332545@qq.com
 * Introduce：：
 **/
interface UserApi {

    @POST("userCenter/register")
    suspend fun register(@Body req: RegisterReq): BaseResp<String>

    @POST("userCenter/login")
    suspend fun login(@Body req: LoginReq): BaseResp<UserInfo>

    @POST("userCenter/forgetPwd")
    suspend fun forgetPwd(@Body req: ForgetPwdReq): BaseResp<String>

    @POST("userCenter/resetPwd")
    suspend fun resetPwd(@Body req: ResetPwdReq): BaseResp<String>

}