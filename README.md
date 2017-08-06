# Bapter
This is small library with few annotations and processors that will generate all RecyclerView.Adapters stuff that you need with only few lines of annotations

# Usage

    @Adapter(
        dataSetType = @DataSetType(dataSetType = TestData.class),
        useViews = {
                @ViewFor(dataClass = TestData.class, viewRes = R.layout.rv_item_test_adapter)
        },
        map = {@Bind(bindClass = TestData.class, bindMode = BindMode.variable, bindingResourceName = "testData"),
                @Bind(bindClass = Void.class, bindMode = BindMode.method, bindingResourceName = "clickAdapter", methodName = "myMethod", methodCallMode = MethodCallMode.onClick)})
    public class TestAdapter {

    }

`@Adapter` - Main annotation that will contain other ones.

`dataSetType = @DataSetType(dataSetType = TestData.class)`

This will specify data that you'll use in your adapter. In this example, adapter will have `private List<TestData> data` as a data source.

` useViews = {
                @ViewFor(dataClass = TestData.class, viewRes = R.layout.rv_item_test_adapter)
        }`
        
Here you can specify what `getItemViewType` will return (based on data class) 

`map = {..}` - Annotation for specifying behaviour of `onBindViewHolder` method

`@Bind`

Basically all this is main thing that you carry about. 

`@Bind` can be 2 types (`bindMode`):
1) Bind as variable. This will generate simple `binding.setVariable(BR.., object)` based on `bindClass` (`if (object instacneof bindClass)..`);
2) Bind as method. Requires `methodName` specification for further abstract method generation. This can generate 3 things:

    a) Bind with return method (`methodCallMode = MethodCallMode.asReturn`). This will generate abstract method with specified method name and set it's return value as variable (`binding.setVariable(BR.., result)`);
    b) Bind as "onClick" (`methodCallMode = MethodCallMode.onClick`). This will set `View.OnClickListener` to your `BR` variable. And then when click occurs it'll call specified method ('methodName');
    c) Bind as "onLongClick" (`methodCallMode = MethodCallMode.onLongClick`). Same as previous, but with `View.OnLongClickListener`.
    
All this stuff will be generated as "$CLASS_NAME$Generated" (in my example "TestAdapterGenerated"). And you can simply create instance of this class, implement generated abstract methods (if any was requested) and set it as adapter to your `RecyclerView`

To set data to this adapter you have 2 options:
1) Just call `adapter.setData(...)` on your adapter instance.
2) Bind it. There is one `@BindingAdapter` that coems from box. You can bind any `List` with `src` annotation and `@BindingAdapter` will call `setData` for you. 

# How to get

    maven {
        url "http://dl.bintray.com/ekalips/Bapter"
    }

    compile 'com.ekalips:adapter-annotation:0.1.1'
    annotationProcessor 'com.ekalips:adapter-processor:0.1.1'
    
# Licence
    Copyright 2017 Ekalips

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
