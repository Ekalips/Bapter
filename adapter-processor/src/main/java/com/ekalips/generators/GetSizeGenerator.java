package com.ekalips.generators;

import android.support.annotation.CallSuper;

import com.ekalips.AnnotatedAdapter;
import com.ekalips.stuff.Consts;
import com.squareup.javapoet.MethodSpec;

import javax.lang.model.element.Modifier;

/**
 * Created by wldev on 8/2/17.
 */

public class GetSizeGenerator implements MethodGenerator {
    @Override
    public MethodSpec generateFor(AnnotatedAdapter annotatedAdapter) {
        MethodSpec.Builder builder = MethodSpec.methodBuilder(Consts.METHOD_GET_SIZE);
        builder.returns(int.class)
                .addAnnotation(CallSuper.class)
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .addStatement("return $L.size()", Consts.FIELD_DATA_NAME);
        return builder.build();
    }
}
