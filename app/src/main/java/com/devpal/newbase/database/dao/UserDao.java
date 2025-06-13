package com.devpal.newbase.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.devpal.newbase.Models.User;

import java.util.List;

import io.reactivex.Completable; // لعمليات لا تُرجع بيانات
import io.reactivex.Single;     // لعمليات تُرجع قيمة واحدة أو قائمة

/**
 * UserDao: واجهة الوصول إلى بيانات المستخدم.
 * توفر عمليات CRUD باستخدام RxJava لعمليات غير متزامنة.
 */
@Dao
public interface UserDao {

    /**
     * إدخال مستخدم جديد أو استبدال مستخدم موجود.
     * @param user كائن المستخدم.
     * @return Completable للإشارة إلى نجاح أو فشل العملية.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertUser(User user);

    /**
     * تحديث بيانات مستخدم.
     * @param user الكائن المحدث.
     * @return Completable للإشارة إلى نجاح أو فشل العملية.
     */
    @Update
    Completable updateUser(User user);

    /**
     * حذف مستخدم.
     * @param user المستخدم المراد حذفه.
     * @return Completable للإشارة إلى نجاح أو فشل العملية.
     */
    @Delete
    Completable deleteUser(User user);

    /**
     * جلب كل المستخدمين من الجدول.
     * @return Single بلائحة المستخدمين.
     */
    @Query("SELECT * FROM users")
    Single<List<User>> getAllUsers();

    /**
     * جلب مستخدم محدد حسب الـ ID.
     * @param userId معرف المستخدم.
     * @return Single يحتوي على المستخدم أو خطأ إن لم يوجد.
     */
    @Query("SELECT * FROM users WHERE id = :userId LIMIT 1")
    Single<User> getUserById(String userId);

    /**
     * حذف جميع المستخدمين من الجدول.
     * @return Completable عند الانتهاء.
     */
    @Query("DELETE FROM users")
    Completable deleteAllUsers();
}
