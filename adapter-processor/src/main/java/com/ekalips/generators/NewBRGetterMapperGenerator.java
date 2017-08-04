package com.ekalips.generators;

import com.ekalips.stuff.Consts;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.lang.model.element.Modifier;

/**
 * Created by wldev on 8/3/17.
 */

public class NewBRGetterMapperGenerator implements TypeSpecGenerator {

    private String BRMapperCode;

    public NewBRGetterMapperGenerator(String BRMapperCode) {
        this.BRMapperCode = BRMapperCode;
    }

    @Override
    public TypeSpec generate() {
        TypeSpec.Builder builder = TypeSpec.classBuilder(Consts.CLASS_NAME_BR_MAPPER);
        builder.addModifiers(Modifier.PUBLIC);
        builder.addField(ParameterizedTypeName.get(Map.class, String.class, Integer.class), "brMap", Modifier.STATIC);
        builder.addStaticBlock(getMapInitBlock());
        builder.addMethod(getBRIdGetterMethod());
        return builder.build();
    }


    private CodeBlock getMapInitBlock() {
        String valuesCode = BRMapperCode.replace("sKeys = new String[]{", "").replace("};", "");
        String[] values = valuesCode.split(",");
        StringBuilder init = new StringBuilder("brMap = new $L<String,Integer>(){\n" +
                "\t{\n");
        for (int i = 0; i < values.length; i++) {
            init.append(String.format(Locale.US, "\t\tput(%s,%d);", values[i].trim(), i)).append("\n");
        }
        init.append("\n\t}\n};");
        return CodeBlock.of(init.toString(), ClassName.get(HashMap.class));
    }

    private MethodSpec getBRIdGetterMethod() {
        MethodSpec.Builder builder = MethodSpec.methodBuilder(Consts.METHOD_GET_BR_FROM_NAME);
        builder.addModifiers(Modifier.STATIC, Modifier.PUBLIC);
        builder.returns(int.class);
        builder.addParameter(String.class, "brName");
        builder.addStatement("return brMap.get($N)", "brName");
        return builder.build();
    }
}
