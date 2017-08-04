package com.ekalips.annotationbindingrecyclerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.ekalips.BindingViewHolder;
import com.ekalips.annotationbindingrecyclerview.data.TestData;
import com.ekalips.annotationbindingrecyclerview.recycler_view_stuff.TestAdapterGenerated;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TestAdapterGenerated adapter = new TestAdapterGenerated() {
            @Override
            public Void myMethod(int position, BindingViewHolder holder, Object object) {
                Log.d(TAG, "myMethod: " + ((TestData) object).getTest());
                return null;
            }
        };



        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        List<TestData> dataList = new ArrayList<>();
        dataList.add(new TestData("teset1"));
        dataList.add(new TestData("teset2"));
        dataList.add(new TestData("teset3"));
        dataList.add(new TestData("teset4"));
        adapter.setData(dataList);

//        TestAdapter adapter = new TestAdapter();

    }
}
