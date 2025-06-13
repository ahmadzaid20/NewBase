package com.devpal.newbase.Ui.fragments; // تم تحديث الحزمة

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.devpal.newbase.R; // استيراد R
import com.devpal.newbase.databinding.FragmentHomeBinding; // Data Binding
import com.devpal.newbase.Ui.base.BaseFragment; // استيراد BaseFragment
import com.devpal.newbase.utils.AppLogger; // استيراد AppLogger

import dagger.hilt.android.AndroidEntryPoint; // لاستخدام @AndroidEntryPoint

/**
 * HomeFragment: يمثل شاشة الصفحة الرئيسية للتطبيق.
 * يرث من BaseFragment لتوفير وظائف أساسية مثل التسجيل والوصول إلى BaseActivity.
 */
@AndroidEntryPoint
public class HomeFragment extends BaseFragment {

    private static final String TAG = "HomeFragment";
    private FragmentHomeBinding binding; // كائن Data Binding

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // تهيئة Data Binding للـ layout الخاص بالـ fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        AppLogger.d(TAG, "HomeFragment onCreateView.");
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // يمكنك إضافة منطق الواجهة هنا، مثل تعيين مستمعين للأزرار، تحميل بيانات، إلخ.
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // تنظيف مرجع الـ binding لتجنب تسرب الذاكرة
        AppLogger.d(TAG, "HomeFragment onDestroyView.");
    }
}
