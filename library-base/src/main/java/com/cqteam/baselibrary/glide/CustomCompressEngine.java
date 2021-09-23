package com.cqteam.baselibrary.glide;

import android.content.Context;

import com.luck.picture.lib.engine.CompressEngine;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnCallbackListener;

import java.util.List;

/**
 * Author： 洪亮
 * Time： 2021/9/23 - 3:59 PM
 * Email：281332545@qq.com
 * <p>
 * 描述： 自定义图片压缩框架
 */
@Deprecated()
public class CustomCompressEngine implements CompressEngine {
    @Override
    public void onCompress(Context context, List<LocalMedia> compressData, OnCallbackListener<List<LocalMedia>> listener) {
        listener.onCall(compressData);
    }
}
