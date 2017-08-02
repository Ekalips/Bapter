package com.example.generators;

import com.example.stuff.Consts;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Modifier;

/**
 * Created by wldev on 8/2/17.
 */

public class BRGetterGenerator {

    public TypeSpec generate(String packageName) {
        TypeSpec.Builder builder = TypeSpec.classBuilder(Consts.CLASS_NAME_BR_GETTER);
        builder.addField(String.class, "TAG", Modifier.PRIVATE, Modifier.STATIC, Modifier.FINAL);
        builder.addStaticBlock(CodeBlock.of("TAG = $L.class.getSimpleName();", Consts.CLASS_NAME_BR_GETTER));
        builder.addModifiers(Modifier.PUBLIC);
        builder.addMethod(MethodSpec.methodBuilder(Consts.METHOD_GET_BR_FROM_NAME)
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .addParameter(String.class, "name")
                .returns(int.class)
                .addStatement("$L field = null", ClassName.get("java.lang.reflect", "Field"))
                .beginControlFlow("try")
                .addStatement("field = $L.class.getField($N)", ClassName.get(packageName, "BR"), "name")
                .endControlFlow()
                .beginControlFlow("catch($L ex)", ClassName.get("java.lang", "NoSuchFieldException"))
                .addStatement("ex.printStackTrace()")
                .endControlFlow()
                .addStatement("int brRes = -1")
                .beginControlFlow("try")
                .addStatement("brRes = field.getInt(null)")
                .endControlFlow()
                .beginControlFlow("catch($L ex)", ClassName.get("java.lang", "IllegalAccessException"))
                .addStatement("ex.printStackTrace()")
                .endControlFlow()
                .addStatement("$L.d(TAG,String.format(\"Found BR res: \" + $L + \" for name: \" + $L))", ClassName.get("android.util", "Log"), "brRes", "name")
                .addStatement("return brRes")
                .build());
        return builder.build();
    }
}
