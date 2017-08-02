package com.example.generators;

import android.support.annotation.LayoutRes;

import com.example.AnnotatedAdapter;
import com.example.annotations.ViewFor;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.lang.reflect.Proxy;

import javax.lang.model.element.Modifier;
import javax.lang.model.type.MirroredTypeException;

/**
 * Created by wldev on 8/2/17.
 */

public class ViewTypesEnumGenerator implements AnnotationTypeSpecGenerator {

    private AnnotatedAdapter annotatedAdapter;

    @Override
    public TypeSpec generateFor(AnnotatedAdapter annotatedAdapter) {
        this.annotatedAdapter = annotatedAdapter;
        return generate();
    }

    @Override
    public TypeSpec generate() {
        TypeSpec.Builder builder = TypeSpec.enumBuilder("ViewTypeEnum");
        builder.addField(FieldSpec.builder(TypeName.INT, "viewType", Modifier.PRIVATE).build());
        builder.addField(FieldSpec.builder(TypeName.INT, "layoutId", Modifier.PRIVATE).build());
        builder.addField(FieldSpec.builder(ClassName.bestGuess("Class"), "dataClass", Modifier.PRIVATE).build());
        builder.addMethod(MethodSpec.methodBuilder(Methods.getViewType.methodName)
                .returns(TypeName.INT)
                .addStatement("return viewType")
                .build());
        builder.addMethod(MethodSpec.methodBuilder(Methods.getLayoutId.methodName)
                .returns(TypeName.INT)
                .addAnnotation(LayoutRes.class)
                .addStatement("return layoutId")
                .build());
        builder.addMethod(MethodSpec.constructorBuilder()
                .addParameter(int.class, "viewType")
                .addParameter(int.class, "layoutId")
                .addParameter(Class.class, "dataClass")
                .addStatement("this.viewType = viewType")
                .addStatement("this.layoutId = layoutId")
                .addStatement("this.dataClass = dataClass")
                .build());

        builder.addMethod(MethodSpec.methodBuilder(Methods.getLayoutIdFromDataClass.methodName)
                .returns(int.class)
                .addAnnotation(LayoutRes.class)
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .addParameter(ClassName.get(Class.class), "dataObj")
                .beginControlFlow("for (ViewTypeEnum val : ViewTypeEnum.values())")
                .addStatement("if (val.dataClass.equals(dataObj)) return val.layoutId")
                .endControlFlow()
                .addStatement("return -1")
                .build());

        builder.addMethod(MethodSpec.methodBuilder(Methods.getViewTypeFromDataClass.methodName)
                .returns(int.class)
                .addParameter(ClassName.get(Class.class), "dataObj")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .beginControlFlow("for (ViewTypeEnum val : ViewTypeEnum.values())")
                .addStatement("if (val.dataClass.equals(dataObj)) return val.viewType")
                .endControlFlow()
                .addStatement("return -1")
                .build());
        builder.addMethod(MethodSpec.methodBuilder(Methods.getLayoutIdFromViewType.methodName)
                .returns(int.class)
                .addParameter(int.class, "viewType")
                .addAnnotation(LayoutRes.class)
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .beginControlFlow("for (ViewTypeEnum val : ViewTypeEnum.values())")
                .addStatement("if (val.viewType == viewType) return val.layoutId")
                .endControlFlow()
                .addStatement("return -1")
                .build());

        int lastViewType = -1;
        for (ViewFor vF :
                annotatedAdapter.getViewForTypes()) {
            lastViewType++;

            String viewForClassName = "";
            try {
                Proxy.getInvocationHandler(vF.dataClass());
            } catch (MirroredTypeException ex) {
                viewForClassName = ex.getTypeMirror().toString();
            }


            builder.addEnumConstant("ViewType" + lastViewType,
                    TypeSpec.anonymousClassBuilder("$L,$L,$L", lastViewType, vF.viewRes(), viewForClassName + ".class").build());
        }
        return builder.build();
    }

    public enum Methods {

        getViewType(int.class, "getViewType"), getLayoutId(int.class, "getLayoutId"),
        getViewTypeFromDataClass(int.class, "getViewTypeFromDataClass"), getLayoutIdFromDataClass(int.class, "getLayoutIdFromDataClass"),
        getLayoutIdFromViewType(int.class, "getLayoutIdFromViewType");

        private Class returnType;
        private String methodName;

        Methods(Class returnType, String methodName) {
            this.returnType = returnType;
            this.methodName = methodName;
        }

        public Class getReturnType() {
            return returnType;
        }

        public String getMethodName() {
            return methodName;
        }
    }
}
