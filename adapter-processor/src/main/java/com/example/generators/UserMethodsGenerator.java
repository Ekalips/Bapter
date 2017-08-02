package com.example.generators;

import com.example.stuff.Consts;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;

import javax.lang.model.element.Modifier;
import javax.lang.model.type.TypeMirror;

/**
 * Created by wldev on 8/2/17.
 */

public class UserMethodsGenerator {

    public MethodSpec generateUserMethods(String methodName, TypeMirror returnType) {
        MethodSpec.Builder builder = MethodSpec.methodBuilder(methodName);
        builder.returns(TypeName.get(returnType));
        builder.addParameter(int.class, "position")
                .addParameter(Consts.BindingViewHolder, "holder")
                .addParameter(TypeName.OBJECT, "object");
        builder.addModifiers(Modifier.ABSTRACT, Modifier.PUBLIC);
        return builder.build();
    }

}
