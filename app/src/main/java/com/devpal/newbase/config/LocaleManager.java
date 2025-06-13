package com.devpal.newbase.config;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.preference.PreferenceManager; // تم استخدام PreferenceManager لتخزين اللغة المختارة
import com.devpal.newbase.utils.AppLogger; // استيراد AppLogger

import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * LocaleManager: كلاس لإدارة لغة التطبيق.
 * يسمح بتغيير لغة الواجهة ديناميكيًا وحفظ اللغة المفضلة للمستخدم في SharedPreferences.
 * يستخدم Dagger Hilt لحقن Context.
 */
@Singleton
public class LocaleManager {

    private static final String TAG = "LocaleManager";
    private static final String LANGUAGE_KEY = "language_key"; // مفتاح لتخزين اللغة المفضلة

    // اللغات المدعومة
    public static final String LANGUAGE_ENGLISH = "en";
    public static final String LANGUAGE_ARABIC = "ar";
    // يمكن إضافة المزيد من اللغات هنا

    private final Context context;

    @Inject
    public LocaleManager(Context context) {
        this.context = context;
        AppLogger.d(TAG, "LocaleManager initialized.");
    }

    /**
     * يحصل على اللغة المفضلة المخزنة في SharedPreferences.
     * @return رمز اللغة (مثال: "en", "ar").
     */
    public String getLanguage() {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(LANGUAGE_KEY, LANGUAGE_ENGLISH); // الافتراضي هو الإنجليزية
    }

    /**
     * يغير لغة التطبيق وينقذ اللغة الجديدة كـ مفضلة.
     * @param language رمز اللغة المراد تعيينها (مثال: "en", "ar").
     * @return سياق (Context) جديد مع اللغة المحدثة.
     */
    public Context setNewLocale(Context c, String language) {
        persistLanguage(language); // حفظ اللغة الجديدة
        return updateResources(c, language); // تحديث الموارد في السياق الحالي
    }

    /**
     * يلتصق باللغة المفضلة عند بدء التطبيق.
     * يجب استدعاؤها في BaseApplication أو في بداية كل Activity لضمان تطبيق اللغة.
     * @param c سياق التطبيق.
     * @return سياق (Context) جديد مع اللغة المفضلة.
     */
    public Context attachBaseContext(Context c) {
        String lang = getLanguage(); // الحصول على اللغة المخزنة
        return updateResources(c, lang); // تطبيق اللغة على السياق
    }

    /**
     * يحفظ اللغة المفضلة للمستخدم في SharedPreferences.
     * @param language رمز اللغة.
     */
    private void persistLanguage(String language) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(LANGUAGE_KEY, language)
                .apply();
        AppLogger.d(TAG, "Language persisted: " + language);
    }

    /**
     * تقوم بتحديث موارد التطبيق لتعكس اللغة الجديدة.
     * @param context السياق الحالي.
     * @param language رمز اللغة.
     * @return سياق (Context) جديد أو محدث.
     */
    private Context updateResources(Context context, String language) {
        Locale locale = new Locale(language); // إنشاء كائن Locale
        Locale.setDefault(locale); // تعيين اللغة الافتراضية للجهاز

        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            configuration.setLocale(locale); // تعيين اللغة في Configuration API 24+
            context = context.createConfigurationContext(configuration);
        } else {
            configuration.locale = locale; // تعيين اللغة في Configuration API < 24
            resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        }
        AppLogger.d(TAG, "Resources updated for language: " + language);
        return context;
    }
}
