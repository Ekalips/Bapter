package com.example.annotationbindingrecyclerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.ekalips.BindingViewHolder;
import com.example.annotationbindingrecyclerview.data.TestData;
import com.example.annotationbindingrecyclerview.recycler_view_stuff.TestAdapterGenerated;

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

            @Override
            public Void myMethod2(int position, BindingViewHolder holder, Object object) {
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
