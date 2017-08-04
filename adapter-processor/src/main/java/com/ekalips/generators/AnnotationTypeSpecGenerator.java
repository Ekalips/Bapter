package com.ekalips.generators;

import com.ekalips.AnnotatedAdapter;
import com.squareup.javapoet.TypeSpec;

/**
 * Created by wldev on 8/2/17.
 */

public interface AnnotationTypeSpecGenerator extends TypeSpecGenerator {
    TypeSpec generateFor(AnnotatedAdapter annotatedAdapter);
}
