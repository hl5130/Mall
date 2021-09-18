package com.cqteam.baselibrary.exception

/**
 * Author： 洪亮
 * Time： 2021/9/18 - 3:45 PM
 * Email：281332545@qq.com
 * <p>
 * 描述： 业务级错误
 */
class BusinessException(
        private val msg: String?
): Exception(msg)