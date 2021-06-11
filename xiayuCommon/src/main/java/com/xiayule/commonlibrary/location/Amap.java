package com.xiayule.commonlibrary.location;


import android.content.Context;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.xiayule.commonlibrary.utlis.KLog;

import java.util.concurrent.ExecutionException;

/**
 * @Description: 使用高德地图定位
 * @Author: 下雨了
 * @CreateDate: 2020/5/9 11:10
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/5/9 11:10
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * <p>
 * 使用方法
 * mAmap = Amap.getInstance();
 * mAmap.getLocation(this);
 * mAmap.setOnAmapListenter(new AmapListener() {
 * @Override public void getResult(AMapLocation aMapLocation) {
 * aMapLocation.getAddress();
 * KLog.i(TAG, aMapLocation.getAddress());
 * }
 * );
 */
public class Amap {
    private static Amap mAmap;

    public static synchronized Amap getInstance() {
        if (mAmap == null) {
            mAmap = new Amap();
        }
        return mAmap;
    }

    private AmapListener amapListener;

    public void setOnAmapListenter(AmapListener amapListenter) {
        this.amapListener = amapListenter;
    }

    //声明AMapLocationClient类对象
    private AMapLocationClient mLocationClient = null;

    public AMapLocationClient getLocationClient() {
        return mLocationClient;
    }

    //声明定位回调监听器
    private AMapLocationListener mLocationListener = new MyAMapLocationListener();

    //声明AMapLocationClientOption对象
    private AMapLocationClientOption mLocationOption = null;

    public void getLocation(Context context) {
        //初始化定位
        mLocationClient = new AMapLocationClient(context);
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);

        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        //Hight_Accuracy 高精度定位模式：会同时使用网络定位和GPS定位，优先返回最高精度的定位结果，以及对应的地址描述信息。
        //Battery_Saving 低功耗定位模式：不会使用GPS和其他传感器，只会使用网络定位（Wi-Fi和基站定位）；
        //Device_Sensors 仅用设备定位模式：不需要连接网络，只使用GPS进行定位，这种模式下不支持室内环境的定位，需要在室外环境下才可以成功定位。注意，自 v2.9.0 版本之后，仅设备定位模式下支持返回地址描述信息。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);

        //获取一次定位结果：
        //该方法默认为false。
        mLocationOption.setOnceLocation(true);

        //获取最近3s内精度最高的一次定位结果：
        //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        mLocationOption.setOnceLocationLatest(true);

        //设置定位间隔,单位毫秒,默认为2000ms，最低1000ms。
//        mLocationOption.setInterval(3000);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //单位是毫秒，默认30000毫秒，建议超时时间不要低于8000毫秒。
        mLocationOption.setHttpTimeOut(20000);
        //开启缓存机制
        mLocationOption.setLocationCacheEnable(true);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }

    private class MyAMapLocationListener implements AMapLocationListener {
        // 存放定位信息
//        HashMap<String, String> arrayMap = new HashMap<>();

        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if (aMapLocation != null) {
                if (aMapLocation.getErrorCode() == 0) {


                    StringBuffer sb = new StringBuffer();
//                    sb.append("获取定位精度（单位:米）:");
//                    sb.append(aMapLocation.getAccuracy());
//                    sb.append("获取海拔高度（单位:米）：");
//                    sb.append(aMapLocation.getAltitude());
//                    sb.append("获取方向角(单位：度0代表正北90代表正东）：");
//                    sb.append(aMapLocation.getBearing());
//                    sb.append("经度：");
//                    sb.append(aMapLocation.getLongitude());
//                    sb.append("维度：");
//                    sb.append(aMapLocation.getLatitude());
//                    sb.append("详细地址：");
//                    sb.append(aMapLocation.getAddress());

//                    sb.append("国家信息:");
                    sb.append(aMapLocation.getCountry());
//                    sb.append("省信息:");
                    sb.append(aMapLocation.getProvince());
//                    sb.append("城市信息:");
                    sb.append(aMapLocation.getCity());
//                    sb.append("城区信息:");
                    sb.append(aMapLocation.getDistrict());
//                    sb.append("街道信息:");
                    sb.append(aMapLocation.getStreet());
//                    sb.append("街道门牌号信息:");
                    sb.append(aMapLocation.getStreetNum());

//                    sb.append("当前室内定位的楼层:");
//                    sb.append(aMapLocation.getFloor());

//                    arrayMap.put("Longitude", String.valueOf(aMapLocation.getLongitude()));
//                    arrayMap.put("Latitude", String.valueOf(aMapLocation.getLatitude()));

                    KLog.d("Amap", "位置：" + sb.toString());
                    // 回调接口返回数据
                    try {
                        amapListener.onSuccess(aMapLocation);
                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                    }
                    mLocationClient.stopLocation();
                } else {
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                    KLog.e("AmapError", "location Error, ErrCode:" + aMapLocation.getErrorCode() +
                            ", errInfo:" + aMapLocation.getErrorInfo());
                    amapListener.onError("ErrCode:" + aMapLocation.getErrorCode() + ", errInfo:" + aMapLocation.getErrorInfo());
                }

                //停止定位后，本地定位服务并不会被销毁
                mLocationClient.stopLocation();
            }
        }
    }


//    public HashMap<String, String> getLocationInfo() {
//        if (arrayMap == null) {
//            mLocationClient.startLocation();
//        }
//        return arrayList;
//    }
}
