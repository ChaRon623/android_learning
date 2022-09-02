package com.example.resolverapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.providerapp.DB_helper;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "ResolverAPP";
    private Uri uri = Uri.parse("content://com.example.providerapp.ProviderAPP");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button_addData).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put(DB_helper.STUDENT_NAME, "陈飞龙");
                values.put(DB_helper.STUDENT_AGE, "22");
                getContentResolver().insert(uri, values);
                values.clear();
                values.put(DB_helper.STUDENT_NAME, "向均鸿");
                values.put(DB_helper.STUDENT_AGE, "23");
                getContentResolver().insert(uri, values);
                values.clear();
                values.put(DB_helper.STUDENT_NAME, "张超");
                values.put(DB_helper.STUDENT_AGE, "22");
                getContentResolver().insert(uri, values);
                values.clear();
                values.put(DB_helper.STUDENT_NAME, "雷海星");
                values.put(DB_helper.STUDENT_AGE, "23");
                getContentResolver().insert(uri, values);
                values.clear();
                System.out.println("即将打印添加数据成功的Toas");
                Toast.makeText(getApplicationContext(),"四条学生信息已添加（他们都是我本科室友）",Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.button_queryData).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentResolver contentResolver=getContentResolver();
                Cursor cursor = contentResolver.query(uri, null, null, null, null);
                String display="";
                while (cursor.moveToNext()) {
                    display+=cursor.getString(0) + ", "
                            + cursor.getString(1) + ", "
                            + cursor.getString(2);

                    //Log.i("ResovlerAPP", "onClick: " + display);
                }
                System.out.println("display = " + display);
                Toast.makeText(getApplicationContext(), display, Toast.LENGTH_LONG).show();
            }
        });

        findViewById(R.id.button_updateData).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentResolver contentResolver=getContentResolver();
                ContentValues values1=new ContentValues();
                values1.put(DB_helper.STUDENT_NAME, "邝耀文");
                values1.put(DB_helper.STUDENT_AGE, "22");
                contentResolver.update(uri,values1,"id?",new String[]{"1"});
                Toast.makeText(getApplicationContext(), "已将张超更改为邝耀文，请查看控制栏输出", Toast.LENGTH_LONG).show();
            }
        });
    }
}