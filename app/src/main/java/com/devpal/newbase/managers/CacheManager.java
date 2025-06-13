package com.devpal.newbase.managers;

import android.content.Context;
import com.devpal.newbase.utils.AppLogger;

import java.io.File;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * CacheManager: كلاس لإدارة التخزين المؤقت للبيانات.
 * يسمح بتخزين واسترجاع الملفات أو البيانات مؤقتًا لتسريع الوصول وتقليل استهلاك الشبكة.
 * يستخدم Dagger Hilt لحقن Context.
 */
@Singleton
public class CacheManager {

    private static final String TAG = "CacheManager";
    private static final String CACHE_DIR_NAME = "app_cache"; // اسم مجلد التخزين المؤقت

    private final Context context;
    private final File cacheDir;

    /**
     * مُنشئ (Constructor) يقوم بحقن السياق (Context) بواسطة Dagger Hilt.
     * @param context سياق التطبيق (Application Context) المحقون بواسطة Hilt.
     */
    @Inject
    public CacheManager(Context context) {
        this.context = context;
        // إنشاء مجلد التخزين المؤقت الخاص بالتطبيق
        this.cacheDir = new File(context.getCacheDir(), CACHE_DIR_NAME);
        if (!cacheDir.exists()) {
            cacheDir.mkdirs(); // إنشاء المجلدات إذا لم تكن موجودة
            AppLogger.d(TAG, "Cache directory created: " + cacheDir.getAbsolutePath());
        } else {
            AppLogger.d(TAG, "Cache directory already exists: " + cacheDir.getAbsolutePath());
        }
    }

    /**
     * يحفظ نصًا معينًا في ملف داخل مجلد التخزين المؤقت.
     * @param fileName اسم الملف الذي سيتم حفظ النص فيه.
     * @param content النص المراد حفظه.
     * @return true إذا تم الحفظ بنجاح، false بخلاف ذلك.
     */
    public boolean saveTextToCache(String fileName, String content) {
        File file = new File(cacheDir, fileName);
        try {
            java.io.FileWriter writer = new java.io.FileWriter(file);
            writer.append(content);
            writer.flush();
            writer.close();
            AppLogger.d(TAG, "Text saved to cache: " + fileName);
            return true;
        } catch (java.io.IOException e) {
            AppLogger.e(TAG, "Error saving text to cache: " + fileName, e);
            return false;
        }
    }

    /**
     * يسترجع نصًا من ملف داخل مجلد التخزين المؤقت.
     * @param fileName اسم الملف الذي سيتم استرجاع النص منه.
     * @return النص المسترجع، أو null إذا لم يتم العثور على الملف أو حدث خطأ.
     */
    public String readTextFromCache(String fileName) {
        File file = new File(cacheDir, fileName);
        if (!file.exists()) {
            AppLogger.d(TAG, "File not found in cache: " + fileName);
            return null;
        }
        try {
            java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(file));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            reader.close();
            String content = stringBuilder.toString().trim();
            AppLogger.d(TAG, "Text read from cache: " + fileName);
            return content;
        } catch (java.io.IOException e) {
            AppLogger.e(TAG, "Error reading text from cache: " + fileName, e);
            return null;
        }
    }

    /**
     * يحذف ملفًا معينًا من مجلد التخزين المؤقت.
     * @param fileName اسم الملف المراد حذفه.
     * @return true إذا تم الحذف بنجاح، false بخلاف ذلك.
     */
    public boolean deleteFromCache(String fileName) {
        File file = new File(cacheDir, fileName);
        if (file.exists()) {
            boolean deleted = file.delete();
            AppLogger.d(TAG, "File deleted from cache: " + fileName + " - " + deleted);
            return deleted;
        }
        AppLogger.d(TAG, "File not found for deletion in cache: " + fileName);
        return false;
    }

    /**
     * يمسح جميع الملفات من مجلد التخزين المؤقت.
     */
    public void clearCache() {
        if (cacheDir.exists()) {
            File[] files = cacheDir.listFiles();
            if (files != null) {
                for (File file : files) {
                    file.delete();
                }
            }
            AppLogger.d(TAG, "Cache cleared.");
        }
    }

    /**
     * يحصل على المسار المطلق لمجلد التخزين المؤقت.
     * @return مسار مجلد التخزين المؤقت.
     */
    public String getCacheDirPath() {
        return cacheDir.getAbsolutePath();
    }
}
