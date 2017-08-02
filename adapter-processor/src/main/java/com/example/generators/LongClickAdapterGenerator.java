package com.example.generators;

import com.example.stuff.Consts;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Modifier;

/**
 * Created by wldev on 8/2/17.
 */

public class LongClickAdapterGenerator implements TypeSpecGenerator {
    @Override
    public TypeSpec generate() {
        TypeSpec.Builder builder = TypeSpec.classBuilder(Consts.CLASS_NAME_LONG_CLICK_ADAPTER);
        builder.addSuperinterface(Consts.View.nestedClass("OnLongClickListener"));
        builder.addModifiers(Modifier.ABSTRACT, Modifier.PUBLIC);
        return builder.build();
    }
}
