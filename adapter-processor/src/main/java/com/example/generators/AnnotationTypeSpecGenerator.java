package com.example.generators;

import com.example.AnnotatedAdapter;
import com.squareup.javapoet.TypeSpec;

/**
 * Created by wldev on 8/2/17.
 */

public interface AnnotationTypeSpecGenerator extends TypeSpecGenerator {
    TypeSpec generateFor(AnnotatedAdapter annotatedAdapter);
}
