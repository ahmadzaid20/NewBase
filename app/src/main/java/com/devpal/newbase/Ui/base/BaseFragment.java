package com.devpal.newbase.Ui.base; // تم تحديث الحزمة

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.devpal.newbase.utils.AppLogger; // تم تحديث الاستيراد

import dagger.hilt.android.AndroidEntryPoint;

/**
 * BaseFragment: الفئة الأساسية لجميع Fragments في التطبيق.
 * توفر وظائف مشتركة، مثل الوصول إلى وظائف BaseActivity (عبر casting)
 * والتعامل مع تهيئة Dagger Hilt.
 *
 * @AndroidEntryPoint: يخبر Dagger Hilt بضرورة حقن التبعيات في هذا الـ Fragment.
 * يجب أن تكون موجودة في كل Activity أو Fragment تحتاج لحقن التبعيات.
 */
@AndroidEntryPoint
public abstract class BaseFragment extends Fragment {

    // مرجع إلى الـ BaseActivity المرتبطة بهذا الـ Fragment
    private BaseActivity parentActivity;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        // التحقق مما إذا كانت الـ Activity المرفقة هي BaseActivity
        if (context instanceof BaseActivity) {
            this.parentActivity = (BaseActivity) context;
            AppLogger.d("BaseFragment", "onAttach: Attached to BaseActivity - " + getClass().getSimpleName());
        } else {
            // في مشروع قالب، قد ترغب في التعامل مع هذا الخطأ بشكل صارم.
            // مثلاً، رمي استثناء لمنع أي Fragment من الوجود بدون BaseActivity
            // هذا لضمان أن جميع Activities التي تستضيف Fragments هي BaseActivity.
            AppLogger.e("BaseFragment", "onAttach: Activity is not an instance of BaseActivity - " + getClass().getSimpleName() + ". Some features might not work.");
            // throw new IllegalStateException("Fragment must be attached to a BaseActivity.");
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // يمكنك إضافة أي منطق مشترك هنا بعد إنشاء View Fragment
        AppLogger.d("BaseFragment", "onViewCreated: " + getClass().getSimpleName());
    }

    /**
     * تعيد الـ BaseActivity المرتبطة بهذا الـ Fragment.
     * يمكن استخدامها للوصول إلى الدوال المشتركة في BaseActivity.
     * @return BaseActivity المرتبطة، أو null إذا لم تكن مرتبطة بـ BaseActivity.
     */
    public BaseActivity getParentActivity() {
        return parentActivity;
    }

    /**
     * تُظهر Progress Dialog من خلال الـ BaseActivity المرتبطة.
     * @param message الرسالة المراد عرضها.
     */
    public void showProgressDialog(String message) {
        if (parentActivity != null) {
            parentActivity.showProgressDialog(message);
        }
    }

    /**
     * تُخفي Progress Dialog من خلال الـ BaseActivity المرتبطة.
     */
    public void hideProgressDialog() {
        if (parentActivity != null) {
            parentActivity.hideProgressDialog();
        }
    }

    /**
     * تُظهر رسالة Toast قصيرة للمستخدم من خلال الـ BaseActivity المرتبطة.
     * @param message الرسالة المراد عرضها.
     */
    public void showToast(String message) {
        if (parentActivity != null) {
            parentActivity.showToast(message);
        }
    }

    /**
     * تُظهر رسالة Toast طويلة للمستخدم من خلال الـ BaseActivity المرتبطة.
     * @param message الرسالة المراد عرضها.
     */
    public void showLongToast(String message) {
        if (parentActivity != null) {
            parentActivity.showLongToast(message);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.parentActivity = null; // تجنب تسرب الذاكرة
        AppLogger.d("BaseFragment", "onDetach: " + getClass().getSimpleName());
    }

    // يمكنك إضافة المزيد من الدوال المساعدة هنا حسب الحاجة
}
