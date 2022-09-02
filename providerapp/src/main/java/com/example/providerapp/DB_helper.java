package com.example.providerapp;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DB_helper extends SQLiteOpenHelper {
    private static final String  DATABASE_NAME="student.db";
    public static final String  TABLE_NAME="user";
    private static final int DATABASE_VERSION=1;

    //列名
    public static final String STUDENT_ID = "ID";
    public static final String STUDENT_NAME = "NAME";
    public static final String STUDENT_AGE = "AGE";

    private final int COL_TYPE_ID = 1;
    private final int COL_TYPE_NAME = 2;

    public DB_helper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlCreateDatabase = "create table " + TABLE_NAME
                + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + STUDENT_NAME + " TEXT,"
                + STUDENT_AGE + " TEXT)";
        db.execSQL(sqlCreateDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion>DATABASE_VERSION){
            onCreate(db);
        }
    }

    public boolean insertStudent(Student student) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues c = new ContentValues();
        c.put(STUDENT_NAME, student.getName());
        c.put(STUDENT_AGE, student.getAge());
        return db.insert(TABLE_NAME, null, c) != -1;
    }

    private List<Student> queryStudentBy(int queryTye, String param) {
        List<Student> students = new ArrayList<>();
        String sql = "select * from " + TABLE_NAME;
        if (queryTye == COL_TYPE_ID) {
            sql += " where ID = " + param;
        } else if (queryTye == COL_TYPE_NAME) {
            sql += " where NAME = " + param;
        }
        SQLiteDatabase db = getReadableDatabase();
        Cursor res = db.rawQuery(sql, null);
        res.moveToFirst();
        while (res.moveToNext()) {
            Student student = new Student();
            student.setId(res.getString(0));
            student.setName(res.getString(1));
            student.setAge(res.getString(2));
            students.add(student);
        }
        res.close();
        return students;
    }

    public List<Student> getAllStudent() {
        return queryStudentBy(0, null);
    }

    public boolean updateStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(STUDENT_ID, student.getId());
        contentValues.put(STUDENT_NAME, student.getName());
        contentValues.put(STUDENT_AGE, student.getAge());
        db.update(TABLE_NAME, contentValues, "ID = ?", new String[]{student.getId()});
        return false;
    }

    public boolean deleteStudentById(String id, String param) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?", new String[]{param}) > 0;
    }

}
