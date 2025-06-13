package com.devpal.newbase.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.devpal.newbase.Models.Notification;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

/**
 * NotificationDao: DAO لجدول الإشعارات.
 * يوفر عمليات CRUD باستخدام RxJava.
 */
@Dao
public interface NotificationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertNotification(Notification notification);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertAllNotifications(List<Notification> notifications);

    @Update
    Completable updateNotification(Notification notification);

    @Delete
    Completable deleteNotification(Notification notification);

    // تم تعديل هذا الاستعلام لاستخدام 'sent_at' بدلاً من 'timestamp' ليتوافق مع هيكل جدول Notifications.
    @Query("SELECT * FROM notifications ORDER BY sent_at DESC")
    Single<List<Notification>> getAllNotifications();

    @Query("SELECT * FROM notifications WHERE id = :notificationId LIMIT 1")
    Single<Notification> getNotificationById(String notificationId);

    @Query("DELETE FROM notifications")
    Completable deleteAllNotifications();
}
