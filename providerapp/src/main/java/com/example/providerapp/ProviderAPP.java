package com.example.providerapp;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ProviderAPP extends ContentProvider{
    private static final String TAG = "ProviderAPP";
    private static  final String AUTHORITY = "com.example.providerapp";

    private DB_helper db_helper;
    private SQLiteDatabase db;

    @Override
    public boolean onCreate() {
        db_helper = new DB_helper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        Log.e(TAG, "insert:  uri = " + uri);
        db = db_helper.getWritableDatabase();
        long id = db.insert("user", null, contentValues);
        db.close();
        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        Log.e(TAG, "delete:  uri = " + uri);
        SQLiteDatabase db = db_helper.getWritableDatabase();
        int count = db.delete("user", s, strings);
        db.close();
        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        Log.e(TAG, "update:  uri = " + uri);
        SQLiteDatabase db = db_helper.getWritableDatabase();
        int count = db.update("user", contentValues, s, strings);
        db.close();
        return count;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        Log.e(TAG, "query:  uri = " + uri);
        db = db_helper.getReadableDatabase();
        return db.query("user", strings, s, strings1, null, null, s1);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }
}