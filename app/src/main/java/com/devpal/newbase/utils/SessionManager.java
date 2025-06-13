package com.devpal.newbase.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.devpal.newbase.Models.User; // سنستخدم كائن User الذي أنشأناه
import com.google.gson.Gson; // لتبسيط تخزين الكائنات كـ JSON
import javax.inject.Inject; // for Dagger Hilt injection
import javax.inject.Singleton; // for Dagger Hilt singleton scope

/**
 * SessionManager: كلاس لإدارة جلسة المستخدم (تسجيل الدخول، تسجيل الخروج، تخزين بيانات المستخدم).
 * يستخدم SharedPreferences لتخزين البيانات بشكل محلي.
 *
 * @Singleton: يضمن وجود مثيل واحد فقط من هذا الكلاس طوال دورة حياة التطبيق.
 */
@Singleton
public class SessionManager {

    private static final String PREF_NAME = "DevPalAppPrefs"; // اسم ملف SharedPreferences
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn"; // مفتاح لتخزين حالة تسجيل الدخول
    private static final String KEY_AUTH_TOKEN = "authToken"; // مفتاح لتخزين رمز المصادقة (Auth Token)
    private static final String KEY_USER_DATA = "userData"; // مفتاح لتخزين بيانات المستخدم كـ JSON

    private final SharedPreferences pref;
    private final SharedPreferences.Editor editor;
    private final Gson gson; // لإدارة تحويل كائنات Java إلى JSON والعكس

    private static final String TAG = "SessionManager";

    /**
     * مُنشئ (Constructor) يقوم بحقن السياق (Context) بواسطة Dagger Hilt
     * ويُهيئ SharedPreferences.
     * @param context سياق التطبيق (Application Context) المحقون بواسطة Hilt.
     */
    @Inject
    public SessionManager(Context context) {
        // تهيئة SharedPreferences مع وضع الخصوصية Private (فقط لهذا التطبيق)
        this.pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        this.editor = pref.edit(); // للحصول على مُحرر للتعديل على SharedPreferences
        this.gson = new Gson(); // تهيئة Gson
        AppLogger.d(TAG, "SessionManager initialized.");
    }

    /**
     * تسجيل دخول المستخدم.
     * يخزن رمز المصادقة (Auth Token) وبيانات المستخدم، ويُعيّن حالة تسجيل الدخول إلى true.
     * @param authToken رمز المصادقة المستلم من الخادم.
     * @param user كائن User الذي يحتوي على بيانات المستخدم.
     */
    public void loginUser(String authToken, User user) {
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.putString(KEY_AUTH_TOKEN, authToken);
        editor.putString(KEY_USER_DATA, gson.toJson(user)); // تحويل كائن User إلى JSON String
        editor.apply(); // تطبيق التغييرات بشكل غير متزامن
        AppLogger.d(TAG, "User logged in. Token stored: " + authToken);
    }

    /**
     * تسجيل خروج المستخدم.
     * يمسح جميع بيانات الجلسة المخزنة.
     */
    public void logoutUser() {
        editor.clear(); // يمسح جميع البيانات من SharedPreferences
        editor.apply(); // تطبيق التغييرات
        AppLogger.d(TAG, "User logged out. Session cleared.");
    }

    /**
     * يتحقق مما إذا كان المستخدم مسجلاً للدخول حاليًا.
     * @return true إذا كان المستخدم مسجلاً للدخول، false بخلاف ذلك.
     */
    public boolean isLoggedIn() {
        return pref.getBoolean(KEY_IS_LOGGED_IN, false); // القيمة الافتراضية false
    }

    /**
     * يحصل على رمز المصادقة (Auth Token) المخزن.
     * @return رمز المصادقة، أو null إذا لم يكن موجودًا.
     */
    public String getAuthToken() {
        return pref.getString(KEY_AUTH_TOKEN, null);
    }

    /**
     * يحصل على كائن بيانات المستخدم المخزن.
     * @return كائن User، أو null إذا لم يكن موجودًا.
     */
    public User getCurrentUser() {
        String userJson = pref.getString(KEY_USER_DATA, null);
        if (userJson != null) {
            return gson.fromJson(userJson, User.class); // تحويل JSON String إلى كائن User
        }
        return null;
    }

    /**
     * (اختياري) تحديث بيانات المستخدم المخزنة محليًا.
     * @param user كائن User الجديد.
     */
    public void updateCurrentUser(User user) {
        editor.putString(KEY_USER_DATA, gson.toJson(user));
        editor.apply();
        AppLogger.d(TAG, "Current user data updated.");
    }

    // يمكنك إضافة المزيد من دوال تخزين/استرجاع البيانات المحددة هنا
    // مثل: setDeviceId(String id), getDeviceId(), setLanguage(String lang), getLanguage()
}
