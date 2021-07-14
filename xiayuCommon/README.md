# xiayucommon
#公共库

| 名称                          | 名称                      | 引用地址                                                          |
|:------------------------------|:-------------------------|:------------------------------------------------------------------|
| easypermissions               | 权限管理库                | api pub.devrel:easypermissions:3.0.0                              |
| AMap                          | 地图                      | api files('libs/AMap_Location_V4.9.0_20200228.jar')               |
| tbs_sdk                       | 浏览器内核                | api files('libs/tbs_sdk_thirdapp_v4.3.0.67_43967_20200923.jar')   |
| DSBridge                      | DSBridge（支持X5内核）    | api 'com.github.wendux:DSBridge-Android:x5-3.0-SNAPSHOT'          |
| glide                         | 图片加载库                | api 'com.github.bumptech.glide:glide:4.11.0'                      |
| glide                         | 图片加载库                | annotationProcessor 'com.github.bumptech.glide:compiler:4.11.0'   |
| picture_library               | 图片选择器                | api 'com.github.LuckSiege.PictureSelector:picture_library:v2.6.1' |
| BaseRecyclerViewAdapterHelper | Adapter适配器             | api 'com.github.CymChad:BaseRecyclerViewAdapterHelper:3.0.4'      |
| Android-PickerView            | 时间选择器、省市区三级联动 | api 'com.contrarywind:Android-PickerView:4.1.9'                   |
| banner                        | banner轮播                | api 'com.youth.banner:banner:2.1.0'                               |
| Dialog V3                     | Dialog                   | api 'com.kongzue.dialog_v3x:dialog:3.2.4'                         |
| okhttp                        | 网络                      | api 'com.squareup.okhttp3:okhttp:3.11.0'                          |
| okhttp                        | 网络日志                  | api 'com.squareup.okhttp3:logging-interceptor:3.11.0'             |
| retrofit                      | retrofit                 | api 'com.squareup.retrofit2:retrofit:2.4.0'                       |
| gson                          | gson                     | api 'com.squareup.retrofit2:converter-gson:2.4.0'                 |
| adapter-rxjava"               | adapter-rxjava"          | api 'com.squareup.retrofit2:adapter-rxjava2:2.4.0'                |
| rxjava                        |                          | api "io.reactivex.rxjava2:rxjava:2.2.3"                           |
| rxandroid                     |                          | api "io.reactivex.rxjava2:rxandroid:2.1.0"                        |
| ucrop                         | 图片裁剪                  | api 'com.github.yalantis:ucrop:2.2.6'                             |

##使用方法：
```  java
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}

dependencies {
    implementation 'com.github.LoveBabyForeve:xiayucommon:1.2'
}
```