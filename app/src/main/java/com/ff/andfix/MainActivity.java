package com.ff.andfix;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

/**
 * 目前不支持4.4以下，5.0以上
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String[] PERMS = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private static final String TAG = "MainActivity";
    private TextView tv;
    private FixTask mTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.result);

        findViewById(R.id.bt_calculator).setOnClickListener(this);
        findViewById(R.id.bt_fix).setOnClickListener(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == 200) {
            for (int i : grantResults) {
                if (i == PackageManager.PERMISSION_DENIED) {
                    Toast.makeText(this, "权限拒绝", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_calculator:
                Calculator calculator = new Calculator();
                tv.setText(String.valueOf(calculator.calculator()));
                break;
            case R.id.bt_fix:
                if (getRuntimePermission()) {
                    mTask = new FixTask();
                    mTask.execute(MainActivity.this);
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        if (mTask != null) {
            mTask.cancel(true);
        }
        super.onDestroy();
    }

    /**
     * 获得运行时权限
     */
    private boolean getRuntimePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(PERMS[0]) == PackageManager.PERMISSION_DENIED) {
                requestPermissions(PERMS, 200);
                return false;
            }
        }
        return true;
    }

    private static class FixTask extends AsyncTask<Context, Void, String> {

        @Override
        protected void onPreExecute() {
            Log.d(TAG, "开始修复");
        }

        @Override
        protected String doInBackground(Context... contexts) {
            File file = new File(Environment.getExternalStorageDirectory(), "fix.dex");
            try {
                new DexManager().load(contexts[0], file);
            } catch (IOException e) {
                return "修复文件不存在";
            }
            return "修复完成";
        }

        @Override
        protected void onPostExecute(String result) {
            Log.d(TAG, result);
        }

        @Override
        protected void onCancelled() {
            Log.d(TAG, "取消修复");
        }
    }
}
