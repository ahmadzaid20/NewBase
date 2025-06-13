package com.devpal.newbase.Network;

import android.content.Context;

import com.devpal.newbase.exceptions.ApiException;
import com.devpal.newbase.exceptions.NoInternetException;
import com.devpal.newbase.Models.Notification;
import com.devpal.newbase.Models.User;
import com.devpal.newbase.response.ApiResponse;
import com.devpal.newbase.response.BaseResponse;
import com.devpal.newbase.utils.AppLogger;
import com.devpal.newbase.utils.InternetUtils;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.List;

import javax.inject.Inject; // For Dagger Hilt injection
import javax.inject.Singleton; // For Dagger Hilt singleton scope

import io.reactivex.Single;

import io.reactivex.schedulers.Schedulers;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.exceptions.CompositeException;



/**
 * ApiHelper: كلاس مساعد يغلف استدعاءات ApiService.
 * يوفر طبقة تجريد للتعامل مع عمليات الشبكة، بما في ذلك:
 * - التحقق من اتصال الإنترنت.
 * - معالجة الأخطاء العامة (مثل عدم وجود إنترنت، مهلة الاتصال، أخطاء API).
 * - تبسيط الواجهة للتعامل مع البيانات.
 *
 * يستخدم Dagger Hilt لحقن ApiService و Context.
 * @Singleton: يضمن وجود مثيل واحد فقط من هذا الكلاس طوال دورة حياة التطبيق.
 */
@Singleton
public class ApiHelper {

    private static final String TAG = "ApiHelper";
    private final ApiService apiService; // Retrofit's API interface
    private final Context context; // Application context for internet check

    /**
     * مُنشئ (Constructor) يقوم بحقن التبعيات (ApiService و Context) بواسطة Dagger Hilt.
     * @param apiService مثيل ApiService الذي تم حقنه.
     * @param context سياق التطبيق الذي تم حقنه.
     */
    @Inject
    public ApiHelper(ApiService apiService, Context context) {
        this.apiService = apiService;
        this.context = context;
        AppLogger.d(TAG, "ApiHelper initialized.");
    }

    /**
     * ينفذ طلب POST بشكل موحد لـ loginUser.
     * @param loginRequest كائن طلب تسجيل الدخول.
     * @return Single يحتوي على ApiResponse من نوع User.
     */
    public Single<ApiResponse<User>> loginUser(User loginRequest) {
        return handleApiCall(apiService.loginUser(loginRequest));
    }

    /**
     * ينفذ طلب POST بشكل موحد لـ registerUser.
     * @param registerRequest كائن طلب تسجيل مستخدم جديد.
     * @return Single يحتوي على ApiResponse من نوع BaseResponse.
     */
    public Single<ApiResponse<BaseResponse>> registerUser(User registerRequest) {
        return handleApiCall(apiService.registerUser(registerRequest));
    }

    /**
     * ينفذ طلب POST بشكل موحد لـ forgotPassword.
     * @param email البريد الإلكتروني للمستخدم.
     * @return Single يحتوي على ApiResponse من نوع BaseResponse.
     */
    public Single<ApiResponse<BaseResponse>> forgotPassword(String email) {
        return handleApiCall(apiService.forgotPassword(email));
    }

    /**
     * ينفذ طلب GET بشكل موحد لـ getUserProfile.
     * @return Single يحتوي على ApiResponse من نوع User.
     */
    public Single<ApiResponse<User>> getUserProfile() {
        return handleApiCall(apiService.getUserProfile());
    }

    /**
     * ينفذ طلب POST بشكل موحد لـ updateUserProfile.
     * @param userProfile كائن User يحتوي على البيانات المحدثة.
     * @return Single يحتوي على ApiResponse من نوع BaseResponse.
     */
    public Single<ApiResponse<BaseResponse>> updateUserProfile(User userProfile) {
        return handleApiCall(apiService.updateUserProfile(userProfile));
    }

    /**
     * ينفذ طلب GET بشكل موحد لـ getNotifications.
     * @return Single يحتوي على ApiResponse من نوع قائمة Notifications.
     */
    public Single<ApiResponse<List<Notification>>> getNotifications() {
        return handleApiCall(apiService.getNotifications());
    }

    /**
     * ينفذ طلب POST بشكل موحد لـ markNotificationAsRead.
     * @param notificationId معرف الإشعار.
     * @return Single يحتوي على ApiResponse من نوع BaseResponse.
     */
    public Single<ApiResponse<BaseResponse>> markNotificationAsRead(String notificationId) {
        return handleApiCall(apiService.markNotificationAsRead(notificationId));
    }

    /**
     * دالة مساعدة لتوحيد معالجة استدعاءات الـ API.
     * تقوم بالتحقق من الاتصال بالإنترنت، وتطبيق Schedulers لـ RxJava،
     * ومعالجة الأخطاء الشائعة (مثل عدم توفر الإنترنت ومهلة الاتصال و ApiException).
     *
     * @param apiCall الـ Single الناتج عن استدعاء دالة من ApiService.
     * @param <T> نوع البيانات المتوقع في الاستجابة (داخل ApiResponse).
     * @return Single معالَج جاهز للاشتراك (subscribe) في الواجهة (UI).
     */
    private <T> Single<ApiResponse<T>> handleApiCall(Single<ApiResponse<T>> apiCall) {
        // التحقق من اتصال الإنترنت قبل محاولة إجراء الطلب
        if (!InternetUtils.isInternetAvailable(context)) {
            AppLogger.e(TAG, "No internet connection detected.");
            return Single.error(new NoInternetException());
        }

        return apiCall
                .subscribeOn(Schedulers.io()) // تنفيذ الطلب في Background thread (IO thread)
                .observeOn(AndroidSchedulers.mainThread()) // مراقبة النتائج في Main thread (UI thread)
                .onErrorResumeNext(throwable -> {
                    // معالجة الأخطاء هنا
                    AppLogger.e(TAG, "API Call Error: " + throwable.getMessage(), throwable);

                    if (throwable instanceof IOException) {
                        // أخطاء الشبكة مثل NoInternetException أو SocketTimeoutException
                        if (throwable instanceof NoInternetException) {
                            return Single.error(throwable); // إذا كان NoInternetException، مرره كما هو
                        } else if (throwable instanceof SocketTimeoutException) {
                            return Single.error(new ApiException("مهلة الاتصال انتهت. يرجى المحاولة لاحقًا.", 0, null));
                        } else {
                            // أي IOException أخرى (مثل مشاكل DNS أو الاتصال العام)
                            return Single.error(new ApiException("حدث خطأ في الشبكة. يرجى التحقق من اتصالك.", 0, null));
                        }
                    } else if (throwable instanceof retrofit2.HttpException) {
                        // أخطاء HTTP (مثل 401, 404, 500)
                        retrofit2.HttpException httpException = (retrofit2.HttpException) throwable;
                        String errorBody = null;
                        try {
                            if (httpException.response() != null && httpException.response().errorBody() != null) {
                                errorBody = httpException.response().errorBody().string();
                            }
                        } catch (IOException e) {
                            AppLogger.e(TAG, "Error parsing error body: " + e.getMessage());
                        }
                        return Single.error(new ApiException(
                                "خطأ في الخادم: " + httpException.code(), // رسالة عامة
                                httpException.code(),                     // رمز حالة HTTP
                                errorBody                                 // جسم الخطأ الخام
                        ));
                    } else {
                        // أي أخطاء أخرى غير متوقعة
                        return Single.error(new ApiException("حدث خطأ غير متوقع. يرجى المحاولة لاحقًا.", 0, null));
                    }
                });
    }
}
