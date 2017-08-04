package com.ekalips.annotationbindingrecyclerview.recycler_view_stuff;


import com.ekalips.annotationbindingrecyclerview.R;
import com.ekalips.annotationbindingrecyclerview.data.TestData;
import com.ekalips.annotations.Adapter;
import com.ekalips.annotations.Bind;
import com.ekalips.annotations.BindMode;
import com.ekalips.annotations.DataSetType;
import com.ekalips.annotations.MethodCallMode;
import com.ekalips.annotations.ViewFor;

/**
 * Created by wldev on 8/1/17.
 */

@Adapter(
        dataSetType = @DataSetType(dataSetType = TestData.class),
        useViews = {
                @ViewFor(dataClass = TestData.class, viewRes = R.layout.rv_item_test_adapter)
        },
        map = {@Bind(bindClass = TestData.class, bindMode = BindMode.variable, bindingResourceName = "testData"),
                @Bind(bindClass = Void.class, bindMode = BindMode.method, bindingResourceName = "clickAdapter", methodName = "myMethod", methodCallMode = MethodCallMode.onClick)})
public class TestAdapter {

}
