package com.devpal.newbase; // تأكد أن الحزمة هي com.devpal.newbase

import android.app.Application;
import dagger.hilt.android.HiltAndroidApp;
import com.devpal.newbase.utils.AppLogger; // استيراد AppLogger

/**
 * BaseApplication: كلاس Application الرئيسي للتطبيق.
 *
 * @HiltAndroidApp: هذا التعليق التوضيحي يخبر Hilt ببدء توليد مكونات Dagger Hilt
 * على مستوى التطبيق. يجب أن يكون هذا الكلاس هو كلاس الـ Application الوحيد في مشروعك
 * الذي يحمل هذا التعليق.
 */
@HiltAndroidApp
public class BaseApplication extends Application {

    private static final String TAG = "BaseApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        // تهيئة أي مكتبات أو خدمات عالمية هنا
        AppLogger.d(TAG, "BaseApplication initialized.");

        // إذا كنت تستخدم Timber (لمكتبة التسجيل المتقدمة)، يمكنك تهيئته هنا:
        // if (BuildConfig.DEBUG) { // تهيئة Timber فقط في وضع Debug
        //     Timber.plant(new Timber.DebugTree());
        // }
    }
}
