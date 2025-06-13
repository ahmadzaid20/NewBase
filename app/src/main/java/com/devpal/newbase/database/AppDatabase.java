package com.devpal.newbase.database; // تأكد من الحزمة

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters; // لاستخدام المحولات المخصصة

import com.devpal.newbase.Models.Notification;
import com.devpal.newbase.Models.User;
import com.devpal.newbase.database.converters.DateConverter; // سننشئ هذا الكلاس لاحقًا
import com.devpal.newbase.database.dao.NotificationDao;
import com.devpal.newbase.database.dao.UserDao;

/**
 * AppDatabase: فئة مجردة تمثل قاعدة بيانات Room الرئيسية للتطبيق.
 * تحدد الـ Entities (الجداول) والـ DAOs (كائنات الوصول إلى البيانات) التي تستخدمها القاعدة.
 *
 * version: رقم إصدار قاعدة البيانات. يجب زيادته عند إجراء أي تغييرات في هيكل الجداول.
 * entities: قائمة بالكلاسات التي تمثل الجداول في قاعدة البيانات.
 * exportSchema: إذا كان true، سيقوم Room بتصدير المخطط الخاص بقاعدة البيانات إلى مجلد محدد
 * (مفيد لعمليات الترحيل Migrations). اجعله false في بيئة الإنتاج لتجنب المشاكل.
 *
 * @TypeConverters: لتحديد محولات TypesConverters إذا كنت تخزن أنواع بيانات معقدة (مثل Date, UUID, JSON).
 */
@Database(
        entities = {User.class, Notification.class}, // تحديد كلاسات الـ Entity هنا
        version = 2, // **مهم: تم زيادة الإصدار إلى 2 بسبب تغيير هيكل User و Notification**
        exportSchema = false // اجعلها false في الإنتاج
)
// استخدام محولات الأنواع إذا كنت تخزن كائنات غير بدائية (مثل Date/Timestamp)
@TypeConverters({DateConverter.class}) // سيتم إنشاء هذا الكلاس قريباً
public abstract class AppDatabase extends RoomDatabase {

    // الدوال المجردة للحصول على DAOs
    public abstract UserDao userDao();
    public abstract NotificationDao notificationDao();

    // يمكنك إضافة المزيد من DAOs هنا إذا كان لديك جداول أخرى
}
