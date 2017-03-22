package com.basic.android.library.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * Created by basic on 2017/3/14.
 */
public final class Utilty {
   private static final String TAG = Utilty.class.getSimpleName();
    private static final int IP_SEGMENT_MASK = 0xFF;
    private static final int EIGHT = 8;
    private static final int SXITEEN = 16;
    private static final int TWENTY_FOUR = 24;
    private static final String DEFAULT_NETWORK_IP = "127.0.0.1";

    /**
     * 获取手机ip地址，如果WiFi可以，则返回WiFi的ip，否则就返回网络ip.
     *
     * @param context 上下文
     * @return ip
     */
    public static String getDeviceNetworkIp(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context
                .CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null) {
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {  //wifi
                WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifi.getConnectionInfo();
                int ip = wifiInfo.getIpAddress();
                return convertToIp(ip);
            } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) { // gprs
                try {
                    for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
                         en.hasMoreElements();) {
                        NetworkInterface intf = en.nextElement();
                        for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses();
                             enumIpAddr.hasMoreElements();) {
                            InetAddress inetAddress = enumIpAddr.nextElement();
                            //这里4.0以上部分手机返回的地址将会是ipv6地址，而且在4G下也验证了返回的是
                            //ipv6，而服务器不处理ipv6地址
                            if (!inetAddress.isLoopbackAddress() && !(inetAddress
                                    instanceof Inet6Address)) {
                                return inetAddress.getHostAddress().toString();
                            }

                        }

                    }
                } catch (SocketException e) {
                    Log.e(TAG, "e", e);
                }
            }
        }
        return DEFAULT_NETWORK_IP;
    }


    private static String convertToIp(int ip) {
        return (ip & IP_SEGMENT_MASK)
                + "." + ((ip >> EIGHT) & IP_SEGMENT_MASK)
                + "." + ((ip >> SXITEEN) & IP_SEGMENT_MASK)
                + "." + (ip >> TWENTY_FOUR & IP_SEGMENT_MASK);
    }
}
