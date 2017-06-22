package com.ipaylinks.mobile.ipaylinkssdk.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.ipaylinks.mobile.ipaylinkssdk.R;

public class test extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("bb", "onCreate: 0");
        setContentView(R.layout.activity_test);
        Log.e("bb", "onCreate: 1");
    }
}
