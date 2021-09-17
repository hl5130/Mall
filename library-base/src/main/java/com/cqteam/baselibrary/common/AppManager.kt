package com.cqteam.baselibrary.common

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import java.util.*
import kotlin.system.exitProcess

/**
 * Author： 洪亮
 * Time： 2021/9/17 - 6:39 PM
 * Email：281332545@qq.com
 * <p>
 * 描述： Activity 管理栈
 */
class AppManager private constructor(){

    private val activityStack: Stack<Activity> = Stack()

    companion object {
        val instance : AppManager by lazy { AppManager() }
    }

    /**
     *  入栈
     */
    fun addActivity(activity: Activity) {
        activityStack.add(activity)
    }

    /**
     *  出栈
     */
    fun finishActivity(activity: Activity) {
        activity.finish()
        activityStack.remove(activity)
    }

    /**
     *  获取当前栈顶
     */
    fun currentActivity(): Activity {
        return activityStack.lastElement()
    }

    /**
     *  清理栈
     */
    fun finishAllActivity(){
        for (activity in activityStack) {
            finishActivity(activity)
        }
        activityStack.clear()
    }

    /**
     * 退出应用
     */
    fun exitApp(context: Context) {
        finishAllActivity()
        val activityManger = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        activityManger.killBackgroundProcesses(context.packageName)
        exitProcess(0)
    }
}