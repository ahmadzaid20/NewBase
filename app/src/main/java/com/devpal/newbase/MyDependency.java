package com.devpal.newbase;

import android.util.Log;
import javax.inject.Inject; // استيراد Inject

// @Inject في المُنشئ يخبر Hilt كيف ينشئ كائنًا من هذه الفئة.
// إذا كان لا يتطلب أي تبعيات أخرى، فسيقوم Hilt بتوفيره تلقائيا.
public class MyDependency {

    private static final String TAG = "MyDependency";

    @Inject
    public MyDependency() {
        Log.d(TAG, "MyDependency instance created!");
    }

    public void doSomething() {
        Log.d(TAG, "Doing something useful with MyDependency.");
    }
}
