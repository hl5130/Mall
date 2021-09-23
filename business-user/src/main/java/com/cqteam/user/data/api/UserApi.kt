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

    /*
        用户注册
     */
    @POST("userCenter/register")
    suspend fun register(@Body req: RegisterReq): BaseResp<String>

    /*
       用户登录
    */
    @POST("userCenter/login")
    suspend fun login(@Body req: LoginReq): BaseResp<UserInfo>

    /*
       忘记密码
    */
    @POST("userCenter/forgetPwd")
    suspend fun forgetPwd(@Body req: ForgetPwdReq): BaseResp<String>

    /*
       重置密码
    */
    @POST("userCenter/resetPwd")
    suspend fun resetPwd(@Body req: ResetPwdReq): BaseResp<String>

    /*
        编辑用户资料
     */
    @POST("userCenter/editUser")
    suspend fun editUser(@Body req:EditUserReq):BaseResp<UserInfo>

}