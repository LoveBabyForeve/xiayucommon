package com.xiayule.commonlibrary.permission;

import android.Manifest;

/**
 * @Description: 自定义 需要动态请求的权限 组合
 * @Author: 下雨了
 * @CreateDate: 2020/5/9 15:17
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/5/9 15:17
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */

/**
 * =================================================================================================
 * 正常权限（Normal Permissions）:
 *
 * ACCESS_LOCATION_EXTRA_COMMANDS   //用于申请调用A-GPS模块
 * ACCESS_NETWORK_STATE             //获取运营商信息，用于支持提供运营商信息相关的接口
 * ACCESS_NOTIFICATION_POLICY
 * ACCESS_WIFI_STATE                //用于访问wifi网络信息
 * BLUETOOTH
 * BLUETOOTH_ADMIN
 * BROADCAST_STICKY
 * CHANGE_NETWORK_STATE
 * CHANGE_WIFI_MULTICAST_STATE
 * CHANGE_WIFI_STATE                //获取wifi的权限 更改wifi状态
 * DISABLE_KEYGUARD
 * EXPAND_STATUS_BAR
 * GET_PACKAGE_SIZE
 * INSTALL_SHORTCUT
 * INTERNET
 * KILL_BACKGROUND_PROCESSES
 * MODIFY_AUDIO_SETTINGS
 * NFC
 * READ_SYNC_SETTINGS
 * READ_SYNC_STATS
 * RECEIVE_BOOT_COMPLETED
 * REORDER_TASKS
 * REQUEST_INSTALL_PACKAGES         // 请求安装包apk
 * SET_ALARM
 * SET_TIME_ZONE
 * SET_WALLPAPER
 * SET_WALLPAPER_HINTS
 * TRANSMIT_IR
 * UNINSTALL_SHORTCUT
 * USE_FINGERPRINT
 * VIBRATE
 * WAKE_LOCK
 * WRITE_SYNC_SETTINGS
 *
 *
 * =================================================================================================
 * 危险权限（Dangerous Permissions）：
 *
 * CALENDAR（日历）
 *          READ_CALENDAR
 *          WRITE_CALENDAR
 * CAMERA（相机）
 *          CAMERA
 * CONTACTS（联系人）
 *          READ_CONTACTS
 *          WRITE_CONTACTS
 *          GET_ACCOUNTS
 * LOCATION（位置）
 *          ACCESS_FINE_LOCATION
 *          ACCESS_COARSE_LOCATION
 * MICROPHONE（麦克风）
 *          RECORD_AUDIO
 * PHONE（手机）
 *          READ_PHONE_STATE
 *          CALL_PHONE
 *          READ_CALL_LOG
 *          WRITE_CALL_LOG
 *          ADD_VOICEMAIL
 *          USE_SIP
 *          PROCESS_OUTGOING_CALLS
 * SENSORS（传感器）
 *          BODY_SENSORS
 * SMS（短信）
 *          SEND_SMS
 *          RECEIVE_SMS
 *          READ_SMS
 *          RECEIVE_WAP_PUSH
 *          RECEIVE_MMS
 * STORAGE（存储卡）
 *          READ_EXTERNAL_STORAGE
 *          WRITE_EXTERNAL_STORAGE
 *
 */

public class EasyPermissionManager {
    /** 相机 */
    public static String CAMERA = Manifest.permission.CAMERA;
    /** 录音  */
    public static String RECORD_AUDIO = Manifest.permission.RECORD_AUDIO;
    /** 文件读取 */
    public static String READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE;
    /** 文件写入 */
    public static String WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    /** 访问电话状态读取短信内容 */
    public static String READ_PHONE_STATE = Manifest.permission.READ_PHONE_STATE;
    /** 访问网络定位 */
    public static String ACCESS_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    /** 访问GPS定位 */
    public static String ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    /** 拨打电话 */
    public static String CALL_PHONE = Manifest.permission.CALL_PHONE;


    // 全部权限
    public static final String[] PERMISSION_ALL = new String[]{
            CAMERA,
            RECORD_AUDIO,
            READ_EXTERNAL_STORAGE,
            WRITE_EXTERNAL_STORAGE,
            READ_PHONE_STATE,
            ACCESS_COARSE_LOCATION,
            ACCESS_FINE_LOCATION,
            CALL_PHONE
    };
    // 相机
    public static final String[] PERMISSION_CAMERA = new String[]{
            CAMERA,
            RECORD_AUDIO,
            READ_EXTERNAL_STORAGE,
            WRITE_EXTERNAL_STORAGE,
            ACCESS_COARSE_LOCATION,
            ACCESS_FINE_LOCATION
    };
    //录音
    public static final String[] PERMISSION_RECORD = new String[]{
            RECORD_AUDIO,
            READ_EXTERNAL_STORAGE,
            WRITE_EXTERNAL_STORAGE,
            ACCESS_COARSE_LOCATION,
            ACCESS_FINE_LOCATION
    };

    //文件读取写入
    public static final String[] PERMISSION_EXTERNAL = new String[]{
            READ_EXTERNAL_STORAGE,
            WRITE_EXTERNAL_STORAGE,
            ACCESS_COARSE_LOCATION,
            ACCESS_FINE_LOCATION
    };
    //定位
    public static final String[] PERMISSION_LOCATION = new String[]{
            ACCESS_COARSE_LOCATION,
            ACCESS_FINE_LOCATION
    };
    //文件读取
    public static final String[] PERMISSION_EXTERNAL_STORAGE = new String[]{
            READ_EXTERNAL_STORAGE,
            WRITE_EXTERNAL_STORAGE
    };
}
