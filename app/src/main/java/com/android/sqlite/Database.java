package com.android.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {
    //name là tên database của mình
    //CursorFactory dạng con trỏ để duyệt dữ liệu
    //version phiên bản
    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //truy vấn không trả kết quả:CREATE , INSERT , DELETE, UPDATE,...
    //sql truyền vào kiểu truy vấn của mình
    public void QueryData(String sql){
             SQLiteDatabase database = getWritableDatabase();
             database.execSQL(sql);
    }

    //truy vấn sẽ trả kết quả :SELECT
    //getWrite gần vào database lúc ghi nó đọc cũng đc
    //getRead nó đọc database thôi
    public Cursor getData(String sql){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql,null);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
