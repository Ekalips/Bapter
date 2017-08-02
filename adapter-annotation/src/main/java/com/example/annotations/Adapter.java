package com.example.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by wldev on 8/1/17.
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface Adapter {

    DataSetType dataSetType() default @DataSetType;

    ViewFor[] useViews();

    Bind[] map();

}
