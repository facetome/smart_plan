package com.basic.android.library.server;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.basic.android.library.util.Utilty;
import com.yanzhenjie.andserver.AndServer;
import com.yanzhenjie.andserver.AndServerBuild;

/**
 * 用于提供接收客户端请求的服务.
 */
public class AndroidServerService extends Service {
    private AndServerBuild mServerBuild;
    private AndServer mAndServer;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("basic", "AndroidServerService onStartCommand...");
        if (mServerBuild == null) {
            mServerBuild = AndServerBuild.create();
            mServerBuild.add("database_table", new DataAndTableRequestHandler(getApplicationContext()));
            mServerBuild.add("database", new DatabaseQueryRequestHanlder(getApplicationContext()));
            mServerBuild.add("table", new DatabaseTableRequestHandler(getApplicationContext()));
            mServerBuild.add("data", new DatabaseDataRequestHandler(getApplicationContext()));
            mServerBuild.setTimeout(5000);
            mServerBuild.setPort(10000);
        }
        if (mAndServer == null) {
            mAndServer = mServerBuild.build();
        }
        if (!mAndServer.isRunning()) {
            mAndServer.launch();
        }
        Log.v("basic", "当前设备的ip地址：" + Utilty.getDeviceNetworkIp(this) + "; 端口号为: " + 10000);
        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mAndServer.close();
        Log.d("basic", "AndroidServerService onDestroy...");
    }
}
