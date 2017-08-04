package com.ekalips.generators;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;

import com.ekalips.stuff.Consts;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;

import javax.lang.model.element.Modifier;

/**
 * Created by wldev on 8/2/17.
 */

public class BindingViewHolderGenerator implements TypeSpecGenerator {


    private static final TypeVariableName BindingView = Consts.BindingView;


    @Override
    public TypeSpec generate() {
        TypeSpec.Builder builder = TypeSpec.classBuilder(Consts.CLASS_NAME_BINDING_VIEW_HOLDER);
        builder.superclass((Consts.RecyclerViewViewHolder));
        builder.addModifiers(Modifier.PUBLIC);
        builder.addTypeVariable(BindingView);
        builder.addField(FieldSpec.builder(BindingView, "binding", Modifier.PRIVATE).build());
        builder.addMethod(MethodSpec.constructorBuilder()
                .addParameter(Consts.View, "itemView")
                .addStatement("super(itemView)")
                .build());
        builder.addMethod(MethodSpec.constructorBuilder()
                .addParameter(ParameterSpec.builder(int.class, "layId").addAnnotation(LayoutRes.class).build())
                .addParameter(ParameterSpec.builder(Consts.ViewGroup, "parent").addAnnotation(NonNull.class).build())
                .addModifiers(Modifier.PUBLIC)
                .addStatement("this($T.<BindingView>inflate($T.from(parent.getContext()), layId, parent, false));", Consts.DataBindingUtil, Consts.LayoutInflater)
                .build());
        builder.addMethod(MethodSpec.constructorBuilder()
                .addParameter(BindingView, "binding")
                .addStatement("super(binding.getRoot())")
                .addStatement("this.binding = binding")
                .build());
        builder.addMethod(MethodSpec.methodBuilder(Methods.getBinding.methodName)
                .addStatement("return binding")
                .returns(BindingView)
                .addModifiers(Modifier.PUBLIC)
                .build());
        return builder.build();
    }


    public static enum Methods {
        getBinding("getBinding");

        private String methodName;

        Methods(String methodName) {
            this.methodName = methodName;
        }

        public String getMethodName() {
            return methodName;
        }
    }

//    public class BindingViewHolder<T extends ViewDataBinding> extends RecyclerView.ViewHolder {
//
//        private T binding;
//
//        public BindingViewHolder(@LayoutRes int layId, @NonNull ViewGroup parent) {
//            this(DataBindingUtil.<T>inflate(LayoutInflater.from(parent.getContext()), layId, parent, false));
//        }
//
//        public BindingViewHolder(View itemView) {
//            super(itemView);
//            binding = DataBindingUtil.bind(itemView);
//        }
//
//        public BindingViewHolder(T binding) {
//            super(binding.getRoot());
//            this.binding = binding;
//        }
//
//
//        public T getBinding() {
//            return binding;
//        }
//    }
}
