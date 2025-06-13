package com.devpal.newbase.repository;

import com.devpal.newbase.Models.Notification;
import com.devpal.newbase.Network.ApiHelper;
import com.devpal.newbase.database.dao.NotificationDao;
import com.devpal.newbase.response.ApiResponse;
import com.devpal.newbase.response.BaseResponse;
import com.devpal.newbase.utils.AppLogger;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers; // تأكد من استيراد Schedulers الصحيح

/**
 * NotificationRepository: كلاس مسؤول عن إدارة بيانات الإشعارات.
 * يعمل كوسيط بين طبقة واجهة المستخدم (UI) ومصادر البيانات (API و Room Database).
 * يستخدم Dagger Hilt لحقن ApiHelper و NotificationDao.
 * @Singleton: يضمن وجود مثيل واحد فقط من هذا الكلاس طوال دورة حياة التطبيق.
 */
@Singleton
public class NotificationRepository {

    private static final String TAG = "NotificationRepository";
    private final ApiHelper apiHelper;
    private final NotificationDao notificationDao;

    /**
     * مُنشئ يقوم بحقن التبعيات.
     * @param apiHelper مساعد API للتعامل مع الشبكة.
     * @param notificationDao كائن DAO للوصول إلى بيانات الإشعارات في قاعدة البيانات المحلية.
     */
    @Inject
    public NotificationRepository(ApiHelper apiHelper, NotificationDao notificationDao) {
        this.apiHelper = apiHelper;
        this.notificationDao = notificationDao;
        AppLogger.d(TAG, "NotificationRepository initialized.");
    }

    /**
     * يحصل على قائمة الإشعارات. يحاول أولاً من API، ثم من قاعدة البيانات المحلية في حالة عدم توفر الإنترنت.
     * @return Single يحتوي على ApiResponse من نوع قائمة Notifications.
     */
    public Single<ApiResponse<List<Notification>>> getNotifications() {
        return apiHelper.getNotifications()
                .flatMap(response -> {
                    if (response.isSuccess() && response.getData() != null) {
                        // تخزين الإشعارات في قاعدة البيانات المحلية بعد الجلب الناجح من API
                        return notificationDao.insertAllNotifications(response.getData())
                                .andThen(Single.just(response)); // ثم إرجاع استجابة API الأصلية
                    } else {
                        // في حالة فشل الجلب من API، أرجع استجابة API الأصلية
                        return Single.just(response);
                    }
                })
                .onErrorResumeNext(throwable -> {
                    // إذا فشل طلب API (ربما بسبب عدم توفر الإنترنت)، حاول من قاعدة البيانات المحلية
                    AppLogger.w(TAG, "Failed to fetch notifications from API, trying local DB: " + throwable.getMessage());
                    return notificationDao.getAllNotifications()
                            .map(notifications -> ApiResponse.success(notifications)) // تحويل القائمة إلى ApiResponse.success
                            .onErrorResumeNext(dbThrowable -> {
                                // إذا فشل حتى من قاعدة البيانات المحلية، أرجع خطأ API الأصلي
                                AppLogger.e(TAG, "Failed to fetch notifications from local DB as well.", dbThrowable);
                                return Single.error(throwable); // أرجع الخطأ الأصلي من API
                            });
                })
                .subscribeOn(Schedulers.io()); // ضمان تنفيذ العمليات في IO thread
    }

    /**
     * يضع علامة "مقروء" على إشعار معين عبر API.
     * @param notificationId معرف الإشعار.
     * @return Single يحتوي على ApiResponse من نوع BaseResponse.
     */
    public Single<ApiResponse<BaseResponse>> markNotificationAsRead(String notificationId) {
        return apiHelper.markNotificationAsRead(notificationId)
                .subscribeOn(Schedulers.io()); // ضمان تنفيذ العمليات في IO thread
    }

    // --- عمليات قاعدة البيانات المحلية (Local Database Operations) ---

    /**
     * يحصل على جميع الإشعارات المخزنة محليًا.
     * @return Single يحتوي على قائمة من كائنات Notification.
     */
    public Single<List<Notification>> getLocalNotifications() {
        return notificationDao.getAllNotifications()
                .subscribeOn(Schedulers.io());
    }

    /**
     * تخزين إشعار واحد في قاعدة البيانات المحلية.
     * @param notification كائن الإشعار المراد تخزينه.
     * @return Completable للإشارة إلى اكتمال العملية.
     */
    public Completable saveNotificationLocally(Notification notification) {
        return notificationDao.insertNotification(notification)
                .subscribeOn(Schedulers.io());
    }

    /**
     * تخزين قائمة من الإشعارات في قاعدة البيانات المحلية.
     * @param notifications قائمة الإشعارات المراد تخزينها.
     * @return Completable للإشارة إلى اكتمال العملية.
     */
    public Completable saveAllNotificationsLocally(List<Notification> notifications) {
        return notificationDao.insertAllNotifications(notifications)
                .subscribeOn(Schedulers.io());
    }
}
