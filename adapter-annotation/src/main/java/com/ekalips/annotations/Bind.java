package com.ekalips.annotations;

/**
 * Created by wldev on 8/2/17.
 */

public @interface Bind {
    String bindingResourceName();

    Class bindClass();

    BindMode bindMode() default BindMode.variable;

    String methodName() default "";

    MethodCallMode methodCallMode() default MethodCallMode.asReturn;
}
