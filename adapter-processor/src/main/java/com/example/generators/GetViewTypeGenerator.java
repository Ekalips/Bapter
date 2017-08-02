package com.example.generators;

import com.example.AnnotatedAdapter;
import com.example.stuff.Consts;
import com.squareup.javapoet.MethodSpec;

import javax.lang.model.element.Modifier;

/**
 * Created by wldev on 8/2/17.
 */

public class GetViewTypeGenerator implements MethodGenerator {
    @Override
    public MethodSpec generateFor(AnnotatedAdapter annotatedAdapter) {
        MethodSpec.Builder builder = MethodSpec.methodBuilder(Consts.METHOD_GET_VIEW_TYPE_NAME);
        builder.addAnnotation(Override.class)
                .addParameter(int.class, "position")
                .addModifiers(Modifier.PUBLIC)
                .returns(int.class);

        builder.addStatement("return $L.$L($L.get(position).getClass())",
                Consts.CLASS_NAME_VIEW_TYPE, ViewTypesEnumGenerator.Methods.getViewTypeFromDataClass.getMethodName()
                , Consts.FIELD_DATA_NAME);

        return builder.build();
    }


//    @Override
//    public int getItemViewType(int position) {
//        return super.getItemViewType(position);
//    }

}
