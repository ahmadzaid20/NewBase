package com.devpal.newbase.Ui.activities; // تم تحديث الحزمة

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.devpal.newbase.R; // استيراد R لتحديد الموارد
import com.devpal.newbase.databinding.ActivityMainBinding; // استيراد Data Binding (سيتم توليده تلقائيًا)
import com.devpal.newbase.Ui.base.BaseActivity; // استيراد BaseActivity
import com.devpal.newbase.Ui.fragments.HomeFragment; // استيراد Fragments (سننشئها لاحقًا)
import com.devpal.newbase.Ui.fragments.NotificationsFragment;
import com.devpal.newbase.Ui.fragments.ProfileFragment;
import com.devpal.newbase.Ui.fragments.SettingsFragment;
import com.devpal.newbase.utils.AppLogger; // استيراد AppLogger
import com.devpal.newbase.utils.SessionManager; // استيراد SessionManager

import javax.inject.Inject; // لاستخدام @Inject

import dagger.hilt.android.AndroidEntryPoint; // لاستخدام @AndroidEntryPoint

/**
 * MainActivity: الشاشة الرئيسية للتطبيق بعد تسجيل الدخول.
 * تستضيف Fragments مختلفة عبر Bottom Navigation View.
 * تتحقق من حالة تسجيل دخول المستخدم وتوجهه إلى LoginActivity إذا لم يكن مسجلاً للدخول.
 */
@AndroidEntryPoint
public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";
    private ActivityMainBinding binding;

    @Inject
    SessionManager sessionManager; // حقن SessionManager للتحقق من حالة تسجيل الدخول

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AppLogger.d(TAG, "MainActivity created.");

        // التحقق من حالة تسجيل الدخول. إذا لم يكن المستخدم مسجلاً للدخول، يتم توجيهه إلى LoginActivity.
        if (!sessionManager.isLoggedIn()) {
            AppLogger.i(TAG, "User not logged in, redirecting to LoginActivity.");
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish(); // إغلاق MainActivity
            return; // إنهاء دالة onCreate
        }

        setupBottomNavigationView(); // إعداد Bottom Navigation
        // عرض الـ Fragment الافتراضي عند بدء الـ Activity
        if (savedInstanceState == null) {
            loadFragment(new HomeFragment());
        }
    }

    /**
     * تُهيئ BottomNavigationView وتُعيّن مستمعًا لاختيار العناصر.
     */
    private void setupBottomNavigationView() {
        binding.navView.setOnItemSelectedListener(this::onNavigationItemSelected);
    }

    /**
     * دالة معالجة حدث اختيار عنصر في BottomNavigationView.
     * تُحدد الـ Fragment الذي يجب عرضه بناءً على العنصر المختار.
     * @param item العنصر المختار من BottomNavigationView.
     * @return true إذا تم التعامل مع الحدث، false بخلاف ذلك.
     */
    private boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment selectedFragment = null;

        int itemId = item.getItemId();
        if (itemId == R.id.navigation_home) { // من ملف bottom_nav_menu.xml
            selectedFragment = new HomeFragment();
            AppLogger.d(TAG, "Home tab selected.");
        } else if (itemId == R.id.navigation_notifications) {
            selectedFragment = new NotificationsFragment();
            AppLogger.d(TAG, "Notifications tab selected.");
        } else if (itemId == R.id.navigation_profile) {
            selectedFragment = new ProfileFragment();
            AppLogger.d(TAG, "Profile tab selected.");
        } else if (itemId == R.id.navigation_settings) {
            selectedFragment = new SettingsFragment();
            AppLogger.d(TAG, "Settings tab selected.");
        }

        if (selectedFragment != null) {
            loadFragment(selectedFragment); // تحميل الـ Fragment المحدد
            return true;
        }
        return false;
    }

    /**
     * تحمل Fragment معين في الـ FrameLayout الرئيسي.
     * @param fragment الـ Fragment المراد تحميله.
     */
    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_fragment_container, fragment); // استبدال الـ Fragment الحالي
        // fragmentTransaction.addToBackStack(null); // يمكن إضافته إلى Back Stack إذا أردت التنقل للخلف
        fragmentTransaction.commit(); // تطبيق التغييرات
        AppLogger.d(TAG, "Fragment loaded: " + fragment.getClass().getSimpleName());
    }

    // يمكنك إضافة دوال للتعامل مع تسجيل الخروج، على سبيل المثال:
    public void performLogout() {
        showProgressDialog(getString(R.string.logging_out)); // "جاري تسجيل الخروج..."
        sessionManager.logoutUser();
        hideProgressDialog();
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        finish();
        AppLogger.i(TAG, "User logged out, redirected to LoginActivity.");
    }
}
