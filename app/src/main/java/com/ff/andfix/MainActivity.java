package com.ff.andfix;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.io.File;

/**
 * 目前不支持4.4以下，5.0以上
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.result);

        findViewById(R.id.bt_calculator).setOnClickListener(this);
        findViewById(R.id.bt_fix).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_calculator:
                Calculator calculator = new Calculator();
                tv.setText(String.valueOf(calculator.calculator()));
                break;
            case R.id.bt_fix:
                File file = new File(Environment.getExternalStorageDirectory(), "fix.dex");
                new DexManager().load(this, file);
                break;
        }
    }
}
