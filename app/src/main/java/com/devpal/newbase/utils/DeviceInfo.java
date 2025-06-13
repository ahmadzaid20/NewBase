package com.devpal.newbase.utils;

import android.os.Build;
import android.provider.Settings;
import android.content.Context;

/**
 * DeviceInfo: كلاس مساعد لجمع معلومات حول الجهاز.
 * يمكن استخدام هذه المعلومات لأغراض التحليلات، تصحيح الأخطاء، أو تخصيص التجربة.
 */
public final class DeviceInfo {

    private static final String TAG = "DeviceInfo";

    /**
     * يحصل على طراز الجهاز.
     * مثال: "SM-G998B", "Pixel 5"
     * @return اسم طراز الجهاز.
     */
    public static String getDeviceModel() {
        String model = Build.MODEL;
        AppLogger.d(TAG, "Device Model: " + model);
        return model;
    }

    /**
     * يحصل على الشركة المصنعة للجهاز.
     * مثال: "Samsung", "Google"
     * @return اسم الشركة المصنعة.
     */
    public static String getDeviceManufacturer() {
        String manufacturer = Build.MANUFACTURER;
        AppLogger.d(TAG, "Device Manufacturer: " + manufacturer);
        return manufacturer;
    }

    /**
     * يحصل على إصدار Android SDK الحالي.
     * مثال: 30 (Android 11), 31 (Android 12)
     * @return رقم إصدار SDK.
     */
    public static int getAndroidSdkVersion() {
        int sdkVersion = Build.VERSION.SDK_INT;
        AppLogger.d(TAG, "Android SDK Version: " + sdkVersion);
        return sdkVersion;
    }

    /**
     * يحصل على إصدار Android كنص.
     * مثال: "11", "12", "13"
     * @return سلسلة نصية لإصدار Android.
     */
    public static String getAndroidVersionName() {
        String versionName = Build.VERSION.RELEASE;
        AppLogger.d(TAG, "Android Version Name: " + versionName);
        return versionName;
    }

    /**
     * يحصل على معرف الجهاز الفريد (Android ID).
     * ملاحظة: هذا المعرف يتغير عند إعادة ضبط المصنع وقد لا يكون فريدًا بشكل كامل.
     * يجب استخدامه بحذر وعدم الاعتماد عليه للتعريف الحساس للمستخدمين.
     * @param context سياق التطبيق.
     * @return معرف الجهاز الفريد (Android ID).
     */
    public static String getAndroidId(Context context) {
        String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        AppLogger.d(TAG, "Android ID: " + androidId);
        return androidId;
    }

    // بناء كلاس DeviceInfo لا يمكن إنشاء مثيل منه (Non-instantiable)
    private DeviceInfo() {
        // منع إنشاء كائنات من هذا الكلاس
    }
}
