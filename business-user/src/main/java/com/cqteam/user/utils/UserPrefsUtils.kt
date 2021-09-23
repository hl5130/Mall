package com.cqteam.user.utils

import com.cqteam.baselibrary.common.BaseConstant
import com.cqteam.baselibrary.utils.AppPrefsUtils
import com.cqteam.provider.common.ProviderConstant
import com.cqteam.user.data.protocol.UserInfo

/**
 * Author： 洪亮
 * Time： 2021/9/23 - 7:44 PM
 * Email：281332545@qq.com
 * <p>
 * 描述：
 */
object UserPrefsUtils {
    fun putUserInfo(userInfo: UserInfo?) {
        AppPrefsUtils.putString(BaseConstant.KEY_SP_TOKEN, userInfo?.id ?: "")
        AppPrefsUtils.putString(ProviderConstant.KEY_SP_USER_ICON, userInfo?.userIcon ?: "")
        AppPrefsUtils.putString(ProviderConstant.KEY_SP_USER_NAME, userInfo?.userName ?: "")
        AppPrefsUtils.putString(ProviderConstant.KEY_SP_USER_MOBILE, userInfo?.userMobile ?: "")
        AppPrefsUtils.putString(ProviderConstant.KEY_SP_USER_GENDER, userInfo?.userGender ?: "")
        AppPrefsUtils.putString(ProviderConstant.KEY_SP_USER_SIGN, userInfo?.userSign ?: "")
    }
}