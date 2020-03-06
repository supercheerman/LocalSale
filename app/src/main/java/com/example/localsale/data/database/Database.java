package com.example.localsale.data.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.localsale.data.database.DbSchema.*;

import java.util.ArrayList;
import java.util.List;

public class Database {

    private static Database mStaticDatebase;
    private SQLiteDatabase mDatabase;

    private Database(Context context){
        mDatabase = new CreateTableHelper(context).getWritableDatabase();
    }

    public static Database getDatabase(Context context){
        if(mStaticDatebase==null){
            mStaticDatebase = new Database(context);
        }
        return mStaticDatebase;
    }
    public void addUser(String userName,String userPassword){

        ContentValues values= new ContentValues();
        values.put(UserInfoTable.Cols.USERNAME,userName);
        values.put(UserInfoTable.Cols.USER_PASSWORD,userPassword);
        mDatabase.insert(UserInfoTable.NAME,null,values);
    }
    public List<UserInfo> getUsers() {

        List<UserInfo> users =new ArrayList<>();
        UserInfoCursorWrapper cursor =UserInfoCursorWrapper.queryCrimes(mDatabase,null,null);
        try{
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                users.add(cursor.getUser());
                cursor.moveToNext();
            }
        }finally {
            cursor.close();
        }
        return users;

    }


    public class CreateTableHelper extends SQLiteOpenHelper{

        private static final int VERSION=1;
        private static final String DATABASE_NAME="LocalSales.db";
        public CreateTableHelper(Context context){
            super(context,DATABASE_NAME,null,VERSION);
        }


        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table "+ UserInfoTable.NAME + "(" +
                    "_id integer primary key autoincrement, " +
                    UserInfoTable.Cols.USERNAME + ", " +
                    UserInfoTable.Cols.USER_PASSWORD +
                    ")");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

    //该类用来保存查询后的结果
    public class PictureCursorWrapper extends CursorWrapper{

        public PictureCursorWrapper(Cursor cursor){super(cursor);}

        //

    }



}
