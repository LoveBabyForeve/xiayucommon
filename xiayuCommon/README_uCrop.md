# uCrop - Image Cropping Library for Android

#### This project aims to provide an ultimate and flexible image cropping experience. Made in [Yalantis](https://yalantis.com/?utm_source=github)

#### [How We Created uCrop](https://yalantis.com/blog/how-we-created-ucrop-our-own-image-cropping-library-for-android/)
#### Check this [project on Dribbble](https://dribbble.com/shots/2484752-uCrop-Image-Cropping-Library)

<img src="preview.gif" width="800" height="600">

# Usage

*For a working implementation, please have a look at the Sample Project - sample*

<a href="https://play.google.com/store/apps/details?id=com.yalantis.ucrop.sample&utm_source=global_co&utm_medium=prtnr&utm_content=Mar2515&utm_campaign=PartBadge&pcampaignid=MKT-AC-global-none-all-co-pr-py-PartBadges-Oct1515-1"><img alt="Get it on Google Play" src="https://play.google.com/intl/en_us/badges/images/generic/en_badge_web_generic.png" width="185" height="70"/></a>

1. Include the library as local library project.

	```
	allprojects {
	   repositories {
	      jcenter()
	      maven { url "https://jitpack.io" }
	   }
	}
	```

    ``` implementation 'com.github.yalantis:ucrop:2.2.6' ``` - lightweight general solution
    
    ``` implementation 'com.github.yalantis:ucrop:2.2.6-native' ``` - get power of the native code to preserve image quality (+ about 1.5 MB to an apk size)
    
2. Add UCropActivity into your AndroidManifest.xml

    ```
    <activity
        android:name="com.yalantis.ucrop.UCropActivity"
        android:screenOrientation="portrait"
        android:theme="@style/Theme.AppCompat.Light.NoActionBar"/>
    ```

3. The uCrop configuration is created using the builder pattern.

	```java
    UCrop.of(sourceUri, destinationUri)
        .withAspectRatio(16, 9)
        .withMaxResultSize(maxWidth, maxHeight)
        .start(context);
    ```


4. Override `onActivityResult` method and handle uCrop result.

    ```java
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            final Uri resultUri = UCrop.getOutput(data);
        } else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);
        }
    }
    ```
5. You may want to add this to your PROGUARD config:

    ```
    -dontwarn com.yalantis.ucrop**
    -keep class com.yalantis.ucrop** { *; }
    -keep interface com.yalantis.ucrop** { *; }
    ```

# Customization

If you want to let your users choose crop ratio dynamically, just do not call `withAspectRatio(x, y)`.

uCrop builder class has method `withOptions(UCrop.Options options)` which extends library configurations.

Currently you can change:

   * image compression format (e.g. PNG, JPEG, WEBP), compression
   * image compression quality [0 - 100]. PNG which is lossless, will ignore the quality setting.
   * whether all gestures are enabled simultaneously
   * maximum size for Bitmap that is decoded from source Uri and used within crop view. If you want to override default behaviour.
   * toggle whether to show crop frame/guidelines
   * setup color/width/count of crop frame/rows/columns
   * choose whether you want rectangle or oval crop area
   * the UI colors (Toolbar, StatusBar, active widget state)
   * and more...
    
# Compatibility
  
  * Library - Android ICS 4.0+ (API 14) (Android GINGERBREAD 2.3+ (API 10) for versions <= 1.3.2)
  * Sample - Android ICS 4.0+ (API 14)
  * CPU - armeabi armeabi-v7a x86 x86_64 arm64-v8a (for versions >= 2.1.2)
