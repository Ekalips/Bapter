package com.ekalips.annotationbindingrecyclerview.recycler_view_stuff;

import com.ekalips.annotationbindingrecyclerview.R;
import com.ekalips.annotations.Adapter;
import com.ekalips.annotations.Bind;
import com.ekalips.annotations.DataSetType;
import com.ekalips.annotations.ViewFor;

/**
 * Created by wldev on 8/4/17.
 */


@Adapter(dataSetType = @DataSetType(dataSetType = String.class),
        useViews = {@ViewFor(dataClass = Object.class, viewRes = R.layout.activity_main),
                @ViewFor(dataClass = String.class, viewRes = R.layout.rv_item_test_adapter)},
        map = @Bind(bindClass = Object.class, bindingResourceName = "br"))
public class TestAdapter2 {
}
