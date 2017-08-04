package com.ekalips.wldev.arcitecturetry;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.ekalips.wldev.arcitecturetry.activity_stuff.ActivityLayout;
import com.ekalips.wldev.arcitecturetry.activity_stuff.BaseDataBindingActivity;
import com.ekalips.wldev.arcitecturetry.databinding.ActivityMainBinding;

@ActivityLayout(layoutRes = R.layout.activity_main)
public class MainActivity extends BaseDataBindingActivity<ActivityMainBinding> {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getBinding().text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
