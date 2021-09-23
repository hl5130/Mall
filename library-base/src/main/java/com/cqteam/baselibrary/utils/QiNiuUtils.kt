package com.cqteam.baselibrary.utils

import android.text.TextUtils
import android.util.Base64
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

/**
 * Author： 洪亮
 * Time： 2021/9/23 - 6:58 PM
 * Email：281332545@qq.com
 * <p>
 * 描述：
 */
class QiNiuUtils {

    companion object {
        val instance: QiNiuUtils by lazy { QiNiuUtils() }
    }

    private var secretKey: String = "i2uvVCDiUdZzKy3cI6QVgWHKHedgJ7GPU17dfu-v"
    private var accessKey: String = "OxJIx89R1pgyP7rkmc6U3mHxsXrPiRXxfpu5JP8j"

    /**
     *  添加时间戳
     */
    private fun addTime(url: String, currentTimeMillis: Long ): String {
        return "$url?e=$currentTimeMillis"
    }

    /**
     *  添加签名
     *  URL 字符串计算HMAC-SHA1签名，并对结果做URL 安全的 Base64 编码
     *  @param base 链接地址
     *  @param secretKey 七牛secretKey
     */
    private fun getSign(base: String, secretKey: String): String {
        if (TextUtils.isEmpty(base) || TextUtils.isEmpty(secretKey)) {
            return ""
        }
        val type = "HmacSHA1"
        val secret = SecretKeySpec(secretKey.toByteArray(), type);
        val mac = Mac.getInstance(type)
        mac.init(secret)
        val digest = mac.doFinal(base.toByteArray())
        return Base64.encodeToString(digest,Base64.DEFAULT)
    }


    private fun getToken(sign: String, accessKey: String): String {
        return "$accessKey:$sign"
    }

    fun downloadUrl(url: String): String {
        val currentTimeMillis = System.currentTimeMillis()
        val timeUrl = addTime(url,currentTimeMillis)
        val sign = getSign(timeUrl, secretKey)
        val token = getToken(sign, accessKey)
        return "$timeUrl&token=$token"
    }
}