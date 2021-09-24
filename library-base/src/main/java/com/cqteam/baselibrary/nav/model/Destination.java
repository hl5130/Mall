package com.cqteam.baselibrary.nav.model;

/**
 * Author： 洪亮
 * Time： 2021/9/2 - 5:28 PM
 * Email：281332545@qq.com
 * <p>
 * 描述：
 */
public class Destination {

    // 页面 url
    public String pageUrl;

    // 路由节点(页面)的id
    public int id;

    // 是否作为路由的第一个启动页
    public boolean asStarter;

    // 路由节点(页面)的类型：activity,dialog,fragment
    public String destType;

    // 全类名
    public String clazName;
}
