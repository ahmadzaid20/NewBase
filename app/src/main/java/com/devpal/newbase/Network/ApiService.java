package com.devpal.newbase.Network;

import com.devpal.newbase.Models.User; // سيتم إنشاء كلاسات الـ models لاحقًا
import com.devpal.newbase.Models.Notification;
import com.devpal.newbase.response.BaseResponse; // سيتم إنشاء كلاسات الـ response لاحقًا
import com.devpal.newbase.response.ApiResponse;

import java.util.List;

import io.reactivex.Single;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * ApiService: واجهة تحدد جميع نقاط نهاية (API Endpoints) التي سيتفاعل معها التطبيق.
 * يستخدم Retrofit لتنفيذ هذه الدوال تلقائيًا بناءً على التعليقات التوضيحية (Annotations).
 * يعيد Single من RxJava2 لتمثيل العمليات غير المتزامنة.
 */
public interface ApiService {

    // ---------------------- Authentication Endpoints ----------------------

    /**
     * طلب تسجيل الدخول للمستخدم.
     * @param loginRequest كائن يحتوي على بيانات تسجيل الدخول (مثل البريد الإلكتروني وكلمة المرور).
     * @return Single يحتوي على ApiResponse من نوع User (بيانات المستخدم بعد تسجيل الدخول).
     */
    @POST(Endpoints.LOGIN)
    Single<ApiResponse<User>> loginUser(@Body User loginRequest); // User هنا كمثال، يمكن إنشاء LoginRequestModel

    /**
     * طلب تسجيل مستخدم جديد.
     * @param registerRequest كائن يحتوي على بيانات التسجيل.
     * @return Single يحتوي على ApiResponse من نوع BaseResponse (تأكيد التسجيل).
     */
    @POST(Endpoints.REGISTER)
    Single<ApiResponse<BaseResponse>> registerUser(@Body User registerRequest); // User هنا كمثال، يمكن إنشاء RegisterRequestModel

    /**
     * طلب استعادة كلمة المرور.
     * @param email البريد الإلكتروني للمستخدم.
     * @return Single يحتوي على ApiResponse من نوع BaseResponse.
     */
    @POST(Endpoints.FORGOT_PASSWORD)
    Single<ApiResponse<BaseResponse>> forgotPassword(@Body String email); // يمكن أن يكون كائن request بدلاً من String

    // ---------------------- User Profile Endpoints ----------------------

    /**
     * الحصول على ملف تعريف المستخدم.
     * @return Single يحتوي على ApiResponse من نوع User (بيانات ملف تعريف المستخدم).
     */
    @GET(Endpoints.GET_USER_PROFILE)
    Single<ApiResponse<User>> getUserProfile();

    /**
     * تحديث ملف تعريف المستخدم.
     * @param userProfile كائن User يحتوي على البيانات المحدثة.
     * @return Single يحتوي على ApiResponse من نوع BaseResponse (تأكيد التحديث).
     */
    @POST(Endpoints.UPDATE_USER_PROFILE)
    Single<ApiResponse<BaseResponse>> updateUserProfile(@Body User userProfile);

    // ---------------------- Notifications Endpoints ----------------------

    /**
     * الحصول على قائمة الإشعارات للمستخدم.
     * @return Single يحتوي على ApiResponse من نوع قائمة Notifications.
     */
    @GET(Endpoints.GET_NOTIFICATIONS)
    Single<ApiResponse<List<Notification>>> getNotifications();

    /**
     * وضع علامة على إشعار كـ "مقروء".
     * @param notificationId معرف الإشعار.
     * @return Single يحتوي على ApiResponse من نوع BaseResponse.
     */
    @POST(Endpoints.MARK_NOTIFICATION_AS_READ)
    Single<ApiResponse<BaseResponse>> markNotificationAsRead(@Path("id") String notificationId);


    // ---------------------- Other Examples ----------------------

    /**
     * مثال على طلب GET مع متغير مسار (Path Variable).
     * @param userId معرف المستخدم.
     * @return Single يحتوي على ApiResponse من نوع User.
     */
    @GET("users/{userId}")
    Single<ApiResponse<User>> getUserDetails(@Path("userId") String userId);

    /**
     * مثال على طلب GET مع استعلام (Query Parameter).
     * @param status حالة المهمة.
     * @return Single يحتوي على ApiResponse من نوع قائمة Notifications.
     */
    @GET("tasks")
    Single<ApiResponse<List<Notification>>> getTasksByStatus(@retrofit2.http.Query("status") String status);
}
