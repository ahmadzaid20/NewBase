package com.devpal.newbase.utils;

import android.util.Log;

import com.devpal.newbase.BuildConfig; // سيتم استخدام هذا الكلاس لـ DEBUG_MODE

/**
 * AppLogger: كلاس مساعد لتسجيل الرسائل في Logcat.
 * يسمح بالتحكم في عرض السجلات بناءً على وضع التطوير (Debug/Release).
 * يتم استخدام BuildConfig.DEBUG لتحديد ما إذا كان التطبيق في وضع التصحيح أم لا.
 */
public class AppLogger {

    // متغير ثابت يحدد ما إذا كنا في وضع التطوير (Debug) أم لا.
    // يتم تهيئته تلقائياً من BuildConfig.DEBUG.
    // هذا يسمح بإظهار سجلات Debug فقط في وضع التطوير وإخفائها في وضع الإنتاج (Release)
    private static final boolean DEBUG_MODE = BuildConfig.DEBUG;

    /**
     * يسجل رسالة Debug في Logcat.
     * @param tag الـ Tag لرسالة السجل (عادة اسم الكلاس).
     * @param message الرسالة المراد تسجيلها.
     */
    public static void d(String tag, String message) {
        if (DEBUG_MODE) {
            Log.d(tag, message);
        }
    }

    /**
     * يسجل رسالة Info في Logcat.
     * @param tag الـ Tag لرسالة السجل.
     * @param message الرسالة المراد تسجيلها.
     */
    public static void i(String tag, String message) {
        if (DEBUG_MODE) { // يمكن تغيير هذا لإظهار Info في كل الأوضاع إذا أردت
            Log.i(tag, message);
        }
    }

    /**
     * يسجل رسالة Warning في Logcat.
     * @param tag الـ Tag لرسالة السجل.
     * @param message الرسالة المراد تسجيلها.
     */
    public static void w(String tag, String message) {
        if (DEBUG_MODE) { // يمكن تغيير هذا لإظهار Warning في كل الأوضاع إذا أردت
            Log.w(tag, message);
        }
    }

    /**
     * يسجل رسالة Error في Logcat.
     * @param tag الـ Tag لرسالة السجل.
     * @param message الرسالة المراد تسجيلها.
     */
    public static void e(String tag, String message) {
        // رسائل الأخطاء يجب أن تظهر دائمًا، حتى في وضع الإنتاج
        Log.e(tag, message);
    }

    /**
     * يسجل رسالة Error مع استثناء (Throwable) في Logcat.
     * @param tag الـ Tag لرسالة السجل.
     * @param message الرسالة المراد تسجيلها.
     * @param throwable الاستثناء المراد تسجيله.
     */
    public static void e(String tag, String message, Throwable throwable) {
        Log.e(tag, message, throwable);
    }

    // يمكنك إضافة المزيد من أنواع السجلات (مثل VERBOSE أو ASSERT) إذا لزم الأمر.
}
