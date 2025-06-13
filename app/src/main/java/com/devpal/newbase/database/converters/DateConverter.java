package com.devpal.newbase.database.converters;

import androidx.room.TypeConverter;
import java.util.Date;

/**
 * DateConverter: كلاس محول أنواع (TypeConverter) لـ Room.
 * يستخدم لتحويل كائنات Date (Java) إلى Long (Unix timestamp) والعكس،
 * ليتم تخزينها في قاعدة بيانات Room.
 * هذا ضروري لأن Room لا يدعم كائنات Date مباشرة.
 */
public class DateConverter {

    /**
     * يحول قيمة Long (Unix timestamp) إلى كائن Date.
     * @param timestamp قيمة Long تمثل timestamp (بالمللي ثانية).
     * @return كائن Date، أو null إذا كانت قيمة timestamp null.
     */
    @TypeConverter
    public static Date toDate(Long timestamp) {
        return timestamp == null ? null : new Date(timestamp);
    }

    /**
     * يحول كائن Date إلى قيمة Long (Unix timestamp).
     * @param date كائن Date.
     * @return قيمة Long تمثل timestamp (بالمللي ثانية)، أو null إذا كان كائن Date null.
     */
    @TypeConverter
    public static Long toTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}
    