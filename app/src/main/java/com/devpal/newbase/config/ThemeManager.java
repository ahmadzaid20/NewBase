package com.devpal.newbase.config;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatDelegate; // لاستخدام AppCompatDelegate
import com.devpal.newbase.utils.AppLogger;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * ThemeManager: كلاس لإدارة سمة التطبيق (الوضع الليلي/النهاري).
 * يسمح بتبديل السمة ديناميكيًا وحفظ السمة المفضلة للمستخدم في SharedPreferences.
 * يستخدم Dagger Hilt لحقن Context.
 */
@Singleton
public class ThemeManager {

    private static final String TAG = "ThemeManager";
    private static final String PREF_NAME = "ThemePrefs"; // اسم ملف SharedPreferences للسمة
    private static final String KEY_THEME_MODE = "theme_mode"; // مفتاح لتخزين وضع السمة

    public static final int MODE_DAY = AppCompatDelegate.MODE_NIGHT_NO; // الوضع النهاري
    public static final int MODE_NIGHT = AppCompatDelegate.MODE_NIGHT_YES; // الوضع الليلي
    public static final int MODE_SYSTEM = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM; // اتبع إعدادات النظام

    private final SharedPreferences sharedPreferences;

    @Inject
    public ThemeManager(Context context) {
        this.sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        AppLogger.d(TAG, "ThemeManager initialized.");
    }

    /**
     * يحصل على وضع السمة الحالي المخزن.
     * @return وضع السمة (MODE_DAY, MODE_NIGHT, MODE_SYSTEM).
     */
    public int getThemeMode() {
        return sharedPreferences.getInt(KEY_THEME_MODE, MODE_SYSTEM); // الافتراضي هو اتباع النظام
    }

    /**
     * يُعيّن وضع سمة جديد للتطبيق ويحفظه.
     * @param themeMode الوضع الجديد (MODE_DAY, MODE_NIGHT, MODE_SYSTEM).
     */
    public void setThemeMode(int themeMode) {
        // حفظ الوضع الجديد في SharedPreferences
        sharedPreferences.edit().putInt(KEY_THEME_MODE, themeMode).apply();
        // تطبيق الوضع الجديد على مستوى التطبيق
        AppCompatDelegate.setDefaultNightMode(themeMode);
        AppLogger.d(TAG, "Theme mode set to: " + themeMode);
    }

    /**
     * يطبق السمة المفضلة المخزنة عند بدء التطبيق.
     * يجب استدعاؤها في BaseApplication أو في بداية كل Activity لضمان تطبيق السمة.
     */
    public void applySavedTheme() {
        AppCompatDelegate.setDefaultNightMode(getThemeMode());
        AppLogger.d(TAG, "Saved theme applied: " + getThemeMode());
    }
}
