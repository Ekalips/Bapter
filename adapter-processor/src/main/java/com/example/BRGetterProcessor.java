
package com.example;

import com.example.annotations.App;
import com.example.generators.NewBRGetterMapperGenerator;
import com.example.stuff.Consts;
import com.squareup.javapoet.JavaFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
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
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import javax.tools.FileObject;
import javax.tools.StandardLocation;

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


    private boolean isBRFileRead = false;

    private boolean isFilePathFound = false;
    private String dataBindingMapperFilePath = "";

    private static final String TEST_FILE_NAME = "BRGetterTestFile";

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

        if (!isFilePathFound) {
            tryToFindFilePath();
        }

        File f = new File(dataBindingMapperFilePath);
        messager.printMessage(Diagnostic.Kind.WARNING, "Does file exists: " + f.exists());
        if (f.exists() && !isBRFileRead) {
            try {
                messager.printMessage(Diagnostic.Kind.WARNING, "File exists, perform first reading");
                Reader reader = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
                int intValueOfChar;
                StringBuilder targetStringBuilder = new StringBuilder("");
                while ((intValueOfChar = reader.read()) != -1) {
                    targetStringBuilder.append((char) intValueOfChar);
                }
                reader.close();

                messager.printMessage(Diagnostic.Kind.WARNING, "Needed string: " + getInnerBrLookupCode(targetStringBuilder.toString()));

                JavaFile.builder(Consts.DEFAULT_PACKAGE_NAME, new NewBRGetterMapperGenerator(getInnerBrLookupCode(targetStringBuilder.toString())).generate())
                        .build().writeTo(filer);

                isBRFileRead = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            messager.printMessage(Diagnostic.Kind.WARNING, "File doesn't exists or reading is performed");
        }


        if (set.isEmpty())
            return false;


//        for (Element element :
//                roundEnvironment.getElementsAnnotatedWith(App.class)) {
//            String packageName = element.getEnclosingElement().toString();
//
//            try {
//                JavaFile.builder(Consts.DEFAULT_PACKAGE_NAME, new BRGetterGenerator().generate(packageName))
//                        .build().writeTo(filer);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }

        return true;
    }

    private void tryToFindFilePath() {
        messager.printMessage(Diagnostic.Kind.WARNING, "Trying to find DataBinding path");
        try {
            FileObject testFO = filer.createResource(StandardLocation.SOURCE_OUTPUT, Consts.DATABINDING_PACKAGE_NAME, TEST_FILE_NAME);
            String outFilePath = testFO.getName().substring(0, testFO.getName().indexOf(TEST_FILE_NAME));
            dataBindingMapperFilePath = outFilePath + "DataBinderMapper.java";
            messager.printMessage(Diagnostic.Kind.WARNING, "Found file path: " + dataBindingMapperFilePath);
            isFilePathFound = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static final String INNER_BR_LOOKUP_CODE_START = " sKeys"; //String[] sKeys
    private static final String INNER_BR_LOOKUP_CODE_END = "};";

    private String getInnerBrLookupCode(String originalCode) {
        String returnString = originalCode.substring(originalCode.indexOf(INNER_BR_LOOKUP_CODE_START), originalCode.lastIndexOf(INNER_BR_LOOKUP_CODE_END) + 1);
        returnString += ";";
        return returnString;
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
