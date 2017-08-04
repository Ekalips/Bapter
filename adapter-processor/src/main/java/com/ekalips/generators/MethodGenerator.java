package com.ekalips.generators;

import com.ekalips.AnnotatedAdapter;
import com.squareup.javapoet.MethodSpec;

/**
 * Created by wldev on 8/2/17.
 */

public interface MethodGenerator {
    MethodSpec generateFor(AnnotatedAdapter annotatedAdapter);
}
