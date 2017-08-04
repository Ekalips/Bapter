package com.example;

import com.example.annotations.Adapter;
import com.example.annotations.Bind;
import com.example.annotations.BindMode;
import com.example.generators.BindingViewHolderGenerator;
import com.example.generators.BindingsGenerator;
import com.example.generators.ClickAdapterGenerator;
import com.example.generators.DataSetGenerator;
import com.example.generators.GetSizeGenerator;
import com.example.generators.GetViewTypeGenerator;
import com.example.generators.LongClickAdapterGenerator;
import com.example.generators.OnBindViewHolderGenerator;
import com.example.generators.OnCreateViewHolderGenerator;
import com.example.generators.SetDataGenerator;
import com.example.generators.UserMethodsGenerator;
import com.example.generators.ViewTypesEnumGenerator;
import com.example.stuff.Consts;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;


public class MainProcessor extends AbstractProcessor {

    private static final List<Class<Adapter>> SUPPORTED_TYPES = Collections.singletonList(Adapter.class);
    private static final char CHAR_DOT = '.';

    private Messager messager;
    private Types typesUtil;
    private Elements elementsUtil;
    private Filer filer;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        messager = processingEnv.getMessager();
        typesUtil = processingEnv.getTypeUtils();
        elementsUtil = processingEnv.getElementUtils();
        filer = processingEnv.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

        if (set.isEmpty())
            return false;

        messager.printMessage(Diagnostic.Kind.WARNING, "Start processing : " + " Set size: " + set.size() + " env: " + roundEnvironment.toString());

        messager.printMessage(Diagnostic.Kind.WARNING, "Generating : " + Consts.CLASS_NAME_BINDING_VIEW_HOLDER);

        try {
            JavaFile.builder(Consts.DEFAULT_PACKAGE_NAME, new BindingViewHolderGenerator().generate())
                    .build().writeTo(filer);
            JavaFile.builder(Consts.DEFAULT_PACKAGE_NAME, new ClickAdapterGenerator().generate())
                    .build().writeTo(filer);
            JavaFile.builder(Consts.DEFAULT_PACKAGE_NAME, new LongClickAdapterGenerator().generate())
                    .build().writeTo(filer);
            JavaFile.builder(Consts.DEFAULT_PACKAGE_NAME, new DataSetGenerator().generate())
                    .build().writeTo(filer);
            JavaFile.builder(Consts.DEFAULT_PACKAGE_NAME, new BindingsGenerator().generate())
                    .build().writeTo(filer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<AnnotatedAdapter> objects = new ArrayList<>();

        for (Element e :
                roundEnvironment.getElementsAnnotatedWith(Adapter.class)) {
            messager.printMessage(Diagnostic.Kind.WARNING, "Parsing: " + e.getSimpleName().toString() + " package: " + e.getEnclosingElement().toString());
            AnnotatedAdapter element = new AnnotatedAdapter(e);
            if (element.getElementKind() != ElementKind.CLASS) {
                messager.printMessage(Diagnostic.Kind.ERROR, "Can be applied to class.");
                return true;
            }
            objects.add(element);
        }


        for (AnnotatedAdapter ann :
                objects) {

            TypeMirror dataTypeMirror = null;

            messager.printMessage(Diagnostic.Kind.WARNING, "Processing annotation : " + ann.toString());

            try {
                messager.printMessage(Diagnostic.Kind.WARNING, ann.getAnnotatedDataSetType().dataSetType().toString());
            } catch (MirroredTypeException ex) {
                dataTypeMirror = ex.getTypeMirror();
            }

            TypeSpec.Builder adapterClass = TypeSpec
                    .classBuilder(getNewClassName(ann))
                    .superclass(ParameterizedTypeName.get(ClassName.get("android.support.v7.widget.RecyclerView", "Adapter"),
                            Consts.BindingViewHolder))
                    .addSuperinterface(Consts.DataSetInterface)
                    .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                    .addField(ParameterizedTypeName.get(ClassName.get(List.class), TypeName.get(dataTypeMirror)), Consts.FIELD_DATA_NAME)
                    .addInitializerBlock(CodeBlock.of("$L = new $L<>();", Consts.FIELD_DATA_NAME, Consts.ArrayList))
                    .addType(generateViewTypesEnum(ann))
                    .addMethod(generateGetViewTypeMethod(ann))
                    .addMethod(generateOnCreateViewHolderMethod(ann))
                    .addMethod(generateOnBindViewHolder(ann))
                    .addMethod(generateGetItemCountMethod())
                    .addMethod(generateSetDataMethod());

            generateUserMethods(ann, adapterClass);

            try {
                JavaFile.builder(ann.getPackageName(), adapterClass.build())
                        .build().writeTo(filer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return false;
    }

    private TypeSpec generateViewTypesEnum(AnnotatedAdapter annotatedAdapter) {
        return new ViewTypesEnumGenerator().generateFor(annotatedAdapter);
    }

    private MethodSpec generateGetViewTypeMethod(AnnotatedAdapter annotatedAdapter) {
        return new GetViewTypeGenerator().generateFor(annotatedAdapter);
    }

    private MethodSpec generateOnCreateViewHolderMethod(AnnotatedAdapter annotatedAdapter) {
        return new OnCreateViewHolderGenerator().generateFor(annotatedAdapter);
    }

    private MethodSpec generateGetItemCountMethod() {
        return new GetSizeGenerator().generateFor(null);
    }

    private MethodSpec generateOnBindViewHolder(AnnotatedAdapter aa) {
        return new OnBindViewHolderGenerator().generateFor(aa);
    }

    private MethodSpec generateSetDataMethod() {
        return new SetDataGenerator().generateFor(null);
    }

    private void generateUserMethods(AnnotatedAdapter aa, TypeSpec.Builder builder) {
        UserMethodsGenerator userMethodsGenerator = new UserMethodsGenerator();
        for (Bind bind :
                aa.getBinds()) {
            if (bind.bindMode() != BindMode.method) continue;

            TypeMirror dataTypeMirror = null;
            try {
                bind.bindClass().getClass();
            } catch (MirroredTypeException ex) {
                dataTypeMirror = ex.getTypeMirror();
            }

            if (dataTypeMirror == null || bind.methodName().isEmpty()) {
                messager.printMessage(Diagnostic.Kind.ERROR, "Can't add user method. Empty methodName or bindClass");
            }

            messager.printMessage(Diagnostic.Kind.WARNING, "Adding user method with name : " + bind.methodName() + " and return type : " + dataTypeMirror.toString());
            builder.addMethod(userMethodsGenerator.generateUserMethods(bind.methodName(), dataTypeMirror));
        }
    }

    private String getNewClassName(AnnotatedAdapter aa) {
        return aa.getClassName() + "Generated";
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotations = new LinkedHashSet<>();
        for (Class<? extends Annotation> annotation : SUPPORTED_TYPES) {
            annotations.add(annotation.getCanonicalName());
        }
        return annotations;
    }


    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }


}
