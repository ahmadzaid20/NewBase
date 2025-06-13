package com.devpal.newbase.repository;

import com.devpal.newbase.Models.User;
import com.devpal.newbase.Network.ApiHelper;
import com.devpal.newbase.database.dao.UserDao;
import com.devpal.newbase.response.ApiResponse;
import com.devpal.newbase.response.BaseResponse;
import com.devpal.newbase.utils.AppLogger;
import com.devpal.newbase.utils.SessionManager;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers; // تأكد من استيراد Schedulers الصحيح

/**
 * UserRepository: كلاس مسؤول عن إدارة بيانات المستخدم.
 * يعمل كوسيط بين طبقة واجهة المستخدم (UI) ومصادر البيانات (API و Room Database).
 * يستخدم Dagger Hilt لحقن ApiHelper و UserDao و SessionManager.
 * @Singleton: يضمن وجود مثيل واحد فقط من هذا الكلاس طوال دورة حياة التطبيق.
 */
@Singleton
public class UserRepository {

    private static final String TAG = "UserRepository";
    private final ApiHelper apiHelper;
    private final UserDao userDao;
    private final SessionManager sessionManager; // لإدارة جلسة المستخدم

    /**
     * مُنشئ يقوم بحقن التبعيات.
     * @param apiHelper مساعد API للتعامل مع الشبكة.
     * @param userDao كائن DAO للوصول إلى بيانات المستخدم في قاعدة البيانات المحلية.
     * @param sessionManager لإدارة جلسة المستخدم (Auth Token, بيانات المستخدم).
     */
    @Inject
    public UserRepository(ApiHelper apiHelper, UserDao userDao, SessionManager sessionManager) {
        this.apiHelper = apiHelper;
        this.userDao = userDao;
        this.sessionManager = sessionManager;
        AppLogger.d(TAG, "UserRepository initialized.");
    }

    // --- عمليات المصادقة (Authentication Operations) ---

    /**
     * يقوم بتسجيل دخول المستخدم من خلال API.
     * عند النجاح، يخزن بيانات المستخدم ورمز المصادقة في SessionManager.
     * @param email البريد الإلكتروني للمستخدم.
     * @param password كلمة المرور للمستخدم.
     * @return Single يحتوي على ApiResponse من نوع User.
     */
    public Single<ApiResponse<User>> loginUser(String email, String password) {
        User loginRequest = new User(email, password);
        return apiHelper.loginUser(loginRequest)
                .flatMap(response -> {
                    if (response.isSuccess() && response.getData() != null) {
                        // تخزين بيانات المستخدم ورمز المصادقة بعد تسجيل الدخول الناجح
                        sessionManager.loginUser(response.getData().getToken(), response.getData());
                        // إدراج المستخدم في قاعدة البيانات المحلية
                        return userDao.insertUser(response.getData())
                                .andThen(Single.just(response)); // ثم إرجاع استجابة API الأصلية
                    } else {
                        // في حالة فشل تسجيل الدخول من API
                        return Single.just(response);
                    }
                })
                .subscribeOn(Schedulers.io()); // ضمان تنفيذ العمليات في IO thread
    }

    /**
     * يقوم بتسجيل مستخدم جديد من خلال API.
     * @param registerRequest كائن User يحتوي على بيانات التسجيل.
     * @return Single يحتوي على ApiResponse من نوع BaseResponse.
     */
    public Single<ApiResponse<BaseResponse>> registerUser(User registerRequest) {
        return apiHelper.registerUser(registerRequest)
                .subscribeOn(Schedulers.io()); // ضمان تنفيذ العمليات في IO thread
    }

    /**
     * يقوم بإرسال طلب استعادة كلمة المرور عبر API.
     * @param email البريد الإلكتروني للمستخدم.
     * @return Single يحتوي على ApiResponse من نوع BaseResponse.
     */
    public Single<ApiResponse<BaseResponse>> forgotPassword(String email) {
        return apiHelper.forgotPassword(email)
                .subscribeOn(Schedulers.io()); // ضمان تنفيذ العمليات في IO thread
    }

    /**
     * تسجيل خروج المستخدم من التطبيق.
     * يقوم بمسح بيانات الجلسة من SessionManager وحذف بيانات المستخدم من قاعدة البيانات المحلية.
     * @return Completable للإشارة إلى اكتمال عملية تسجيل الخروج.
     */
    public Completable logoutUser() {
        sessionManager.logoutUser(); // مسح بيانات الجلسة من SharedPreferences
        return userDao.deleteAllUsers() // حذف المستخدمين من قاعدة البيانات المحلية
                .subscribeOn(Schedulers.io()); // ضمان تنفيذ العمليات في IO thread
    }

    // --- عمليات ملف تعريف المستخدم (User Profile Operations) ---

    /**
     * يحصل على ملف تعريف المستخدم. يحاول أولاً من API، ثم من قاعدة البيانات المحلية في حالة عدم توفر الإنترنت.
     * @return Single يحتوي على ApiResponse من نوع User.
     */
    public Single<ApiResponse<User>> getUserProfile() {
        return apiHelper.getUserProfile()
                .onErrorResumeNext(throwable -> {
                    // إذا فشل طلب API (ربما بسبب عدم توفر الإنترنت)، حاول من قاعدة البيانات المحلية
                    AppLogger.w(TAG, "Failed to fetch user profile from API, trying local DB: " + throwable.getMessage());
                    return userDao.getUserById(sessionManager.getCurrentUser().getId()) // افترض أن ID المستخدم متاح في SessionManager
                            .map(user -> ApiResponse.success(user)) // تحويل المستخدم إلى ApiResponse.success
                            .onErrorResumeNext(dbThrowable -> {
                                // إذا فشل حتى من قاعدة البيانات المحلية، أرجع خطأ API الأصلي
                                AppLogger.e(TAG, "Failed to fetch user profile from local DB as well.", dbThrowable);
                                return Single.error(throwable); // أرجع الخطأ الأصلي من API
                            });
                })
                .subscribeOn(Schedulers.io());
    }

    /**
     * يقوم بتحديث ملف تعريف المستخدم عبر API.
     * عند النجاح، يحدث بيانات المستخدم في SessionManager وقاعدة البيانات المحلية.
     * @param userProfile كائن User يحتوي على البيانات المحدثة.
     * @return Single يحتوي على ApiResponse من نوع BaseResponse.
     */
    public Single<ApiResponse<BaseResponse>> updateUserProfile(User userProfile) {
        return apiHelper.updateUserProfile(userProfile)
                .flatMap(response -> {
                    if (response.isSuccess()) {
                        // تحديث المستخدم في قاعدة البيانات المحلية بعد التحديث الناجح في API
                        return userDao.updateUser(userProfile)
                                .andThen(Single.just(response)); // ثم إرجاع استجابة API الأصلية
                    } else {
                        return Single.just(response);
                    }
                })
                .subscribeOn(Schedulers.io());
    }

    // --- عمليات قاعدة البيانات المحلية (Local Database Operations) ---

    /**
     * يحصل على بيانات المستخدم المخزنة محليًا.
     * @return Single يحتوي على كائن User.
     */
    public Single<User> getLocalUserProfile() {
        return userDao.getUserById(sessionManager.getCurrentUser().getId())
                .subscribeOn(Schedulers.io());
    }

    /**
     * تخزين بيانات المستخدم في قاعدة البيانات المحلية.
     * @param user كائن المستخدم المراد تخزينه.
     * @return Completable للإشارة إلى اكتمال العملية.
     */
    public Completable saveUserLocally(User user) {
        return userDao.insertUser(user)
                .subscribeOn(Schedulers.io());
    }
}
