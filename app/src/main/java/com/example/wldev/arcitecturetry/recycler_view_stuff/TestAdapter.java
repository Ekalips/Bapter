package com.ekalips.wldev.arcitecturetry.recycler_view_stuff;

import com.ekalips.wldev.arcitecturetry.R;
import com.ekalips.wldev.arcitecturetry.data.TestData;
import com.ekalips.wldev.arcitecturetry.recycler_view_stuff.annotations.Adapter;
import com.ekalips.wldev.arcitecturetry.recycler_view_stuff.annotations.DataSetType;
import com.ekalips.wldev.arcitecturetry.recycler_view_stuff.annotations.ViewFor;

/**
 * Created by wldev on 8/1/17.
 */

@Adapter(
        dataSetType = @DataSetType(dataSetType = TestData.class),
        useViews = {
                @ViewFor(dataClass = TestData.class, viewRes = R.layout.rv_item_test_adapter)
        })
public class TestAdapter {
}
