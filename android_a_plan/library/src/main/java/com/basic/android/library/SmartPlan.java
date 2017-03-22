package com.basic.android.library;

import android.content.Context;
import android.content.Intent;

import com.basic.android.library.server.AndroidServerService;

/**
 * 开启抓取服务类.
 */
public final class SmartPlan {

    /**
     * 开启服务.
     *
     * @param context {@link Context}
     */
    public static void startPlan(Context context) {
        Intent intent = new Intent(context, AndroidServerService.class);
        context.startService(intent);
    }

    /**
     * 关闭服务.
     * 确保开启和关闭应该在同一个context上下文中进行.
     *
     * @param context {@link Context}
     */
    public static void stopPlan(Context context) {
        Intent intent = new Intent(context, AndroidServerService.class);
        context.stopService(intent);
    }
}
