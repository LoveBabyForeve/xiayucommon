package com.xiayule.commonlibrary.utlis;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;

import androidx.annotation.RequiresPermission;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;

import static android.Manifest.permission.ACCESS_NETWORK_STATE;

public class NetworkUtils {
    public static String url = "https://www.baidu.com";
    public static int NET_CNNT_BAIDU_OK = 1; // NetworkAvailable
    public static int NET_CNNT_BAIDU_TIMEOUT = 2; // no NetworkAvailable
    public static int NET_NOT_PREPARE = 3; // Net no ready
    public static int NET_ERROR = 4; //net error
    private static int TIMEOUT = 3000; // TIMEOUT

    /**
     * 网络类型
     */
    public enum NetType {
        NETWORK_ETHERNET, // 以太网
        NETWORK_WIFI,
        NETWORK_5G,
        NETWORK_4G,
        NETWORK_3G,
        NETWORK_2G,
        NETWORK_UNKNOWN, // 未知的
        NETWORK_NO
    }

    /**
     * 初始化网络信息
     */
    public static NetworkInfo getActiveNetworkInfo() {
        ConnectivityManager cm = (ConnectivityManager) Utils.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
            return null;
        }
        return cm.getActiveNetworkInfo();
    }

    /**
     * 检查 网络状态 是否能获取
     * check Network Available
     */
    public static boolean isNetworkAvailable() {
        if (getActiveNetworkInfo() == null) {
            return false;
        }
        return true;
    }

    /**
     * getLocalIpAddress 获取本地Ip地址
     */
    public static String getLocalIpAddress() {
        String ret = "";
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        ret = inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
        return ret;
    }

    /**
     * 返回当前网络状态
     */
    public static int getNetState() {
        try {
            NetworkInfo networkinfo = getActiveNetworkInfo();
            if (networkinfo != null) {
                if (networkinfo.isAvailable() && networkinfo.isConnected()) {
                    if (!connectionNetwork())
                        return NET_CNNT_BAIDU_TIMEOUT;
                    else
                        return NET_CNNT_BAIDU_OK;
                } else {
                    return NET_NOT_PREPARE;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return NET_ERROR;
    }

    /**
     * 返回当前网络类型
     */
    public static NetType getNetType() {
        if (isEthernet()) {
            return NetType.NETWORK_ETHERNET;
        }
        NetworkInfo info = getActiveNetworkInfo();
        if (info != null && info.isAvailable()) {
            if (isWifi()) {
                return NetType.NETWORK_WIFI;
            } else if (is2G()) {
                return NetType.NETWORK_2G;
            } else if (is3G()) {
                return NetType.NETWORK_3G;
            } else if (is4G()) {
                return NetType.NETWORK_4G;
            } else if (is5G()) {
                return NetType.NETWORK_5G;
            } else {
                String subtypeName = info.getSubtypeName();
                if (subtypeName.equalsIgnoreCase("TD-SCDMA")
                        || subtypeName.equalsIgnoreCase("WCDMA")
                        || subtypeName.equalsIgnoreCase("CDMA2000")) {
                    return NetType.NETWORK_3G;
                } else {
                    return NetType.NETWORK_UNKNOWN;
                }
            }
        }
        return NetType.NETWORK_NO;
    }

    /**
     * ping "http://www.baidu.com"
     */
    static private boolean connectionNetwork() {
        boolean result = false;
        HttpURLConnection httpUrl = null;
        try {
            httpUrl = (HttpURLConnection) new URL(url)
                    .openConnection();
            httpUrl.setConnectTimeout(TIMEOUT);
            httpUrl.connect();
            result = true;
        } catch (IOException e) {
        } finally {
            if (null != httpUrl) {
                httpUrl.disconnect();
            }
            httpUrl = null;
        }
        return result;
    }

    /**
     * 返回是否使用以太网。
     * Return whether using ethernet.
     */
    @RequiresPermission(ACCESS_NETWORK_STATE)
    public static boolean isEthernet() {
        final ConnectivityManager cm = (ConnectivityManager) Utils.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
            return false;
        }
        final NetworkInfo info = cm.getNetworkInfo(ConnectivityManager.TYPE_ETHERNET);
        if (info == null) {
            return false;
        }
        NetworkInfo.State state = info.getState();
        if (null == state) {
            return false;
        }
        return state == NetworkInfo.State.CONNECTED || state == NetworkInfo.State.CONNECTING;
    }

    /**
     * is2G
     */
    public static boolean is2G() {
        NetworkInfo activeNetInfo = getActiveNetworkInfo();
        if (activeNetInfo != null
                && activeNetInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
            switch (activeNetInfo.getSubtype()) {
                case TelephonyManager.NETWORK_TYPE_GSM:
                case TelephonyManager.NETWORK_TYPE_GPRS:
                case TelephonyManager.NETWORK_TYPE_CDMA:
                case TelephonyManager.NETWORK_TYPE_EDGE:
                case TelephonyManager.NETWORK_TYPE_1xRTT:
                case TelephonyManager.NETWORK_TYPE_IDEN:
                    return true;
                default:
                    return false;
            }
        }
        return false;
    }

    /**
     * check is3G
     */
    public static boolean is3G() {
        NetworkInfo activeNetInfo = getActiveNetworkInfo();
        if (activeNetInfo != null
                && activeNetInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
            switch (activeNetInfo.getSubtype()) {
                case TelephonyManager.NETWORK_TYPE_TD_SCDMA:
                case TelephonyManager.NETWORK_TYPE_EVDO_A:
                case TelephonyManager.NETWORK_TYPE_UMTS:
                case TelephonyManager.NETWORK_TYPE_EVDO_0:
                case TelephonyManager.NETWORK_TYPE_HSDPA:
                case TelephonyManager.NETWORK_TYPE_HSUPA:
                case TelephonyManager.NETWORK_TYPE_HSPA:
                case TelephonyManager.NETWORK_TYPE_EVDO_B:
                case TelephonyManager.NETWORK_TYPE_EHRPD:
                case TelephonyManager.NETWORK_TYPE_HSPAP:
                    return true;
                default:
                    return false;
            }
        }
        return false;
    }

    /**
     * check is4G
     */
    public static boolean is4G() {
        NetworkInfo activeNetInfo = getActiveNetworkInfo();
        if (activeNetInfo != null
                && activeNetInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
            switch (activeNetInfo.getSubtype()) {
                case TelephonyManager.NETWORK_TYPE_IWLAN:
                case TelephonyManager.NETWORK_TYPE_LTE:
                    return true;
                default:
                    return false;
            }
        }
        return false;
    }

    /**
     * check is5G
     */
    public static boolean is5G() {
        NetworkInfo activeNetInfo = getActiveNetworkInfo();
        if (activeNetInfo != null
                && activeNetInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
            switch (activeNetInfo.getSubtype()) {
                case TelephonyManager.NETWORK_TYPE_NR:
                    return true;
                default:
                    return false;
            }
        }
        return false;
    }

    /**
     * isWifi
     */
    public static boolean isWifi() {
        NetworkInfo activeNetInfo = getActiveNetworkInfo();
        if (activeNetInfo != null
                && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }
        return false;
    }

    /**
     * is wifi on
     */
    public static boolean isWifiEnabled(Context context) {
        ConnectivityManager mgrConn = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        TelephonyManager mgrTel = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        return ((mgrConn.getActiveNetworkInfo() != null && mgrConn
                .getActiveNetworkInfo().getState() == NetworkInfo.State.CONNECTED) || mgrTel
                .getNetworkType() == TelephonyManager.NETWORK_TYPE_UMTS);
    }

    /**
     * 获取IP地址
     */
    public static String getIpAddress(Context context) {
        String ip = "";
        ConnectivityManager mgrConn = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo mobileNetworkInfo = mgrConn.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo wifiNetworkInfo = mgrConn.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        assert mobileNetworkInfo != null;
        if (mobileNetworkInfo.isConnected()) {
            ip = getLocalIpAddress();
        } else if (wifiNetworkInfo.isConnected()) {
            ip = getWifiIpAddress(context);
        }
        return ip;
    }

    /**
     * getWifiIpAddress 获取 WIFI Ip地址
     */
    public static String getWifiIpAddress(Context context) {
        String ret = "";
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();
        ret = intToIp(ipAddress);
        return ret;
    }

    /**
     * 转化 wifi ip 地址
     */
    public static String intToIp(int ipInt) {
        StringBuilder sb = new StringBuilder();
        sb.append(ipInt & 0xFF).append(".");
        sb.append((ipInt >> 8) & 0xFF).append(".");
        sb.append((ipInt >> 16) & 0xFF).append(".");
        sb.append((ipInt >> 24) & 0xFF);
        return sb.toString();
    }

    /**
     * 首页加载时长
     */
    public static String getLoadingTime(String s) {
        LoadingTimeBean loadingTimeBean = new Gson().fromJson(s, LoadingTimeBean.class);

        //统计数据
        //白屏时间，也就是开始解析DOM耗时
        long firstPaint = loadingTimeBean.domLoading - loadingTimeBean.fetchStart;
        //加载总时间,这几乎代表了用户等待页面可用的时间
        long loadTime = loadingTimeBean.loadEventEnd - loadingTimeBean.navigationStart;
        //执行onload回调函数的时间,是否太多不必要的操作都放到 onload 回调函数里执行了，考虑过延迟加载、按需加载的策略么？
        long loadEventTime = loadingTimeBean.loadEventEnd - loadingTimeBean.loadEventStart;
        //用户可操作时间
        long domReadyTime = loadingTimeBean.domContentLoadedEventEnd - loadingTimeBean.navigationStart;
        //首屏时间
        long firstScreenTiming = domReadyTime;
        //解析DOM树结构的时间,期间要加载内嵌资源,反省下你的 DOM 树嵌套是不是太多了
        long parseDomTime = loadingTimeBean.domComplete - loadingTimeBean.domInteractive;
        //请求完毕至DOM加载耗时
        long initDomTreeTime = loadingTimeBean.domInteractive - loadingTimeBean.responseEnd;
        //重定向的时间,拒绝重定向！比如，http://example.com/ 就不该写成 http://example.com
        long redirectTime = loadingTimeBean.redirectEnd - loadingTimeBean.redirectStart;
        //DNS缓存耗时
        long appcacheTime = loadingTimeBean.domainLookupStart - loadingTimeBean.fetchStart;
        //DNS查询耗时,DNS 预加载做了么？页面内是不是使用了太多不同的域名导致域名查询的时间太长？
        long lookupDomainTime = loadingTimeBean.domainLookupEnd - loadingTimeBean.domainLookupStart;
        //TCP链接耗时
        long connectTime = loadingTimeBean.connectEnd - loadingTimeBean.connectStart;
        //内容加载完成的时间,页面内容经过 gzip 压缩了么，静态资源 css/js 等压缩了么？
        long requestTime = loadingTimeBean.responseEnd - loadingTimeBean.requestStart;
        //请求文档,开始请求文档到开始接收文档
        long requestDocumentTime = loadingTimeBean.responseStart - loadingTimeBean.requestStart;
        //接收文档,开始接收文档到文档接收完成
        long responseDocumentTime = loadingTimeBean.responseEnd - loadingTimeBean.responseStart;
        //读取页面第一个字节的时间,这可以理解为用户拿到你的资源占用的时间，加异地机房了么，加CDN 处理了么？加带宽了么？加 CPU 运算速度了么？
        long TTFB = loadingTimeBean.responseStart - loadingTimeBean.navigationStart;

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("白屏时间", firstPaint / 1000 + "s");
        map.put("执行onload回调函数的时间", domReadyTime / 1000 + "s");
        map.put("用户可操作时间", domReadyTime / 1000 + "s");
        map.put("首屏时间", firstScreenTiming / 1000 + "s");
        map.put("解析DOM树结构的时间", parseDomTime / 1000 + "s");
        map.put("请求完毕至DOM加载耗时", initDomTreeTime / 1000 + "s");
        map.put("重定向的时间", redirectTime / 1000 + "s");
        map.put("DNS缓存耗时", appcacheTime / 1000 + "s");
        map.put("DNS查询耗时", lookupDomainTime / 1000 + "s");
        map.put("TCP链接耗时", connectTime / 1000 + "s");
        map.put("内容加载完成的时间", requestTime / 1000 + "s");
        map.put("请求文档", requestDocumentTime / 1000 + "s");
        map.put("接收文档", responseDocumentTime / 1000 + "s");
        map.put("读取页面第一个字节的时间", TTFB / 1000 + "s");

        return new Gson().toJson(map);
    }


    /**
     * 首页加载时间 Bean 类
     */
    public class LoadingTimeBean implements Serializable {
        // 用户录入地址
        public long navigationStart; //浏览器处理当前网页的启动时间

        public long unloadEventStart;
        public long unloadEventEnd;

        // 重定向
        public long redirectStart; // 到当前页面的重定向开始的时间。但只有在重定向的页面来自同一个域时这个属性才会有值；否则，值为0
        public long redirectEnd; // 到当前页面的重定向结束的时间。但只有在重定向的页面来自同一个域时这个属性才会有值；否则，值为0

        // APP缓存
        public long fetchStart; //浏览器发起http请求读取文档的毫秒时间戳

        // DNS
        public long domainLookupStart; // 域名查询开始时的时间戳
        public long domainLookupEnd; // 域名查询结束时的时间戳

        // TCP
        public long connectStart; // http请求开始向服务器发送的时间戳
        public long connectEnd; // 浏览器与服务器连接建立（握手和认证过程结束）的毫秒时间戳
        public long secureConnectionStart;

        // request and response
        public long requestStart;
        public long responseStart; // 浏览器向服务器发出http请求时的时间戳。或者开始读取本地缓存时
        public long responseEnd; // 浏览器从服务器收到最后一个字节时的毫秒时间戳

        // DOM
        public long domLoading; // 浏览器开始解析网页DOM结构的时间
        public long domInteractive; // 网页dom树创建完成，开始加载内嵌资源的时间
        public long domContentLoadedEventStart;
        public long domContentLoadedEventEnd; // 网页所有需要执行的脚本执行完成时的时间，domReady的时间
        public long domComplete; // 网页dom结构生成时的时间戳

        // onLoad
        public long loadEventStart; // 当前网页load事件的回调函数开始执行的时间戳
        public long loadEventEnd;  // 当前网页load事件的回调函数结束运行时的时间戳
    }

}
