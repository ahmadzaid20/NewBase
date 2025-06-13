package com.devpal.newbase.managers;

import android.content.Context;
import android.os.Environment; // لإدارة التخزين الخارجي
import com.devpal.newbase.utils.AppLogger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * FileManager: كلاس لإدارة عمليات الملفات (القراءة والكتابة) داخل التطبيق أو على التخزين الخارجي.
 * يتطلب أذونات التخزين (READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE) في AndroidManifest.xml.
 * يستخدم Dagger Hilt لحقن Context.
 */
@Singleton
public class FileManager {

    private static final String TAG = "FileManager";
    private final Context context;

    /**
     * مُنشئ (Constructor) يقوم بحقن السياق (Context) بواسطة Dagger Hilt.
     * @param context سياق التطبيق (Application Context) المحقون بواسطة Hilt.
     */
    @Inject
    public FileManager(Context context) {
        this.context = context;
        AppLogger.d(TAG, "FileManager initialized.");
    }

    /**
     * يحفظ نصًا معينًا في ملف داخل التخزين الخاص بالتطبيق (Internal Storage).
     * هذه الملفات خاصة بالتطبيق ولا يمكن لتطبيقات أخرى الوصول إليها.
     * @param fileName اسم الملف الذي سيتم حفظ النص فيه.
     * @param content النص المراد حفظه.
     * @return true إذا تم الحفظ بنجاح، false بخلاف ذلك.
     */
    public boolean saveTextToInternalStorage(String fileName, String content) {
        try (FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE)) {
            fos.write(content.getBytes());
            AppLogger.d(TAG, "Text saved to internal storage: " + fileName);
            return true;
        } catch (IOException e) {
            AppLogger.e(TAG, "Error saving text to internal storage: " + fileName, e);
            return false;
        }
    }

    /**
     * يسترجع نصًا من ملف داخل التخزين الخاص بالتطبيق (Internal Storage).
     * @param fileName اسم الملف الذي سيتم استرجاع النص منه.
     * @return النص المسترجع، أو null إذا لم يتم العثور على الملف أو حدث خطأ.
     */
    public String readTextFromInternalStorage(String fileName) {
        try (FileInputStream fis = context.openFileInput(fileName)) {
            StringBuilder stringBuilder = new StringBuilder();
            int content;
            while ((content = fis.read()) != -1) {
                stringBuilder.append((char) content);
            }
            String result = stringBuilder.toString();
            AppLogger.d(TAG, "Text read from internal storage: " + fileName);
            return result;
        } catch (IOException e) {
            AppLogger.e(TAG, "Error reading text from internal storage: " + fileName, e);
            return null;
        }
    }

    /**
     * يحفظ نصًا معينًا في ملف في التخزين المشترك (External Storage / Downloads folder).
     * يتطلب إذن WRITE_EXTERNAL_STORAGE في AndroidManifest.
     * @param fileName اسم الملف الذي سيتم حفظ النص فيه.
     * @param content النص المراد حفظه.
     * @return true إذا تم الحفظ بنجاح، false بخلاف ذلك.
     */
    public boolean saveTextToDownloads(String fileName, String content) {
        if (!isExternalStorageWritable()) {
            AppLogger.e(TAG, "External storage not writable.");
            return false;
        }

        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File file = new File(path, fileName);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(content.getBytes());
            AppLogger.d(TAG, "Text saved to Downloads: " + fileName);
            return true;
        } catch (IOException e) {
            AppLogger.e(TAG, "Error saving text to Downloads: " + fileName, e);
            return false;
        }
    }

    /**
     * يسترجع نصًا من ملف في التخزين المشترك (External Storage / Downloads folder).
     * يتطلب إذن READ_EXTERNAL_STORAGE في AndroidManifest.
     * @param fileName اسم الملف الذي سيتم استرجاع النص منه.
     * @return النص المسترجع، أو null إذا لم يتم العثور على الملف أو حدث خطأ.
     */
    public String readTextFromDownloads(String fileName) {
        if (!isExternalStorageReadable()) {
            AppLogger.e(TAG, "External storage not readable.");
            return null;
        }

        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File file = new File(path, fileName);
        if (!file.exists()) {
            AppLogger.d(TAG, "File not found in Downloads: " + fileName);
            return null;
        }

        try (FileInputStream fis = new FileInputStream(file)) {
            StringBuilder stringBuilder = new StringBuilder();
            int content;
            while ((content = fis.read()) != -1) {
                stringBuilder.append((char) content);
            }
            String result = stringBuilder.toString();
            AppLogger.d(TAG, "Text read from Downloads: " + fileName);
            return result;
        } catch (IOException e) {
            AppLogger.e(TAG, "Error reading text from Downloads: " + fileName, e);
            return null;
        }
    }

    /**
     * يتحقق مما إذا كان التخزين الخارجي متاحًا للكتابة.
     * @return true إذا كان متاحًا للكتابة، false بخلاف ذلك.
     */
    public static boolean isExternalStorageWritable() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    /**
     * يتحقق مما إذا كان التخزين الخارجي متاحًا للقراءة.
     * @return true إذا كان متاحًا للقراءة، false بخلاف ذلك.
     */
    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
    }
}
