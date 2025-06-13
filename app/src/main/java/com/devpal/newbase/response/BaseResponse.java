package com.devpal.newbase.response;

import com.google.gson.annotations.SerializedName;

/**
 * BaseResponse: يمثل الهيكل الأساسي لأي استجابة من الـ API.
 * يحتوي على حقول مشتركة مثل status و message.
 * جميع استجابات الـ API الأخرى يمكن أن ترث منه أو تحتوي عليه.
 */
public class BaseResponse {

    @SerializedName("status") // يشير إلى الاسم الذي سيتم تحويله من JSON
    private boolean status;   // true للنجاح، false للفشل (مثال: يمكن أن يكون int code)

    @SerializedName("message")
    private String message;   // رسالة توضيحية من السيرفر (مثال: "تم تسجيل الدخول بنجاح")

    // --- Constructor ---
    public BaseResponse(boolean status, String message) {
        this.status = status;
        this.message = message;
    }

    // --- Getters ---
    public boolean getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    // --- Setters (اختياري، غالبًا لا تحتاجها في كائنات الاستجابة) ---
    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "status=" + status +
                ", message='" + message + '\'' +
                '}';
    }
}
