package com.kotlin.base.common

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import java.util.*
import kotlin.system.exitProcess

class AppManager private constructor() {
    private val activityStack: Stack<Activity> = Stack();

    companion object {
        val instance: AppManager by lazy {
            AppManager()
        }
    }

    /**
     * r入栈
     */
    fun addActivity(activity: Activity) {
        activityStack.add(activity);
    }

    /**
     * 出栈
     */
    fun finishActivity(activity: Activity) {
        activity.finish();
        activityStack.remove(activity);
    }

    /**
     * 获取当前栈
     */
    fun currentActivity(): Activity {
        //表示最后一个
        return activityStack.lastElement();
    }

    /**
     * 清理栈
     */
    fun finishAllActivity() {
        for (activity in activityStack) {
            activity.finish();
        }
        activityStack.clear();
    }
/**
 * 推出app
 */
    fun exitApp(context: Context) {
        finishAllActivity()
        var activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        activityManager.killBackgroundProcesses(context.packageName)
        exitProcess(0)
    }
}