package com.ekalips.wldev.arcitecturetry.recycler_view_stuff.annotations;

/**
 * Created by wldev on 8/1/17.
 */

public @interface DataSetType {
    Class<?> dataSetType() default Object.class;
}
