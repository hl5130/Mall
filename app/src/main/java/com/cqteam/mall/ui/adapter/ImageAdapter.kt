package com.cqteam.mall.ui.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.cqteam.baselibrary.utils.GlideUtils
import com.youth.banner.adapter.BannerAdapter

/**
 * Author： 洪亮
 * Time： 2021/9/24 - 5:27 PM
 * Email：281332545@qq.com
 * <p>
 * 描述：
 */
class ImageAdapter(
    private val datas: List<String>
): BannerAdapter<String, ImageAdapter.BannerViewHolder>(datas) {

    private var context: Context? = null

    override fun onCreateHolder(parent: ViewGroup?, viewType: Int): BannerViewHolder {
        context = parent?.context
        val imageView = ImageView(parent?.context)
        //注意，必须设置为match_parent，这个是viewpager2强制要求的
        val lp = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)
        imageView.layoutParams = lp
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        return BannerViewHolder(imageView)
    }

    override fun onBindView(holder: BannerViewHolder?, data: String?, position: Int, size: Int) {
        context?.let {
            GlideUtils.loadUrlImage(it,data?:"",holder?.itemView as ImageView)
        }
    }

    class BannerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
}