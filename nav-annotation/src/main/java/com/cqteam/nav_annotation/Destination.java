package com.cqteam.nav_annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Author： 洪亮
 * Time： 2021/9/2 - 2:54 PM
 * Email：281332545@qq.com
 * <p>
 * 描述：
 */

// 该注解只能用于指定类
@Target(ElementType.TYPE)
public @interface Destination {

    /**
     *  页面在路由中的名称
     * @return
     */
    String pageUrl();

    /**
     * 是否作为路由中的第一个启动项
     * @return
     */
    boolean asStarter() default false ;
}
