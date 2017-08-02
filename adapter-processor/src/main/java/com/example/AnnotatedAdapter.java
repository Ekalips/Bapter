package com.example;

import com.example.annotations.Adapter;
import com.example.annotations.Bind;
import com.example.annotations.DataSetType;
import com.example.annotations.ViewFor;

import javax.lang.model.element.Element;

/**
 * Created by wldev on 8/1/17.
 */

public class AnnotatedAdapter extends AnnotatedAdapterElement {


    private DataSetType annotatedDataSetType;
    private ViewFor[] viewForTypes;
    private Bind[] binds;

    public AnnotatedAdapter(Element element) {
        super(element);
        Adapter adapter = (element.getAnnotation(Adapter.class));
        annotatedDataSetType = adapter.dataSetType();
        viewForTypes = adapter.useViews();
        binds = adapter.map();
    }

    public DataSetType getAnnotatedDataSetType() {
        return annotatedDataSetType;
    }

    public ViewFor[] getViewForTypes() {
        return viewForTypes;
    }

    public Bind[] getBinds() {
        return binds;
    }
}

