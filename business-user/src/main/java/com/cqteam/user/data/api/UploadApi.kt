package com.cqteam.user.data.api

import com.cqteam.baselibrary.data.protocol.BaseResp
import retrofit2.http.POST

/**
 * Author： 洪亮
 * Time： 2021/9/23 - 2:48 PM
 * Email：281332545@qq.com
 * <p>
 * 描述：
 */
interface UploadApi {

    /*
        获取七牛云上传凭证
     */
    @POST("common/getUploadToken")
    suspend fun getUploadToken(): BaseResp<String>
}