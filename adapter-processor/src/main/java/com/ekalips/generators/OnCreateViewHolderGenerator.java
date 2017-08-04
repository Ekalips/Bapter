package com.ekalips.generators;

import android.support.annotation.CallSuper;

import com.ekalips.AnnotatedAdapter;
import com.ekalips.stuff.Consts;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;

import javax.lang.model.element.Modifier;

/**
 * Created by wldev on 8/2/17.
 */

public class OnCreateViewHolderGenerator implements MethodGenerator {
    @Override
    public MethodSpec generateFor(AnnotatedAdapter annotatedAdapter) {
        MethodSpec.Builder builder = MethodSpec.methodBuilder(Consts.METHOD_CREATE_VIEW_HOLDER);
        builder.addAnnotation(Override.class)
                .addAnnotation(CallSuper.class)
                .addParameter(ClassName.get("android.view", "ViewGroup"), "parent")
                .addParameter(int.class, "viewType")
                .addModifiers(Modifier.PUBLIC)
                .returns(Consts.BindingViewHolder);

        builder.addStatement("return new $L<>($L.$L(viewType),parent)", Consts.BindingViewHolder, Consts.CLASS_NAME_VIEW_TYPE, ViewTypesEnumGenerator.Methods.getLayoutIdFromViewType.getMethodName());
        return builder.build();
    }


//    @Override
//    public BindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        new BindingViewHolder<>(ViewTypeEnum.getLayoutIdFromViewType(viewType), parent);
//    }
}
