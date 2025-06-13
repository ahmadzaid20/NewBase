package com.devpal.newbase.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Validator: كلاس مساعد يحتوي على دوال للتحقق من صحة المدخلات.
 * يساعد في ضمان أن البيانات التي يتم إدخالها بواسطة المستخدم تتبع التنسيقات والقواعد المطلوبة.
 */
public final class Validator {

    private static final String TAG = "Validator";

    // Regular Expression (Regex) للتحقق من صحة تنسيق البريد الإلكتروني
    private static final String EMAIL_REGEX =
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    // Regular Expression (Regex) للتحقق من قوة كلمة المرور (مثال: 8 أحرف على الأقل، حرف كبير، حرف صغير، رقم، رمز خاص)
    // يمكن تعديل هذا Regex حسب متطلبات قوة كلمة المرور الخاصة بك.
    private static final String PASSWORD_REGEX =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(PASSWORD_REGEX);


    /**
     * تتحقق مما إذا كان البريد الإلكتروني المدخل صالحًا (يتبع تنسيق البريد الإلكتروني).
     * @param email البريد الإلكتروني المراد التحقق منه.
     * @return true إذا كان البريد الإلكتروني صالحًا، false بخلاف ذلك.
     */
    public static boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            AppLogger.d(TAG, "Email is null or empty.");
            return false;
        }
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        boolean isValid = matcher.matches();
        AppLogger.d(TAG, "Email '" + email + "' is valid: " + isValid);
        return isValid;
    }

    /**
     * تتحقق مما إذا كانت كلمة المرور المدخلة قوية بما يكفي (تتبع معايير معينة).
     * المعايير الحالية (يمكن تعديلها في PASSWORD_REGEX):
     * - 8 أحرف على الأقل (.{8,})
     * - حرف رقمي واحد على الأقل (?=.*[0-9])
     * - حرف صغير واحد على الأقل (?=.*[a-z])
     * - حرف كبير واحد على الأقل (?=.*[A-Z])
     * - رمز خاص واحد على الأقل (?=.*[@#$%^&+=])
     * - لا تحتوي على مسافات بيضاء (?=\\S+$)
     *
     * @param password كلمة المرور المراد التحقق منها.
     * @return true إذا كانت كلمة المرور قوية، false بخلاف ذلك.
     */
    public static boolean isValidPassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            AppLogger.d(TAG, "Password is null or empty.");
            return false;
        }
        Matcher matcher = PASSWORD_PATTERN.matcher(password);
        boolean isValid = matcher.matches();
        AppLogger.d(TAG, "Password validity: " + isValid);
        return isValid;
    }

    /**
     * تتحقق مما إذا كان النص المدخل غير فارغ.
     * @param text النص المراد التحقق منه.
     * @return true إذا كان النص غير فارغ أو null، false بخلاف ذلك.
     */
    public static boolean isNotEmpty(String text) {
        boolean notEmpty = text != null && !text.trim().isEmpty();
        AppLogger.d(TAG, "Text is not empty: " + notEmpty);
        return notEmpty;
    }

    /**
     * تتحقق مما إذا كان طول النص المدخل ضمن نطاق معين.
     * @param text النص المراد التحقق منه.
     * @param minLength الحد الأدنى للطول المسموح به.
     * @param maxLength الحد الأقصى للطول المسموح به.
     * @return true إذا كان الطول ضمن النطاق، false بخلاف ذلك.
     */
    public static boolean isLengthBetween(String text, int minLength, int maxLength) {
        if (text == null) {
            AppLogger.d(TAG, "Text is null for length check.");
            return false;
        }
        int length = text.trim().length();
        boolean isValid = length >= minLength && length <= maxLength;
        AppLogger.d(TAG, "Text length (" + length + ") is between " + minLength + " and " + maxLength + ": " + isValid);
        return isValid;
    }


    // بناء كلاس Validator لا يمكن إنشاء مثيل منه (Non-instantiable)
    private Validator() {
        // منع إنشاء كائنات من هذا الكلاس
    }
}
