package com.devpal.newbase.listeners;

import com.devpal.newbase.Models.User; // استيراد كائن المستخدم
import com.devpal.newbase.exceptions.ApiException; // استيراد استثناء API

/**
 * OnLoginResultListener: واجهة مُخصصة للاستماع إلى نتائج عملية تسجيل الدخول.
 * تُستخدم لإرجاع نتائج تسجيل الدخول (نجاح المستخدم أو فشل مع خطأ) إلى الكلاس الذي يقوم بالاستماع.
 * هذا يفصل بين طبقة المنطق (مثل Repository) وطبقة واجهة المستخدم (مثل LoginActivity).
 */
public interface OnLoginResultListener {

    /**
     * تُستدعى عند نجاح عملية تسجيل الدخول.
     * @param user كائن المستخدم الذي تم تسجيل دخوله بنجاح.
     */
    void onSuccess(User user);

    /**
     * تُستدعى عند فشل عملية تسجيل الدخول.
     * @param error كائن ApiException يصف الخطأ الذي حدث.
     */
    void onFailure(ApiException error);

    /**
     * تُستدعى عند وجود مشكلة في الاتصال بالإنترنت.
     */
    void onNoInternet();
}
