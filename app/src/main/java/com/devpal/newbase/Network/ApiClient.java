package com.devpal.newbase.Network;

//import com.devpal.newbase.BuildConfig;
import com.devpal.newbase.utils.AppLogger; // سيتم إنشاء هذا لاحقًا

import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * ApiClient: كلاس يقوم بتهيئة وتوفير مثيل واحد (Singleton) لـ Retrofit و OkHttpClient.
 * مسؤول عن إعدادات الشبكة مثل BASE_URL، مهلات الاتصال، والمُعترضات (Interceptors).
 */
public class ApiClient {

    private static final String TAG = "ApiClient";
    private static Retrofit retrofit = null;

    /**
     * يحصل على مثيل Retrofit.
     * إذا لم يكن هناك مثيل موجود، يقوم بإنشائه وتهيئته.
     *
     * @return مثيل Retrofit.
     */
    public static Retrofit getClient() {
        if (retrofit == null) {
            // 1. إعداد HttpLoggingInterceptor: لتسجيل طلبات واستجابات الشبكة.
            // يكون مفيدًا جدًا في وضع التطوير (DEBUG) لتتبع مشاكل الشبكة.
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            // تعيين مستوى التسجيل للجسم الكامل للطلب والاستجابة
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            // 2. إعداد OkHttpClient: العميل الذي يقوم بإنشاء طلبات HTTP.
            // هنا نضبط مهلات الاتصال ونضيف المُعترضات.
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS) // مهلة الاتصال بالخادم
                    .readTimeout(30, TimeUnit.SECONDS)    // مهلة قراءة البيانات من الخادم
                    .writeTimeout(30, TimeUnit.SECONDS)   // مهلة إرسال البيانات إلى الخادم
                    .addInterceptor(loggingInterceptor)   // إضافة مُعترض التسجيل
                    // .addInterceptor(createAuthInterceptor()) // يمكن إضافة مُعترض للمصادقة لاحقًا
                    .build();

            // 3. بناء مثيل Retrofit: يربط OkHttpClient بـ BASE_URL ومعالجات البيانات.
            retrofit = new Retrofit.Builder()
                    .baseUrl(Endpoints.BASE_URL) // استخدام BASE_URL من كلاس Endpoints
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // دعم RxJava
                    .addConverterFactory(GsonConverterFactory.create()) // دعم تحويل JSON باستخدام Gson
                    .client(okHttpClient) // ربط OkHttpClient المُهيأ
                    .build();

            AppLogger.d(TAG, "Retrofit client initialized with Base URL: " + Endpoints.BASE_URL);

        }
        return retrofit;
    }

    /**
     * (اختياري) دالة لإنشاء مُعترض للمصادقة.
     * يمكن استخدام هذا المُعترض لإضافة رموز المصادقة (مثل Bearer Token) إلى كل طلب.
     * ستحتاج إلى الوصول إلى SessionManager هنا.
     */
    private static Interceptor createAuthInterceptor() {
        return chain -> {
            // يمكن هنا الحصول على Token المصادقة من SessionManager
            // String authToken = SessionManager.getToken(); // تتطلب حقن SessionManager
            AppLogger.d(TAG, "Adding Authorization Header (Placeholder)");

            // بناء الطلب الجديد مع إضافة رأس المصادقة
            okhttp3.Request.Builder requestBuilder = chain.request().newBuilder();
            // if (authToken != null) {
            //     requestBuilder.addHeader("Authorization", "Bearer " + authToken);
            // }

            return chain.proceed(requestBuilder.build());
        };
    }

    // بناء كلاس ApiClient لا يمكن إنشاء مثيل منه (Non-instantiable)
    private ApiClient() {
        // منع إنشاء كائنات من هذا الكلاس
    }
}
