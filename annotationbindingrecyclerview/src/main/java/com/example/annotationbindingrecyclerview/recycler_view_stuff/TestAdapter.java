package com.example.annotationbindingrecyclerview.recycler_view_stuff;


import com.example.annotationbindingrecyclerview.R;
import com.example.annotationbindingrecyclerview.data.TestData;
import com.example.annotations.Adapter;
import com.example.annotations.Bind;
import com.example.annotations.BindMode;
import com.example.annotations.DataSetType;
import com.example.annotations.MethodCallMode;
import com.example.annotations.ViewFor;

/**
 * Created by wldev on 8/1/17.
 */

@Adapter(
        dataSetType = @DataSetType(dataSetType = TestData.class),
        useViews = {
                @ViewFor(dataClass = TestData.class, viewRes = R.layout.rv_item_test_adapter)
        },
        map = {@Bind(bindClass = TestData.class, bindMode = BindMode.variable, bindingResourceName = "testData"),
                @Bind(bindClass = Void.class, bindMode = BindMode.method, bindingResourceName = "clickAdapter", methodName = "myMethod", methodCallMode = MethodCallMode.onClick),
        @Bind(bindClass = Void.class, bindMode = BindMode.method, bindingResourceName = "clickAdapter", methodName = "myMethod2", methodCallMode = MethodCallMode.onLongClick)})
public class TestAdapter {

}
