package com.devpal.newbase.Ui.activities; // تم تحديث الحزمة

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils; // لاستخدام TextUtils
import android.view.View; // لاستخدام View

import com.devpal.newbase.Models.User;
import com.devpal.newbase.Network.ApiHelper; // ApiHelper للحصول على البيانات
import com.devpal.newbase.R; // استيراد R لتحديد الموارد
import com.devpal.newbase.databinding.ActivityLoginBinding; // استيراد Data Binding (سيتم توليده تلقائيًا)
import com.devpal.newbase.exceptions.ApiException;
import com.devpal.newbase.Ui.base.BaseActivity; // استيراد BaseActivity
import com.devpal.newbase.utils.AppLogger; // استيراد AppLogger
import com.devpal.newbase.utils.ErrorHandler; // استيراد ErrorHandler
import com.devpal.newbase.utils.SessionManager; // استيراد SessionManager
import com.devpal.newbase.utils.Validator; // استيراد Validator

import javax.inject.Inject; // لاستخدام @Inject

import dagger.hilt.android.AndroidEntryPoint; // لاستخدام @AndroidEntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers; // RxJava Android Schedulers
import io.reactivex.disposables.CompositeDisposable; // لإدارة اشتراكات RxJava
import io.reactivex.schedulers.Schedulers; // RxJava Schedulers

/**
 * LoginActivity: شاشة تسجيل الدخول للمستخدم.
 * تسمح للمستخدم بإدخال بريده الإلكتروني وكلمة المرور لتسجيل الدخول.
 * تستخدم Dagger Hilt لحقن التبعيات (مثل ApiHelper و SessionManager).
 */
@AndroidEntryPoint
public class LoginActivity extends BaseActivity {

    private static final String TAG = "LoginActivity";
    private ActivityLoginBinding binding; // كائن Data Binding لتسهيل الوصول إلى الـ Views

    @Inject
    ApiHelper apiHelper; // حقن ApiHelper للتعامل مع استدعاءات الـ API

    @Inject
    SessionManager sessionManager; // حقن SessionManager لإدارة جلسة المستخدم

    private CompositeDisposable compositeDisposable; // لإدارة اشتراكات RxJava ومنع تسرب الذاكرة

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater()); // تهيئة Data Binding
        setContentView(binding.getRoot()); // تعيين الـ Layout من Data Binding

        AppLogger.d(TAG, "LoginActivity created.");

        compositeDisposable = new CompositeDisposable(); // تهيئة CompositeDisposable

        // التحقق مما إذا كان المستخدم مسجلاً للدخول بالفعل
        if (sessionManager.isLoggedIn()) {
            AppLogger.i(TAG, "User already logged in, redirecting to MainActivity.");
            // إذا كان المستخدم مسجلاً للدخول، انتقل مباشرة إلى الشاشة الرئيسية (MainActivity)
            // (سوف نقوم بإنشاء MainActivity لاحقًا)
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish(); // إغلاق LoginActivity لمنع العودة إليها
            return; // إنهاء دالة onCreate
        }

        // إعداد المستمعين للأزرار
        setupListeners();
    }

    private void setupListeners() {
        // مستمع لزر تسجيل الدخول
        binding.loginButton.setOnClickListener(v -> attemptLogin());

        // مستمع لزر "هل نسيت كلمة المرور؟"
        binding.forgotPasswordButton.setOnClickListener(v -> {
            AppLogger.d(TAG, "Forgot password button clicked.");
            // الانتقال إلى شاشة استعادة كلمة المرور (ForgotPasswordActivity)
            // (سنقوم بإنشاء ForgotPasswordActivity لاحقًا)
            startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
        });

        // مستمع لزر التسجيل
        binding.registerButton.setOnClickListener(v -> {
            AppLogger.d(TAG, "Register button clicked.");
            // الانتقال إلى شاشة التسجيل (RegisterActivity)
            // (سنقوم بإنشاء RegisterActivity لاحقًا)
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });
    }

    /**
     * محاولة تسجيل دخول المستخدم.
     * تقوم بالتحقق من صحة المدخلات وإجراء استدعاء API.
     */
    private void attemptLogin() {
        String email = binding.emailEditText.getText().toString().trim();
        String password = binding.passwordEditText.getText().toString().trim();

        // إخفاء أي أخطاء سابقة
        binding.emailInputLayout.setError(null);
        binding.passwordInputLayout.setError(null);

        // التحقق من صحة المدخلات
        if (TextUtils.isEmpty(email) || !Validator.isValidEmail(email)) {
            binding.emailInputLayout.setError(getString(R.string.error_invalid_email));
            AppLogger.w(TAG, "Invalid email entered: " + email);
            return;
        }

        if (TextUtils.isEmpty(password)) {
            binding.passwordInputLayout.setError(getString(R.string.error_empty_password));
            AppLogger.w(TAG, "Empty password entered.");
            return;
        }

        // إظهار Progress Dialog أثناء عملية تسجيل الدخول
        showProgressDialog(getString(R.string.logging_in));

        // إجراء استدعاء API لتسجيل الدخول باستخدام RxJava
        compositeDisposable.add(apiHelper.loginUser(new User(email, password)) // User هنا كـ LoginRequest
                .subscribeOn(Schedulers.io()) // تنفيذ في Background thread
                .observeOn(AndroidSchedulers.mainThread()) // مراقبة النتائج في Main thread (UI)
                .subscribe(apiResponse -> {
                    hideProgressDialog(); // إخفاء Progress Dialog
                    if (apiResponse.isSuccess() && apiResponse.getData() != null) {
                        // تسجيل الدخول بنجاح
                        AppLogger.i(TAG, "Login successful for user: " + apiResponse.getData().getEmail());
                        sessionManager.loginUser(apiResponse.getData().getToken(), apiResponse.getData());
                        showToast(getString(R.string.login_success));
                        // الانتقال إلى الشاشة الرئيسية (MainActivity)
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish(); // إغلاق LoginActivity
                    } else {
                        // فشل تسجيل الدخول من API
                        String errorMessage = apiResponse.getMessage() != null ? apiResponse.getMessage() : getString(R.string.error_login_failed);
                        ErrorHandler.handleError(this, new ApiException(errorMessage, 0, null)); // استخدام ErrorHandler
                        AppLogger.e(TAG, "Login failed: " + errorMessage);
                    }
                }, throwable -> {
                    hideProgressDialog(); // إخفاء Progress Dialog
                    // معالجة الأخطاء التي تحدث أثناء استدعاء API (مثل عدم وجود إنترنت، مهلة اتصال)
                    ErrorHandler.handleError(this, throwable); // استخدام ErrorHandler
                    AppLogger.e(TAG, "Login API call failed: " + throwable.getMessage(), throwable);
                })
        );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // التخلص من جميع اشتراكات RxJava لمنع تسرب الذاكرة
        if (compositeDisposable != null) {
            compositeDisposable.clear();
            AppLogger.d(TAG, "CompositeDisposable cleared in onDestroy.");
        }
    }
}
