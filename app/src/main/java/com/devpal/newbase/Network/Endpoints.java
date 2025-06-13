package com.devpal.newbase.Network; // تأكد أن الحزمة هي com.devpal.newbase.Network

/**
 * Endpoints: كلاس يحتوي على URLs الأساسية لـ API.
 * يُفضل وضع URLs هنا لتسهيل التغيير والإدارة.
 */
public class Endpoints {
    // URL الأساسي لـ API الخاص بك
    // تأكد من تحديث هذا الـ URL ليتناسب مع الـ API الفعلي الذي تستخدمه.
    // يجب أن يبدأ بـ "http://" أو "https://" ولا يجب أن يحتوي على أقواس مربعة.
    // مثال في ملف Constants.java أو ما شابه
    public static final String BASE_URL = "https://blackbearmobility.com/API_newBase/";
    // تأكد من أنه لا يحتوي على "/" في النهاية إذا كان الـ API نفسه لا يتوقع ذلك.
    // لكن في هذا المثال، قمت بوضع / في النهاية لضمان عمل Retrofit بشكل صحيح مع الـ paths

    // روابط نقاط النهاية للمصادقة (Authentication)
    public static final String LOGIN = "auth/login"; // مثال
    public static final String REGISTER = "auth/register"; // مثال
    public static final String FORGOT_PASSWORD = "user/forgot_password"; // مثال

    // روابط نقاط النهاية للمستخدمين (Users)
    public static final String GET_USER_PROFILE = "user/profile"; // مثال
    public static final String UPDATE_USER_PROFILE = "user/update_profile"; // مثال

    // روابط نقاط النهاية للإشعارات (Notifications)
    public static final String GET_NOTIFICATIONS = "notifications"; // مثال
    public static final String MARK_NOTIFICATION_AS_READ = "notifications/mark_read"; // مثال
}
