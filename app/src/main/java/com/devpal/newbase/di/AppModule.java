package com.devpal.newbase.di;

import android.content.Context;

import androidx.room.Room; // استيراد Room

import com.devpal.newbase.Network.ApiHelper;
import com.devpal.newbase.Network.ApiService;
import com.devpal.newbase.Network.ApiClient;
import com.devpal.newbase.database.AppDatabase;
import com.devpal.newbase.database.dao.UserDao;
import com.devpal.newbase.database.dao.NotificationDao;
import com.devpal.newbase.utils.SessionManager; // استيراد SessionManager
import com.devpal.newbase.utils.AppLogger; // استيراد AppLogger

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

/**
 * AppModule: Hilt Module لتوفير التبعيات على مستوى Singleton (التطبيق بأكمله).
 * تُعرف هنا كيفية إنشاء مثيلات للخدمات والمساعدات وقواعد البيانات التي ستستخدم مرة واحدة.
 */
@Module
@InstallIn(SingletonComponent.class)
public class AppModule {

    private static final String TAG = "AppModule";

    // يوفر سياق التطبيق (Application Context)
    @Provides
    @Singleton
    public Context provideApplicationContext(@ApplicationContext Context context) {
        AppLogger.d(TAG, "Providing Application Context.");
        return context;
    }

    // يوفر مثيل ApiService (Retrofit)
    @Provides
    @Singleton
    public ApiService provideApiService() {
        AppLogger.d(TAG, "Providing ApiService.");
        return ApiClient.getClient().create(ApiService.class);
    }

    // يوفر مثيل ApiHelper
    // Hilt سيعرف كيف يوفر ApiService و Context لأننا قمنا بتعريفهم أعلاه.
    @Provides
    @Singleton
    public ApiHelper provideApiHelper(ApiService apiService, @ApplicationContext Context context) {
        AppLogger.d(TAG, "Providing ApiHelper.");
        return new ApiHelper(apiService, context);
    }

    // يوفر مثيل قاعدة البيانات Room
    @Provides
    @Singleton
    public AppDatabase provideAppDatabase(@ApplicationContext Context context) {
        AppLogger.d(TAG, "Providing AppDatabase.");
        // بناء قاعدة البيانات
        return Room.databaseBuilder(
                        context,
                        AppDatabase.class,
                        "devpal_app_database" // اسم قاعدة البيانات
                )
                .fallbackToDestructiveMigration() // استراتيجية الترحيل (لأغراض التطوير، تُحذف البيانات عند التغيير)
                .build();
    }

    // يوفر مثيل UserDao من قاعدة البيانات
    @Provides
    @Singleton
    public UserDao provideUserDao(AppDatabase appDatabase) {
        AppLogger.d(TAG, "Providing UserDao.");
        return appDatabase.userDao();
    }

    // يوفر مثيل NotificationDao من قاعدة البيانات
    @Provides
    @Singleton
    public NotificationDao provideNotificationDao(AppDatabase appDatabase) {
        AppLogger.d(TAG, "Providing NotificationDao.");
        return appDatabase.notificationDao();
    }

    // يوفر مثيل SessionManager
    @Provides
    @Singleton
    public SessionManager provideSessionManager(@ApplicationContext Context context) {
        AppLogger.d(TAG, "Providing SessionManager.");
        return new SessionManager(context);
    }

    // يمكنك إضافة المزيد من دوال @Provides هنا لتبعيات أخرى
}
