package com.devpal.newbase.Ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.devpal.newbase.databinding.FragmentProfileBinding; // Data Binding
import com.devpal.newbase.Ui.base.BaseFragment;
import com.devpal.newbase.utils.AppLogger;

import dagger.hilt.android.AndroidEntryPoint;

/**
 * ProfileFragment: يمثل شاشة الملف الشخصي للتطبيق.
 * يرث من BaseFragment لتوفير وظائف أساسية.
 */
@AndroidEntryPoint
public class ProfileFragment extends BaseFragment {

    private static final String TAG = "ProfileFragment";
    private FragmentProfileBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        AppLogger.d(TAG, "ProfileFragment onCreateView.");
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // يمكنك إضافة منطق الواجهة هنا
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        AppLogger.d(TAG, "ProfileFragment onDestroyView.");
    }
}
