package com.example.wldev.arcitecturetry.activity_stuff;

import android.support.annotation.LayoutRes;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ActivityLayout {
    @LayoutRes
    int layoutRes();
}
