package com.devpal.newbase.exceptions;

/**
 * ApiException: كلاس استثناء مخصص لتمثيل الأخطاء التي تحدث أثناء التفاعل مع API.
 * يمكن أن يحتوي على رسالة الخطأ ورمز حالة HTTP (إذا كان متاحًا) لتوفير تفاصيل أكثر.
 */
public class ApiException extends RuntimeException {

    private int httpStatusCode; // رمز حالة HTTP مثل 401, 404, 500
    private String errorBody; // جسم الخطأ الخام (JSON) إذا كان متاحًا

    /**
     * ينشئ مثيل جديد من ApiException برسالة خطأ فقط.
     * @param message رسالة الخطأ.
     */
    public ApiException(String message) {
        super(message);
    }

    /**
     * ينشئ مثيل جديد من ApiException برسالة خطأ وسبب أساسي (Throwable).
     * @param message رسالة الخطأ.
     * @param cause السبب الأساسي للاستثناء.
     */
    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * ينشئ مثيل جديد من ApiException بمعلومات أكثر تفصيلاً لخطأ API.
     * @param message رسالة الخطأ.
     * @param httpStatusCode رمز حالة HTTP.
     * @param errorBody جسم الخطأ الخام (استجابة الخطأ من السيرفر).
     */
    public ApiException(String message, int httpStatusCode, String errorBody) {
        super(message);
        this.httpStatusCode = httpStatusCode;
        this.errorBody = errorBody;
    }

    /**
     * يحصل على رمز حالة HTTP المرتبط بالخطأ.
     * @return رمز حالة HTTP.
     */
    public int getHttpStatusCode() {
        return httpStatusCode;
    }

    /**
     * يحصل على جسم الخطأ الخام (JSON) من استجابة السيرفر.
     * @return جسم الخطأ الخام.
     */
    public String getErrorBody() {
        return errorBody;
    }

    // يمكنك إضافة دوال مساعدة إضافية هنا إذا أردت تحليل errorBody إلى كائن محدد (مثل ErrorResponse)
    // على سبيل المثال:
    /*
    public ErrorResponse getParsedErrorResponse() {
        if (errorBody != null && !errorBody.isEmpty()) {
            // هنا تحتاج إلى استخدام مكتبة مثل Gson لتحليل JSON
            // Gson gson = new Gson();
            // return gson.fromJson(errorBody, ErrorResponse.class);
        }
        return null;
    }
    */
}
