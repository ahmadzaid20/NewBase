package com.devpal.newbase.exceptions;

import java.io.IOException;

/**
 * NoInternetException: كلاس استثناء مخصص لتمثيل حالة عدم توفر الاتصال بالإنترنت.
 * يرث من IOException لأنه يمثل مشكلة في الإدخال/الإخراج (الشبكة)
 * والتي تمنع إتمام عملية معينة.
 */
public class NoInternetException extends IOException {

    /**
     * ينشئ مثيل جديد من NoInternetException برسالة افتراضية.
     */
    public NoInternetException() {
        super("لا يوجد اتصال بالإنترنت. يرجى التحقق من اتصالك والمحاولة مرة أخرى.");
    }

    /**
     * ينشئ مثيل جديد من NoInternetException برسالة خطأ مخصصة.
     * @param message الرسالة المراد عرضها عند عدم توفر الإنترنت.
     */
    public NoInternetException(String message) {
        super(message);
    }

    /**
     * ينشئ مثيل جديد من NoInternetException برسالة خطأ مخصصة وسبب أساسي.
     * @param message الرسالة المراد عرضها.
     * @param cause السبب الأساسي للاستثناء.
     */
    public NoInternetException(String message, Throwable cause) {
        super(message, cause);
    }
}
