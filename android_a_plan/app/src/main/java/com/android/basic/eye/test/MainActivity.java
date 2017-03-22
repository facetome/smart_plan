package com.android.basic.eye.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.android.basic.eye.R;
import com.basic.android.library.SmartPlan;

/**
 * Created by basic on 2017/3/14.
 */
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_test);
        DbHelperManger manger = DbHelperManger.getDbManger(this);
        manger.insert(1);
        manger.insert(2);
        manger.insert(3);
        TextView start = (TextView) findViewById(R.id.start_server);
        start.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                SmartPlan.startPlan(MainActivity.this);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SmartPlan.stopPlan(MainActivity.this);
        DbHelperManger.getDbManger(this).close();
    }
}
