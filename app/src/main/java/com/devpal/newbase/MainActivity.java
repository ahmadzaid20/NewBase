// File: app/src/main/java/com/devpal/newbase/MainActivity.java
package com.devpal.newbase;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import dagger.hilt.android.AndroidEntryPoint; // استيراد AndroidEntryPoint

import javax.inject.Inject; // استيراد Inject

// AndroidEntryPoint يخبر Hilt بحقن التبعيات في هذه الفئة.
@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    // Hilt سيقوم بتوفير (حقن) كائن MyDependency هنا
    // تأكد أن MyDependency لديها مُنشئ (constructor) مُعلم بـ @Inject
    @Inject
    MyDependency myDependency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // تأكد من وجود activity_main.xml

        // الآن يمكنك استخدام myDependency
        myDependency.doSomething();
        Log.d(TAG, "MyDependency hash code: " + myDependency.hashCode());
    }
}
