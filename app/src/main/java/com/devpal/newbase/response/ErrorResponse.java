package com.devpal.newbase.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

/**
 * ErrorResponse: يمثل هيكل استجابات الأخطاء الأكثر تفصيلاً من الـ API.
 * يمكن أن يحتوي على رمز خطأ (code)، رسالة خطأ عامة (message/error_msg)،
 * وقائمة بأخطاء التحقق من الصحة (errors) إذا كانت المشكلة تتعلق بالبيانات المدخلة.
 */
public class ErrorResponse {

    @SerializedName("code")
    private int code; // رمز خطأ محدد من السيرفر (مثال: 1001 لخطأ التحقق، 1002 لخطأ في المصادقة)

    @SerializedName("message") // يمكن أن يكون "error_msg" أو "error" حسب الـ API الخاص بك
    private String message;   // رسالة خطأ عامة من السيرفر

    // حقل اختياري لأخطاء التحقق من الصحة (Validation errors)
    // Map<String, List<String>> تعني:
    // Key: اسم الحقل الذي حدث فيه الخطأ (مثال: "email", "password")
    // Value: قائمة بالرسائل الخطأ لهذا الحقل (مثال: ["البريد الإلكتروني غير صالح", "مطلوب"])
    @SerializedName("errors")
    private Map<String, List<String>> errors; // لأخطاء التحقق من الصحة (validation errors)

    // مُنشئ فارغ مطلوب لـ Gson
    public ErrorResponse() {
    }

    // --- Constructor ---
    public ErrorResponse(int code, String message, Map<String, List<String>> errors) {
        this.code = code;
        this.message = message;
        this.errors = errors;
    }

    // --- Getters ---
    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Map<String, List<String>> getErrors() {
        return errors;
    }

    // --- Setters (اختياري) ---
    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setErrors(Map<String, List<String>> errors) {
        this.errors = errors;
    }

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", errors=" + errors +
                '}';
    }
}
