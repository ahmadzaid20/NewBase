package com.devpal.newbase.utils;

/**
 * Constants: كلاس يحتوي على جميع الثوابت العامة المستخدمة في التطبيق.
 * يساعد في تنظيم القيم الثابتة في مكان واحد لسهولة الوصول والتعديل.
 */
public final class Constants {

    // --- أكواد حالة API (يمكن تخصيصها حسب API الخاص بك) ---
    public static final int API_SUCCESS_CODE = 200;
    public static final int API_BAD_REQUEST_CODE = 400;
    public static final int API_UNAUTHORIZED_CODE = 401;
    public static final int API_NOT_FOUND_CODE = 404;
    public static final int API_INTERNAL_SERVER_ERROR_CODE = 500;
    public static final int API_NO_INTERNET_CODE = -1; // رمز خاص بنا لعدم وجود إنترنت
    public static final int API_TIMEOUT_CODE = -2;     // رمز خاص بنا لانتهاء المهلة

    // --- مفاتيح SharedPreferences (إذا لم يتم إدارتها بالكامل في SessionManager) ---
    // public static final String PREF_KEY_SOME_SETTING = "some_setting_key";

    // --- أكواد طلب الصلاحيات (Request Codes for Permissions) ---
    public static final int PERMISSION_REQUEST_CODE_LOCATION = 101;
    public static final int PERMISSION_REQUEST_CODE_STORAGE = 102;
    public static final int PERMISSION_REQUEST_CODE_CAMERA = 103;

    // --- أكواد الأنشطة (Activity Request Codes) ---
    public static final int ACTIVITY_REQUEST_CODE_LOGIN = 201;
    public static final int ACTIVITY_REQUEST_CODE_PICK_IMAGE = 202;

    // --- مفاتيح تمرير البيانات بين الأنشطة/الـ Fragments (Bundle Keys) ---
    public static final String BUNDLE_KEY_USER_ID = "user_id";
    public static final String BUNDLE_KEY_NOTIFICATION_ID = "notification_id";
    public static final String BUNDLE_KEY_WEBVIEW_URL = "webview_url";
    public static final String BUNDLE_KEY_WEBVIEW_TITLE = "webview_title";

    // --- قيم افتراضية أو عامة أخرى ---
    public static final long SPLASH_DISPLAY_LENGTH = 2000; // مدة شاشة البداية بالمللي ثانية

    // بناء كلاس Constants لا يمكن إنشاء مثيل منه (Non-instantiable)
    private Constants() {
        // منع إنشاء كائنات من هذا الكلاس
    }
}
