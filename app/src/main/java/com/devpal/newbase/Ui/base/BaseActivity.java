package com.devpal.newbase.Ui.base; // تم تحديث الحزمة

import android.app.ProgressDialog;
import android.content.Context; // قد لا تكون ضرورية هنا، ولكن من الجيد بقاؤها
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.devpal.newbase.utils.AppLogger; // تم تحديث الاستيراد

import dagger.hilt.android.AndroidEntryPoint;

/**
 * BaseActivity: الفئة الأساسية لجميع Activities في التطبيق.
 * توفر وظائف مشتركة مثل إظهار/إخفاء Progress Dialog، عرض رسائل Toast،
 * والتعامل مع تهيئة Dagger Hilt.
 *
 * @AndroidEntryPoint: يخبر Dagger Hilt بضرورة حقن التبعيات في هذه الـ Activity.
 * يجب أن تكون موجودة في كل Activity أو Fragment تحتاج لحقن التبعيات.
 */
@AndroidEntryPoint
public abstract class BaseActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // يمكنك إضافة أي منطق مشترك آخر هنا في كل مرة يتم فيها إنشاء Activity
        AppLogger.d("BaseActivity", "onCreate: " + getClass().getSimpleName());
    }

    /**
     * تُظهر Progress Dialog مع رسالة اختيارية.
     * @param message الرسالة التي تظهر في الـ Progress Dialog. إذا كانت فارغة، سيتم عرض رسالة افتراضية.
     */
    public void showProgressDialog(String message) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setCancelable(false); // لا يمكن إلغاؤه بالضغط خارجًا
            progressDialog.setCanceledOnTouchOutside(false); // لا يمكن إلغاؤه باللمس خارجًا
        }
        progressDialog.setMessage(message != null && !message.isEmpty() ? message : "الرجاء الانتظار...");
        if (!progressDialog.isShowing()) {
            // إضافة try-catch لتجنب WindowLeakedException إذا كانت الـ Activity قد تم تدميرها
            try {
                progressDialog.show();
            } catch (Exception e) {
                AppLogger.e("BaseActivity", "Error showing progress dialog: " + e.getMessage());
            }
        }
    }

    /**
     * تُخفي Progress Dialog إذا كان مرئيًا.
     */
    public void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            // إضافة try-catch لتجنب IllegalArgumentException إذا كانت الـ dialog غير مرفقة
            try {
                progressDialog.dismiss();
            } catch (Exception e) {
                AppLogger.e("BaseActivity", "Error hiding progress dialog: " + e.getMessage());
            }
        }
        progressDialog = null; // تأكد من تعيينه لـ null لتجنب تسرب الذاكرة
    }

    /**
     * تُظهر رسالة Toast قصيرة للمستخدم.
     * @param message الرسالة المراد عرضها.
     */
    public void showToast(String message) {
        if (message != null && !message.isEmpty()) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        } else {
            AppLogger.w("BaseActivity", "Attempted to show empty Toast message.");
        }
    }

    /**
     * تُظهر رسالة Toast طويلة للمستخدم.
     * @param message الرسالة المراد عرضها.
     */
    public void showLongToast(String message) {
        if (message != null && !message.isEmpty()) {
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        } else {
            AppLogger.w("BaseActivity", "Attempted to show empty long Toast message.");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // تأكد من إخفاء الـ dialog لتجنب تسرب الذاكرة
        if (progressDialog != null && progressDialog.isShowing()) {
            try {
                progressDialog.dismiss();
            } catch (Exception e) {
                AppLogger.e("BaseActivity", "Error dismissing progress dialog in onDestroy: " + e.getMessage());
            }
        }
        progressDialog = null; // تأكد من تعيينه لـ null
        AppLogger.d("BaseActivity", "onDestroy: " + getClass().getSimpleName());
    }

    // يمكنك إضافة المزيد من الدوال المساعدة هنا حسب الحاجة (مثل التحقق من الاتصال بالإنترنت)
}
