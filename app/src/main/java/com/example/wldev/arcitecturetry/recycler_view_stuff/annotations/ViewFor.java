package com.example.wldev.arcitecturetry.recycler_view_stuff.annotations;

import android.support.annotation.LayoutRes;

/**
 * Created by wldev on 8/1/17.
 */

public @interface ViewFor {
    Class<?> dataClass();

    @LayoutRes int viewRes();
}
