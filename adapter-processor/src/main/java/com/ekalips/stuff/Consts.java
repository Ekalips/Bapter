package com.ekalips.stuff;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeVariableName;

/**
 * Created by wldev on 8/2/17.
 */

public class Consts {
    public static final String FIELD_DATA_NAME = "data";

    public static final String CLASS_NAME_VIEW_TYPE = "ViewTypeEnum";
    public static final String CLASS_NAME_BR_GETTER = "BRGetter";
    public static final String METHOD_GET_VIEW_TYPE_NAME = "getItemViewType";
    public static final String METHOD_CREATE_VIEW_HOLDER = "onCreateViewHolder";
    public static final String METHOD_BIND_VIEW_HOLDER = "onBindViewHolder";
    public static final String CLASS_NAME_BINDING_VIEW_HOLDER = "BindingViewHolder";

    public static final String METHOD_GET_SIZE = "getItemCount";
    public static final String METHOD_GET_BR_FROM_NAME = "getBRForName";
    public static final String CLASS_NAME_CLICK_ADAPTER = "ClickAdapter";
    public static final String CLASS_NAME_LONG_CLICK_ADAPTER = "LongClickAdapter";
    public static final String DEFAULT_PACKAGE_NAME = "com.ekalips";
    public static final String DATABINDING_PACKAGE_NAME = "android.databinding";

    public static final TypeVariableName BindingView = TypeVariableName.get("BindingView", ClassName.get(DATABINDING_PACKAGE_NAME, "ViewDataBinding"));
    public static final ClassName RecyclerViewViewHolder = ClassName.get("android.support.v7.widget.RecyclerView", "ViewHolder");
    public static final ClassName RecyclerViewAdapter = ClassName.get("android.support.v7.widget.RecyclerView", "Adapter");
    public static final ClassName RecyclerView = ClassName.get("android.support.v7.widget", "RecyclerView");
    public static final ClassName View = ClassName.get("android.view", "View");
    public static final ClassName ViewGroup = ClassName.get("android.view", "ViewGroup");
    public static final ClassName DataBindingUtil = ClassName.get(DATABINDING_PACKAGE_NAME, "DataBindingUtil");
    public static final ClassName LayoutInflater = ClassName.get("android.view", "LayoutInflater");
    public static final ClassName BindingViewHolder = ClassName.get(Consts.DEFAULT_PACKAGE_NAME, "BindingViewHolder");
    public static final ClassName ArrayList = ClassName.get("java.util", "ArrayList");
    public static final ClassName List = ClassName.get("java.util", "List");
    public static final String CLASS_NAME_DATA_SET = "DataSetInterface";
    public static final ClassName DataSetInterface = ClassName.get(Consts.DEFAULT_PACKAGE_NAME, Consts.CLASS_NAME_DATA_SET);
    public static final String METHOD_SET_DATA = "setData";
    public static final String CLASS_NAME_BINDINGS = "Bindings";
    public static final ClassName BRGetter = ClassName.get(Consts.DEFAULT_PACKAGE_NAME, Consts.CLASS_NAME_BR_MAPPER);
    public static final String CLASS_NAME_BR_MAPPER = "AnnotationBRMapper";
}
