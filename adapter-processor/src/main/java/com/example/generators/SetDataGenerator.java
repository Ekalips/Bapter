package com.example.generators;

import com.example.AnnotatedAdapter;
import com.example.stuff.Consts;
import com.squareup.javapoet.MethodSpec;

import java.util.List;

import javax.lang.model.element.Modifier;

/**
 * Created by wldev on 8/2/17.
 */

public class SetDataGenerator implements MethodGenerator {
    @Override
    public MethodSpec generateFor(AnnotatedAdapter annotatedAdapter) {
        MethodSpec.Builder builder = MethodSpec.methodBuilder(Consts.METHOD_SET_DATA);
        builder.addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .addParameter(List.class, Consts.FIELD_DATA_NAME)
                .addStatement("this.$N.clear()", Consts.FIELD_DATA_NAME)
                .addStatement("this.$N.addAll($N)", Consts.FIELD_DATA_NAME, Consts.FIELD_DATA_NAME)
                .addStatement("notifyDataSetChanged()");
        return builder.build();
    }
}
