package com.ekalips.wldev.arcitecturetry.activity_stuff;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import static junit.framework.Assert.assertNotNull;

/**
 * Created by wldev on 8/1/17.
 */

public abstract class BaseDataBindingActivity<BindingType extends ViewDataBinding> extends AppCompatLifecycleActivity {

    private BindingType binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, getActivityLayout());
    }

    public BindingType getBinding() {
        return binding;
    }

    @LayoutRes
    int getActivityLayout() {
        ActivityLayout activityLayout = getClass().getAnnotation(ActivityLayout.class);
        assertNotNull("Activity must have @ActivityLayout annotation", activityLayout);
        return activityLayout.layoutRes();
    }
}
