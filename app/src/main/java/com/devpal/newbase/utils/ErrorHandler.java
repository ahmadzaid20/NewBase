package com.devpal.newbase.utils;

import android.content.Context;
import android.widget.Toast;

import com.devpal.newbase.exceptions.ApiException;
import com.devpal.newbase.exceptions.NoInternetException;
import com.devpal.newbase.response.ErrorResponse; // استيراد ErrorResponse
import com.google.gson.Gson; // لاستخدام Gson
import com.google.gson.JsonSyntaxException; // لمعالجة أخطاء تحليل JSON

/**
 * ErrorHandler: كلاس مساعد لتوحيد معالجة الأخطاء وعرض رسائل مناسبة للمستخدم.
 * يقوم بتحويل الاستثناءات المختلفة (مثل أخطاء الشبكة، أخطاء API) إلى رسائل نصية قابلة للعرض.
 */
public final class ErrorHandler {

    private static final String TAG = "ErrorHandler";

    /**
     * تُظهر رسالة خطأ للمستخدم بناءً على الاستثناء الذي حدث.
     * يجب استدعاء هذه الدالة من الـ UI (Activity/Fragment) لضمان الوصول إلى السياق.
     *
     * @param context سياق التطبيق أو الـ Activity لعرض الـ Toast.
     * @param throwable الاستثناء الذي حدث.
     */
    public static void handleError(Context context, Throwable throwable) {
        String errorMessage = getErrorMessage(throwable);
        AppLogger.e(TAG, "Handling error: " + errorMessage, throwable); // سجل الخطأ الكامل
        Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show(); // اعرض الرسالة للمستخدم
    }

    /**
     * يحصل على رسالة خطأ قابلة للعرض من الاستثناء.
     * @param throwable الاستثناء الذي حدث.
     * @return رسالة خطأ نصية مناسبة للعرض للمستخدم.
     */
    public static String getErrorMessage(Throwable throwable) {
        if (throwable instanceof NoInternetException) {
            return "لا يوجد اتصال بالإنترنت. يرجى التحقق من اتصالك والمحاولة مرة أخرى.";
        } else if (throwable instanceof ApiException) {
            ApiException apiException = (ApiException) throwable;
            // حاول تحليل errorBody إذا كان موجودًا
            if (apiException.getErrorBody() != null && !apiException.getErrorBody().isEmpty()) {
                try {
                    Gson gson = new Gson();
                    ErrorResponse errorResponse = gson.fromJson(apiException.getErrorBody(), ErrorResponse.class);
                    if (errorResponse != null && errorResponse.getMessage() != null && !errorResponse.getMessage().isEmpty()) {
                        return errorResponse.getMessage(); // استخدم رسالة الخطأ من جسم الاستجابة
                    }
                } catch (JsonSyntaxException e) {
                    AppLogger.e(TAG, "Error parsing API error body: " + e.getMessage());
                }
            }

            // إذا لم يتم تحليل جسم الخطأ أو لم يكن يحتوي على رسالة، استخدم رسالة ApiException أو رمز الحالة
            if (apiException.getMessage() != null && !apiException.getMessage().isEmpty()) {
                return apiException.getMessage();
            } else {
                // **تم التحديث هنا: استخدام getHttpStatusCode() بدلاً من getStatusCode()**
                return "خطأ في API: " + apiException.getHttpStatusCode();
            }
        } else if (throwable instanceof java.net.SocketTimeoutException) {
            return "انتهت مهلة الاتصال بالخادم. يرجى المحاولة لاحقًا.";
        } else if (throwable instanceof java.io.IOException) {
            // أخطاء IO عامة (غير متصلة بالإنترنت)
            return "حدث خطأ في الشبكة. يرجى التحقق من اتصالك.";
        } else if (throwable instanceof java.util.concurrent.TimeoutException) {
            return "تجاوزت العملية الحد الزمني. يرجى المحاولة لاحقًا.";
        } else {
            // لأي استثناءات أخرى غير متوقعة
            return "حدث خطأ غير متوقع. يرجى المحاولة لاحقًا.";
        }
    }

    // بناء كلاس ErrorHandler لا يمكن إنشاء مثيل منه (Non-instantiable)
    private ErrorHandler() {
        // منع إنشاء كائنات من هذا الكلاس
    }
}
