package com.devpal.newbase.Ui.activities;

import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Log; // لأغراض تصحيح الأخطاء

import com.devpal.newbase.Models.Country;
import com.devpal.newbase.Models.User;
import com.devpal.newbase.R;
import com.devpal.newbase.databinding.ActivityRegisterBinding;
import com.devpal.newbase.exceptions.ApiException;
import com.devpal.newbase.repository.UserRepository;
import com.devpal.newbase.Ui.base.BaseActivity; // استيراد BaseActivity الخاص بك
import com.devpal.newbase.utils.AppLogger;
import com.devpal.newbase.utils.ErrorHandler;
import com.devpal.newbase.utils.Validator;
import com.devpal.newbase.utils.AppConstants; // استيراد كلاس AppConstants لجلب قائمة الدول

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * RegisterActivity: شاشة تسجيل حساب جديد للمستخدم.
 * تم تعديلها الآن لاستخدام قائمة دول مخصصة يتم جلبها من ملف strings.xml
 * وتتحكم في طول رقم الهاتف ديناميكياً بناءً على الدولة المختارة.
 * تستخدم Dagger Hilt لحقن التبعيات (مثل UserRepository).
 * مؤشرات التقدم (Progress Dialog) يتم التعامل معها الآن بشكل صحيح عبر BaseActivity الخاصة بك.
 */
@AndroidEntryPoint
public class RegisterActivity extends BaseActivity {

    private static final String TAG = "RegisterActivity";
    private ActivityRegisterBinding binding;

    @Inject
    UserRepository userRepository;

    private CompositeDisposable compositeDisposable;

    private Country selectedCountry; // لتخزين الدولة المختارة حالياً
    private List<Country> allCountries; // قائمة بجميع الدول المتاحة

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AppLogger.d(TAG, "RegisterActivity created.");

        compositeDisposable = new CompositeDisposable();

        // تهيئة قائمة الدول: الآن يتم جلبها من AppConstants التي تقرأ من strings.xml
        allCountries = AppConstants.getCountries(this);

        setupListeners();
        // تهيئة نص حقل اختيار الدولة ليعرض نصًا افتراضيًا عند البدء
        binding.countryCodeEditText.setText(getString(R.string.prompt_select_country));

        // يمكنك تعيين دولة افتراضية عند بدء النشاط (مثلاً الأردن أو مصر)
        // إذا كنت ترغب في ذلك، ابحث عن الدولة في قائمة allCountries وقم باستدعاء setSelectedCountry بها.
        // مثال لتعيين الأردن كدولة افتراضية (تأكد أن "Jordan" أو "الأردن" موجودة في strings.xml الخاص بك):
        // for (Country country : allCountries) {
        //     if (country.getNameEn().equals("Jordan")) { // أو country.getName().equals("الأردن")
        //         setSelectedCountry(country);
        //         break;
        //     }
        // }
    }

    /**
     * إعداد مستمعي الأحداث لمختلف عناصر واجهة المستخدم.
     */
    private void setupListeners() {
        binding.registerButton.setOnClickListener(v -> attemptRegistration());
        binding.loginButton.setOnClickListener(v -> {
            AppLogger.d(TAG, "Login button clicked from RegisterActivity. Finishing current activity.");
            finish();
        });

        // مستمع للنقر على حقل اختيار الدولة لفتح قائمة الدول المخصصة
        binding.countryCodeEditText.setOnClickListener(v -> showCountryPickerDialog());
        // مستمع للنقر على الأيقونة النهائية في TextInputLayout لفتح قائمة الدول أيضاً
        binding.countryCodeInputLayout.setEndIconOnClickListener(v -> showCountryPickerDialog());
    }

    /**
     * تفتح نافذة حوار لاختيار الدولة من قائمة.
     * <p>
     * ملاحظة: هذا تطبيق بسيط جداً باستخدام AlertDialog.
     * في تطبيق حقيقي، يفضل استخدام {@link androidx.fragment.app.DialogFragment}
     * أو {@link com.google.android.material.bottomsheet.BottomSheetDialogFragment}
     * لتوفير واجهة مستخدم أفضل مع حقل بحث و RecyclerView.
     */
    private void showCountryPickerDialog() {
        AppLogger.d(TAG, "Showing country picker dialog.");

        // تحويل قائمة كائنات Country إلى مصفوفة من السلاسل النصية لعرضها في AlertDialog
        String[] countryNamesForDialog = new String[allCountries.size()];
        for (int i = 0; i < allCountries.size(); i++) {
            Country country = allCountries.get(i);
            // نعرض اسم الدولة ورمزها (مثلاً: "الأردن (+962)")
            countryNamesForDialog[i] = country.getName() + " (" + country.getCode() + ")";
        }

        // بناء وعرض AlertDialog بسيط لاختيار الدولة
        new android.app.AlertDialog.Builder(this)
                .setTitle(R.string.prompt_select_country) // عنوان الحوار "اختر الدولة"
                .setItems(countryNamesForDialog, (dialog, which) -> {
                    // عند اختيار عنصر من القائمة، قم بتعيين الدولة المختارة
                    Country chosenCountry = allCountries.get(which);
                    setSelectedCountry(chosenCountry);
                })
                .show();
    }

    /**
     * تعيين الدولة المختارة وتحديث واجهة المستخدم بناءً عليها.
     * هذا يشمل تحديث نص حقل الدولة وتعيين الحد الأقصى لطول رقم الهاتف.
     *
     * @param country الدولة التي تم اختيارها.
     */
    private void setSelectedCountry(Country country) {
        this.selectedCountry = country;
        // تحديث نص حقل اختيار الدولة ليعرض اسم الدولة ومفتاحها
        binding.countryCodeEditText.setText(country.getName() + " " + country.getCode());

        // تحديث الحد الأقصى لعدد خانات رقم الهاتف في حقل الإدخال
        InputFilter[] filters = new InputFilter[1];
        filters[0] = new InputFilter.LengthFilter(country.getMaxLength());
        binding.phoneNumberEditText.setFilters(filters);
        AppLogger.d(TAG, "Selected country: " + country.getName() + ", Max phone length set to: " + country.getMaxLength());

        // مسح رقم الهاتف الحالي إذا كان طوله يتجاوز الحد الأقصى الجديد
        // هذا يمنع وجود رقم أطول مما هو مسموح به بعد تغيير الدولة
        if (binding.phoneNumberEditText.getText() != null &&
                binding.phoneNumberEditText.getText().length() > country.getMaxLength()) {
            binding.phoneNumberEditText.setText("");
        }
        // إزالة أي رسائل خطأ سابقة عن رقم الهاتف أو حقل اختيار الدولة
        binding.phoneNumberInputLayout.setError(null);
        binding.countryCodeInputLayout.setError(null);
    }

    /**
     * محاولة تسجيل حساب مستخدم جديد.
     * تقوم بالتحقق من صحة جميع المدخلات (اسم المستخدم، البريد الإلكتروني، كلمة المرور، رقم الهاتف، والدولة)
     * ثم تقوم بإجراء استدعاء API للتسجيل.
     */
    private void attemptRegistration() {
        // إخفاء أي رسائل خطأ سابقة معروضة على حقول الإدخال.
        binding.emailInputLayout.setError(null);
        binding.passwordInputLayout.setError(null);
        binding.confirmPasswordInputLayout.setError(null);
        binding.phoneNumberInputLayout.setError(null);
        binding.countryCodeInputLayout.setError(null);

        // جلب النصوص المدخلة من حقول الإدخال وإزالة المسافات البيضاء الزائدة.
        String email = binding.emailEditText.getText().toString().trim();
        String password = binding.passwordEditText.getText().toString().trim();
        String confirmPassword = binding.confirmPasswordEditText.getText().toString().trim();
        String phoneNumber = binding.phoneNumberEditText.getText().toString().trim(); // الرقم المحلي فقط

        // --- بدء التحقق من صحة المدخلات ---



        // التحقق من البريد الإلكتروني
        if (TextUtils.isEmpty(email) || !Validator.isValidEmail(email)) {
            binding.emailInputLayout.setError(getString(R.string.error_invalid_email));
            AppLogger.w(TAG, "Invalid email entered: " + email + ". Requesting focus.");
            binding.emailEditText.requestFocus();
            return;
        }

        // التحقق من كلمة المرور
        if (TextUtils.isEmpty(password)) {
            binding.passwordInputLayout.setError(getString(R.string.error_empty_password));
            AppLogger.w(TAG, "Empty password entered. Requesting focus.");
            binding.passwordEditText.requestFocus();
            return;
        }
        if (password.length() < 6) {
            binding.passwordInputLayout.setError(getString(R.string.error_short_password));
            AppLogger.w(TAG, "Password too short. Must be at least 6 characters. Requesting focus.");
            binding.passwordEditText.requestFocus();
            return;
        }

        // التحقق من تأكيد كلمة المرور
        if (TextUtils.isEmpty(confirmPassword)) {
            binding.confirmPasswordInputLayout.setError(getString(R.string.error_empty_confirm_password));
            AppLogger.w(TAG, "Empty confirm password entered. Requesting focus.");
            binding.confirmPasswordEditText.requestFocus();
            return;
        }
        if (!password.equals(confirmPassword)) {
            binding.confirmPasswordInputLayout.setError(getString(R.string.error_passwords_not_match));
            AppLogger.w(TAG, "Passwords do not match. Requesting focus on confirm password.");
            binding.confirmPasswordEditText.requestFocus();
            return;
        }

        // التحقق من اختيار الدولة
        if (selectedCountry == null) {
            binding.countryCodeInputLayout.setError(getString(R.string.error_empty_country_selection));
            AppLogger.w(TAG, "No country selected. Requesting focus on country selection field.");
            binding.countryCodeEditText.requestFocus();
            return;
        }

        // التحقق من رقم الهاتف المحلي
        if (TextUtils.isEmpty(phoneNumber)) {
            binding.phoneNumberInputLayout.setError(getString(R.string.error_empty_phone));
            AppLogger.w(TAG, "Empty phone number entered. Requesting focus.");
            binding.phoneNumberEditText.requestFocus();
            return;
        }
        // التحقق من طول رقم الهاتف بناءً على الدولة المختارة
        if (phoneNumber.length() != selectedCountry.getMaxLength()) {
            binding.phoneNumberInputLayout.setError(getString(R.string.error_invalid_phone_length, selectedCountry.getMaxLength()));
            AppLogger.w(TAG, "Invalid phone number length for " + selectedCountry.getName() + ". Expected: " + selectedCountry.getMaxLength() + ", Got: " + phoneNumber.length());
            binding.phoneNumberEditText.requestFocus();
            return;
        }
        // التحقق من أن الرقم يحتوي على أرقام فقط
        if (!TextUtils.isDigitsOnly(phoneNumber)) {
            binding.phoneNumberInputLayout.setError(getString(R.string.error_invalid_phone_digits_only));
            AppLogger.w(TAG, "Phone number contains non-digits.");
            binding.phoneNumberEditText.requestFocus();
            return;
        }
        // --- انتهاء التحقق من صحة المدخلات ---

        AppLogger.d(TAG, "All inputs validated. Attempting registration for: " + email);
        // بناء رقم الهاتف الكامل (رمز الدولة + الرقم المحلي)
        String fullPhoneNumber = selectedCountry.getCode() + phoneNumber;
        AppLogger.d(TAG, "Full Phone Number: " + fullPhoneNumber);

        // استخدام دالة عرض التقدم من BaseActivity
        super.showProgressDialog(getString(R.string.registering));

        User registerRequest = new User(email, password);
        registerRequest.setPhoneNumber(fullPhoneNumber); // تعيين رقم الهاتف الكامل

        compositeDisposable.add(userRepository.registerUser(registerRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(apiResponse -> {
                    // استخدام دالة إخفاء التقدم من BaseActivity
                    super.hideProgressDialog();
                    if (apiResponse.isSuccess()) {
                        AppLogger.i(TAG, "Registration successful for user: " + email);
                        super.showToast(getString(R.string.registration_success)); // استخدام دالة عرض Toast من BaseActivity
                        finish();
                    } else {
                        String errorMessage = apiResponse.getMessage() != null ? apiResponse.getMessage() : getString(R.string.error_registration_failed);
                        ErrorHandler.handleError(this, new ApiException(errorMessage, 0, null));
                        AppLogger.e(TAG, "Registration failed: " + errorMessage);
                    }
                }, throwable -> {
                    // استخدام دالة إخفاء التقدم من BaseActivity
                    super.hideProgressDialog();
                    ErrorHandler.handleError(this, throwable);
                    AppLogger.e(TAG, "Registration API call failed: " + throwable.getMessage(), throwable);
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
