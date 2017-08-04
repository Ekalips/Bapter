package com.ekalips;

import com.ekalips.annotations.Adapter;
import com.ekalips.annotations.Bind;
import com.ekalips.annotations.DataSetType;
import com.ekalips.annotations.ViewFor;

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

