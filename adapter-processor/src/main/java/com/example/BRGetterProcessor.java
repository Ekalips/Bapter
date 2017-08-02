
package com.example;

import com.example.annotations.App;
import com.example.generators.BRGetterGenerator;
import com.example.stuff.Consts;
import com.squareup.javapoet.JavaFile;

import java.io.IOException;
import java.lang.annotation.Annotation;
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
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

/**
 * Created by wldev on 8/2/17.
 */

public class BRGetterProcessor extends AbstractProcessor {
    private static final List<Class<App>> SUPPORTED_TYPES = Collections.singletonList(App.class);

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

        messager.printMessage(Diagnostic.Kind.OTHER, "Processing @App annotation");

        for (Element element :
                roundEnvironment.getElementsAnnotatedWith(App.class)) {
            String packageName = element.getEnclosingElement().toString();

            try {
                JavaFile.builder(Consts.DEFAULT_PACKAGE_NAME, new BRGetterGenerator().generate(packageName))
                        .build().writeTo(filer);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }
        //        JavaFile.builder(Consts.DEFAULT_PACKAGE_NAME, new BindingViewHolderGenerator().generate())
//                .build().writeTo(filer);

        return true;
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
