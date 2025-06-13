package com.devpal.newbase.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * InternetUtils: كلاس مساعد للتحقق من توفر الاتصال بالإنترنت.
 * يوفر دالة ثابتة يمكن استدعاؤها من أي مكان في التطبيق للتأكد من وجود اتصال.
 */
public class InternetUtils {

    private static final String TAG = "InternetUtils";

    /**
     * تتحقق مما إذا كان الجهاز متصلاً بالإنترنت (Wi-Fi أو Mobile Data).
     *
     * @param context سياق التطبيق أو الـ Activity.
     * @return true إذا كان الجهاز متصلاً بالإنترنت، false بخلاف ذلك.
     */
    public static boolean isInternetAvailable(Context context) {
        if (context == null) {
            AppLogger.e(TAG, "Context is null, cannot check internet availability.");
            return false; // لا يمكن التحقق إذا كان السياق فارغًا
        }

        // الحصول على خدمة ConnectivityManager
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {
            // الحصول على معلومات الشبكة النشطة حاليًا
            NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();

            // التحقق مما إذا كانت هناك شبكة نشطة وأنها متصلة
            boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
            AppLogger.d(TAG, "Internet available: " + isConnected);
            return isConnected;
        }
        AppLogger.w(TAG, "ConnectivityManager is null, cannot check internet availability.");
        return false;
    }

    // بناء كلاس InternetUtils لا يمكن إنشاء مثيل منه (Non-instantiable)
    private InternetUtils() {
        // منع إنشاء كائنات من هذا الكلاس
    }
}
