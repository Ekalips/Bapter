package com.example.generators;

import com.example.stuff.Consts;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.util.List;

import javax.lang.model.element.Modifier;

/**
 * Created by wldev on 8/2/17.
 */

public class DataSetGenerator implements TypeSpecGenerator {
    @Override
    public TypeSpec generate() {
        TypeSpec.Builder builder = TypeSpec.interfaceBuilder(Consts.CLASS_NAME_DATA_SET);
        builder.addModifiers(Modifier.PUBLIC);
        builder.addMethod(MethodSpec.methodBuilder(Consts.METHOD_SET_DATA)
                .addParameter(List.class, Consts.FIELD_DATA_NAME)
                .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                .build());
        return builder.build();
    }
}
