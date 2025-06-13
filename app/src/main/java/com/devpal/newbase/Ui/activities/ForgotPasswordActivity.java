package com.devpal.newbase.Ui.activities;

import android.os.Bundle;
import android.text.TextUtils;

import com.devpal.newbase.R;
import com.devpal.newbase.databinding.ActivityForgotPasswordBinding;
import com.devpal.newbase.exceptions.ApiException;
import com.devpal.newbase.repository.UserRepository;
import com.devpal.newbase.Ui.base.BaseActivity;
import com.devpal.newbase.utils.AppLogger;
import com.devpal.newbase.utils.ErrorHandler;
import com.devpal.newbase.utils.Validator;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * ForgotPasswordActivity: شاشة استعادة كلمة المرور.
 * تسمح للمستخدم بإدخال بريده الإلكتروني لإرسال تعليمات إعادة تعيين كلمة المرور.
 * تستخدم Dagger Hilt لحقن التبعيات (مثل UserRepository).
 */
@AndroidEntryPoint
public class ForgotPasswordActivity extends BaseActivity {

    private static final String TAG = "ForgotPasswordActivity";
    private ActivityForgotPasswordBinding binding;

    @Inject
    UserRepository userRepository; // حقن UserRepository للتعامل مع منطق استعادة كلمة المرور

    private CompositeDisposable compositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AppLogger.d(TAG, "ForgotPasswordActivity created.");

        compositeDisposable = new CompositeDisposable();

        setupListeners();
    }

    private void setupListeners() {
        // مستمع لزر "إعادة تعيين كلمة المرور"
        binding.resetPasswordButton.setOnClickListener(v -> attemptPasswordReset());

        // مستمع لزر "العودة إلى تسجيل الدخول"
        binding.backToLoginButton.setOnClickListener(v -> {
            AppLogger.d(TAG, "Back to Login button clicked from ForgotPasswordActivity.");
            finish(); // إغلاق ForgotPasswordActivity والعودة إلى LoginActivity
        });
    }

    /**
     * محاولة إرسال طلب إعادة تعيين كلمة المرور.
     * تقوم بالتحقق من صحة البريد الإلكتروني وإجراء استدعاء API.
     */
    private void attemptPasswordReset() {
        String email = binding.emailEditText.getText().toString().trim();

        // إخفاء أي أخطاء سابقة
        binding.emailInputLayout.setError(null);

        // التحقق من صحة البريد الإلكتروني
        if (TextUtils.isEmpty(email) || !Validator.isValidEmail(email)) {
            binding.emailInputLayout.setError(getString(R.string.error_invalid_email));
            AppLogger.w(TAG, "Invalid email entered for password reset: " + email);
            return;
        }

        // إظهار Progress Dialog
        showProgressDialog(getString(R.string.sending_reset_email));

        // إجراء استدعاء API لإعادة تعيين كلمة المرور
        compositeDisposable.add(userRepository.forgotPassword(email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(apiResponse -> {
                    hideProgressDialog(); // إخفاء Progress Dialog
                    if (apiResponse.isSuccess()) {
                        AppLogger.i(TAG, "Password reset email sent successfully to: " + email);
                        showToast(getString(R.string.password_reset_success));
                        finish(); // إغلاق النشاط بعد النجاح
                    } else {
                        String errorMessage = apiResponse.getMessage() != null ? apiResponse.getMessage() : getString(R.string.error_password_reset_failed);
                        ErrorHandler.handleError(this, new ApiException(errorMessage, 0, null));
                        AppLogger.e(TAG, "Password reset failed for: " + email + " - " + errorMessage);
                    }
                }, throwable -> {
                    hideProgressDialog(); // إخفاء Progress Dialog
                    ErrorHandler.handleError(this, throwable);
                    AppLogger.e(TAG, "Forgot password API call failed: " + throwable.getMessage(), throwable);
                })
        );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (compositeDisposable != null) {
            compositeDisposable.clear();
            AppLogger.d(TAG, "CompositeDisposable cleared in onDestroy.");
        }
    }
}
