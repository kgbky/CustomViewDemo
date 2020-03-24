package com.example.customviewapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.baseButton).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
    }

    public void test(View view) {
        Toast.makeText(this, "HADADAAA", Toast.LENGTH_SHORT).show();
    }

    public void test2(View view) {
        Toast.makeText(this, "BBBBBBBB", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            Log.d(TAG, "dispatchTouchEvent: " + ev.getAction());
        }
        //这里是仿照源码的格式写的
        if (getWindow().superDispatchTouchEvent(ev)) {
            Log.d(TAG, "dispatchTouchEvent: 这里被调用" + ev.getAction());
            return true;
        }
        return onTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "onTouchEvent: " + event.getAction());
        return super.onTouchEvent(event);
    }

}
