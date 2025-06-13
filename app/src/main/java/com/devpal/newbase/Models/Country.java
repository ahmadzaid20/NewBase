package com.devpal.newbase.Models;

import java.io.Serializable; // لجعل الكائن قابلاً للمرور بين الـ Activities/Fragments

/**
 * Country: نموذج بيانات لتمثيل معلومات الدولة.
 * يحتوي على اسم الدولة، رمز الاتصال الدولي، والحد الأقصى لعدد خانات الرقم المحلي.
 */
public class Country implements Serializable {
    private String name;        // اسم الدولة (مثال: "المملكة الأردنية الهاشمية")
    private String nameEn;      // اسم الدولة بالإنجليزية للبحث (مثال: "Jordan")
    private String code;        // رمز الاتصال الدولي (مثال: "+962")
    private int maxLength;      // الحد الأقصى لعدد خانات الرقم المحلي (مثال: 9 لأرقام الأردن)

    public Country(String name, String nameEn, String code, int maxLength) {
        this.name = name;
        this.nameEn = nameEn;
        this.code = code;
        this.maxLength = maxLength;
    }

    // getters (الوصول للبيانات)
    public String getName() {
        return name;
    }

    public String getNameEn() {
        return nameEn;
    }

    public String getCode() {
        return code;
    }

    public int getMaxLength() {
        return maxLength;
    }

    // يمكنك إضافة setters إذا كنت بحاجة لتغيير هذه القيم بعد الإنشاء
    // public void setName(String name) { this.name = name; }
    // public void setCode(String code) { this.code = code; }
    // public void setMaxLength(int maxLength) { this.maxLength = maxLength; }

    @Override
    public String toString() {
        return name + " (" + code + ")";
    }
}
