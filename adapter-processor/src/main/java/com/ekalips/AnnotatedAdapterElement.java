package com.ekalips;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.type.TypeMirror;

/**
 * Created by wldev on 8/1/17.
 */

class AnnotatedAdapterElement {

    private Element element;
    private TypeMirror elementType;
    private ElementKind elementKind;

    private String packageName;
    private String className;

    AnnotatedAdapterElement(Element element) {
        this.element = element;
        elementType = element.asType();
        elementKind = element.getKind();
        packageName = element.getEnclosingElement().toString();
        className = element.getSimpleName().toString();
    }

    public Element getElement() {
        return element;
    }

    public TypeMirror getElementType() {
        return elementType;
    }

    public ElementKind getElementKind() {
        return elementKind;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getClassName() {
        return className;
    }
}
