package com.cqteam.baselibrary.glide;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;

/**
 * Author： 洪亮
 * Time： 2021/9/23 - 10:14 AM
 * Email：281332545@qq.com
 * <p>
 * 描述：
 */
class ImageLoaderUtils {
    public static boolean assertValidRequest(Context context) {
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            return !isDestroy(activity);
        } else if (context instanceof ContextWrapper){
            ContextWrapper contextWrapper = (ContextWrapper) context;
            if (contextWrapper.getBaseContext() instanceof Activity){
                Activity activity = (Activity) contextWrapper.getBaseContext();
                return !isDestroy(activity);
            }
        }
        return true;
    }

    private static boolean isDestroy(Activity activity) {
        if (activity == null) {
            return true;
        }
        return activity.isFinishing() || activity.isDestroyed();
    }
}
