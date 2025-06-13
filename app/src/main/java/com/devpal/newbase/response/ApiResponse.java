package com.devpal.newbase.response;

import com.google.gson.annotations.SerializedName;
import androidx.annotation.Nullable;

/**
 * ApiResponse: نموذج استجابة عام من الـ API.
 * يُستخدم لتغليف البيانات الفعلية التي تم إرجاعها من الخادم، بالإضافة إلى معلومات الحالة والرسائل.
 * @param <T>: النوع العام للبيانات التي تحتويها الاستجابة (مثل User, List<Notification>).
 */
public class ApiResponse<T> {

    @SerializedName("status")
    private String status; // حالة الاستجابة (مثال: "success", "error")

    @SerializedName("message")
    private String message; // رسالة وصفية من الخادم

    @SerializedName("data")
    @Nullable // البيانات يمكن أن تكون null في حالة الخطأ أو إذا لم يتم إرجاع بيانات
    private T data; // البيانات الفعلية التي تم إرجاعها

    // مُنشئ فارغ مطلوب لـ Gson (Room لا يحتاجه هنا لأنه ليس Entity)
    public ApiResponse() {
    }

    /**
     * مُنشئ لإنشاء استجابة API.
     * @param status حالة الاستجابة (مثال: "success", "error").
     * @param message رسالة وصفية من الخادم.
     * @param data البيانات الفعلية التي تم إرجاعها.
     */
    public ApiResponse(String status, String message, @Nullable T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    // Getters and Setters

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Nullable
    public T getData() {
        return data;
    }

    public void setData(@Nullable T data) {
        this.data = data;
    }

    /**
     * دالة مساعدة للتحقق مما إذا كانت الاستجابة ناجحة.
     * تُعتبر ناجحة إذا كانت قيمة حقل "status" هي "success" (غير حساسة لحالة الأحرف).
     * @return true إذا كانت الاستجابة ناجحة، false بخلاف ذلك.
     */
    public boolean isSuccess() {
        return "success".equalsIgnoreCase(status);
    }

    @Override
    public String toString() {
        return "ApiResponse{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    // --- Static Factory Methods for convenience ---

    /**
     * ينشئ مثيل ApiResponse لـ استجابة ناجحة.
     * @param data البيانات التي تم إرجاعها.
     * @param <T> نوع البيانات.
     * @return ApiResponse ناجح.
     */
    public static <T> ApiResponse<T> success(@Nullable T data) {
        return new ApiResponse<>("success", "Operation successful", data);
    }

    /**
     * ينشئ مثيل ApiResponse لـ استجابة ناجحة بدون بيانات.
     * @return ApiResponse ناجح.
     */
    public static <T> ApiResponse<T> success() {
        return new ApiResponse<>("success", "Operation successful", null);
    }

    /**
     * ينشئ مثيل ApiResponse لـ استجابة فاشلة.
     * @param message رسالة الخطأ.
     * @param <T> نوع البيانات (يكون null عادة).
     * @return ApiResponse فاشل.
     */
    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>("error", message, null);
    }

    /**
     * ينشئ مثيل ApiResponse لـ استجابة فاشلة مع رمز خطأ HTTP (مثلاً من HttpException).
     * @param httpStatusCode رمز حالة HTTP.
     * @param message رسالة الخطأ.
     * @param <T> نوع البيانات (يكون null عادة).
     * @return ApiResponse فاشل.
     */
    public static <T> ApiResponse<T> error(int httpStatusCode, String message) {
        // يمكنك تخصيص رسالة بناءً على رمز الحالة هنا إذا أردت
        return new ApiResponse<>("error", "Error " + httpStatusCode + ": " + message, null);
    }
}
