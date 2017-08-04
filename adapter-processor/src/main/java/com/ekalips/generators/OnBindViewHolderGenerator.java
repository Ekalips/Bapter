package com.ekalips.generators;

import android.support.annotation.CallSuper;

import com.ekalips.AnnotatedAdapter;
import com.ekalips.annotations.Bind;
import com.ekalips.annotations.BindMode;
import com.ekalips.stuff.Consts;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Modifier;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeMirror;

/**
 * Created by wldev on 8/2/17.
 */

public class OnBindViewHolderGenerator implements MethodGenerator {

    private static final String CURRENT_DATA_OBJ_NAME = "currentData";

    @Override
    public MethodSpec generateFor(AnnotatedAdapter annotatedAdapter) {
        Bind[] binds = annotatedAdapter.getBinds();


        MethodSpec.Builder builder = MethodSpec.methodBuilder(Consts.METHOD_BIND_VIEW_HOLDER);
        builder.addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .addAnnotation(CallSuper.class)
                .addParameter(Consts.BindingViewHolder, "holder", Modifier.FINAL)
                .addParameter(int.class, "position");

        builder.addStatement("final Object $N = $L.get(holder.getAdapterPosition())", CURRENT_DATA_OBJ_NAME, Consts.FIELD_DATA_NAME);

        for (Bind bind :
                binds) {

            TypeMirror dataTypeMirror = null;
            try {
                bind.bindClass().getClass();
            } catch (MirroredTypeException ex) {
                dataTypeMirror = ex.getTypeMirror();
            }

            if (bind.bindMode() == BindMode.variable) {
                builder.beginControlFlow("if ($N instanceof $L)", CURRENT_DATA_OBJ_NAME, TypeName.get(dataTypeMirror));
                builder.addStatement("holder.getBinding().setVariable($L.$L($S),$N)", Consts.BRGetter, Consts.METHOD_GET_BR_FROM_NAME, bind.bindingResourceName(), CURRENT_DATA_OBJ_NAME);
                builder.endControlFlow();
            } else if (!bind.methodName().isEmpty()) {
                switch (bind.methodCallMode()) {
                    case asReturn: {
                        addReturnMethod(builder, bind);
                        break;
                    }
                    case onClick: {
                        addOnClick(builder, bind);
                        break;
                    }
                    case onLongClick: {
                        addOnLongClick(builder, bind);
                        break;
                    }
                }
            }
        }


        return builder.build();
    }

//    @Override
//    public void onBindViewHolder(BindingViewHolder holder, int position, List<Object> payloads) {
//        super.onBindViewHolder(holder, position, payloads);
//    }


    private void addReturnMethod(MethodSpec.Builder builder, Bind bind) {
//        builder.addStatement("int brId = $L.$L($S)", Consts.BRGetter, Consts.METHOD_GET_BR_FROM_NAME, bind.bindingResourceName());

        builder.addStatement("holder.getBinding().setVariable($L.$L($S),$L(holder.getAdapterPosition(),holder,$N))", Consts.BRGetter, Consts.METHOD_GET_BR_FROM_NAME, bind.bindingResourceName(), bind.methodName(), CURRENT_DATA_OBJ_NAME);
    }

    private void addOnClick(MethodSpec.Builder builder, Bind bind) {
//        builder.addStatement("int brId = $L.$L($S)", Consts.BRGetter, Consts.METHOD_GET_BR_FROM_NAME, bind.bindingResourceName());

        MethodSpec.Builder onClickBuilder = MethodSpec.methodBuilder("onClick");
        onClickBuilder.addAnnotation(Override.class);
        onClickBuilder.addModifiers(Modifier.PUBLIC);
        onClickBuilder.addParameter(Consts.View, "view");
        onClickBuilder.addStatement("$L(holder.getAdapterPosition(),holder,$N)", bind.methodName(), CURRENT_DATA_OBJ_NAME);

        TypeSpec.Builder anonymousOnClickBuilder = TypeSpec.anonymousClassBuilder("");
        anonymousOnClickBuilder.addSuperinterface(ClassName.get(Consts.DEFAULT_PACKAGE_NAME, Consts.CLASS_NAME_CLICK_ADAPTER));
        anonymousOnClickBuilder.addMethod(onClickBuilder.build());

        builder.addStatement("holder.getBinding().setVariable($L.$L($S), $L)", Consts.BRGetter, Consts.METHOD_GET_BR_FROM_NAME, bind.bindingResourceName(), anonymousOnClickBuilder.build());
    }

//    @Override
//    public void onClick(View view) {
//
//    }

    private void addOnLongClick(MethodSpec.Builder builder, Bind bind) {
//        builder.addStatement("int brId = $L.$L($S)", Consts.BRGetter, Consts.METHOD_GET_BR_FROM_NAME, bind.bindingResourceName());

        MethodSpec.Builder onLongClickMethodBuilder = MethodSpec.methodBuilder("onLongClick");
        onLongClickMethodBuilder.addAnnotation(Override.class);
        onLongClickMethodBuilder.addModifiers(Modifier.PUBLIC);
        onLongClickMethodBuilder.addParameter(Consts.View, "view");
        onLongClickMethodBuilder.returns(boolean.class);
        onLongClickMethodBuilder.addStatement("$L(holder.getAdapterPosition(),holder,$N)", bind.methodName(), CURRENT_DATA_OBJ_NAME);
        onLongClickMethodBuilder.addStatement("return false");

        TypeSpec.Builder anonymousOnClickBuilder = TypeSpec.anonymousClassBuilder("");
        anonymousOnClickBuilder.addSuperinterface(ClassName.get(Consts.DEFAULT_PACKAGE_NAME, Consts.CLASS_NAME_LONG_CLICK_ADAPTER));
        anonymousOnClickBuilder.addMethod(onLongClickMethodBuilder.build());

        builder.addStatement("holder.getBinding().setVariable($L.$L($S), $L)", Consts.BRGetter, Consts.METHOD_GET_BR_FROM_NAME, bind.bindingResourceName(), anonymousOnClickBuilder.build());
    }

//    @Override
//    public boolean onLongClick(View view) {
//        return false;
//    }


    private Object[] brGetterObjects = new Object[]{Consts.BRGetter, Consts.METHOD_GET_BR_FROM_NAME};

}
