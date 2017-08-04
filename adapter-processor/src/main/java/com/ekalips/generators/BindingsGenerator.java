package com.ekalips.generators;

import com.ekalips.stuff.Consts;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

/**
 * Created by wldev on 8/2/17.
 */

public class BindingsGenerator implements TypeSpecGenerator {
    @Override
    public TypeSpec generate() {
        TypeSpec.Builder builder = TypeSpec.classBuilder(Consts.CLASS_NAME_BINDINGS);
        builder.addMethod(MethodSpec.methodBuilder("setDataToAdapter")
                .addParameter(Consts.RecyclerView, "recyclerView")
                .addParameter(Consts.List, "data")
                .addAnnotation(AnnotationSpec.builder(ClassName.get("android.databinding", "BindingAdapter"))
                        .addMember("value", "{\"$N\"}", "src")
                        .build())
                .beginControlFlow("if (data != null && recyclerView.getAdapter() instanceof $L)", Consts.DataSetInterface)
                .addStatement("(($L)recyclerView.getAdapter()).$N(data)", Consts.DataSetInterface, Consts.METHOD_SET_DATA)
                .endControlFlow()
                .build());
        return builder.build();
    }
}
