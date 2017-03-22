package com.android.basic.eye.test;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * 数据库管理工具.
 */
public class DbHelperManger {

    private static SQLiteDatabase mDatabase;
    private static DbHelperManger mManger;
    private static int mCount = 0;

    //这里仅仅做测试使用，不适用单例来创建数据库
    private DbHelperManger(Context context) {
        DbHelper helper = new DbHelper(context);
        mDatabase = helper.getWritableDatabase();
    }

    public static DbHelperManger getDbManger(Context context) {
        mCount++;
        if (mManger == null) {
            mManger = new DbHelperManger(context);
        }
        return mManger;
    }

    /**
     * close.
     */
    public void close() {
        mCount--;
        if (mCount == 0) {
            mDatabase.close();
            mDatabase = null;
            mManger = null;
        }
    }

    /**
     * insert.
     *
     * @param name column
     */
    public synchronized void insert(double name) {
        ContentValues values = new ContentValues();
        values.put("username", name);
        values.put("age", 10);
        values.put("school", "北冥有鱼，其名为鲲，鲲之大，不知其几千里，化为鹏，鹏之大，不知其几千里也");
        values.put("sex", 1);
        mDatabase.insert("user", null, values);
    }



    public static class DbHelper extends SQLiteOpenHelper {

        public DbHelper(Context context) {
            this(context, "test.db", null, 1);
        }

        public DbHelper(Context context, String name, CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            StringBuilder builder = new StringBuilder();
            builder.append("create table user").append("(_id integer primary key autoincrement, ")
                    .append("username text, age integer, school text, sex integer);");
            db.execSQL(builder.toString());
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // do nothing now
        }
    }
}
