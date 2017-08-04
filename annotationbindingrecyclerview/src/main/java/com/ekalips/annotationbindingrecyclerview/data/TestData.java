package com.ekalips.annotationbindingrecyclerview.data;

/**
 * Created by wldev on 8/1/17.
 */

public class TestData {

    private String test;

    public TestData(String test) {
        this.test = test;
    }

    public String getTest() {
        return test;
    }

    @Override
    public String toString() {
        return "TestData{" +
                "test='" + test + '\'' +
                '}';
    }
}
