package com.example.localsale.data.LocalDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.localsale.data.LocalDatabase.DbSchema.*;
import com.example.localsale.data.UserInfo.UserInfo;
import com.example.localsale.ui.shoppingPlesk.ItemCategories;

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

    public void addCategories(ItemCategories itemCategories){
        List<ItemCategories.ItemCategory> categoryList =  itemCategories.getItemCategoryList();
        int size = categoryList.size();
        for(int i=0;i<size;i++){
            List<ItemCategories.Item> items = categoryList.get(i).getItems();
            String category = categoryList.get(i).getCategory();
            int tmp =items.size();
            for(int i1 = 0;i1<tmp;i1++){
                addItem(category,items.get(i1));
            }
        }
    }
    public void addItem(String category, ItemCategories.Item item){

        ContentValues values= new ContentValues();
        values.put(ItemInfoTable.Cols.NAME,item.getName());
        values.put(ItemInfoTable.Cols.CATEGORY,category);
        values.put(ItemInfoTable.Cols.PRICE,item.getPrice());
        values.put(ItemInfoTable.Cols.DESCRIPTION,item.getDescription());
        mDatabase.insert(ItemInfoTable.NAME,null,values);
    }

    public ItemCategories getLocalDBCategories() {

        ItemCategories categories = new ItemCategories();
        ItemInfoCursorWrapper cursor = ItemInfoCursorWrapper.queryCrimes(mDatabase,null,null);
        try{
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                categories.InsetIntoCategory(cursor.getLocalDBCategory(),cursor.getLocalDBItem());
                cursor.moveToNext();
            }
        }finally {
            cursor.close();
        }
        return categories;

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
            db.execSQL("create table "+ ItemInfoTable.NAME + "(" +
                    "_id integer primary key autoincrement, " +
                    ItemInfoTable.Cols.NAME + ", " +
                    ItemInfoTable.Cols.CATEGORY +", "+
                    ItemInfoTable.Cols.PRICE +", "+
                    ItemInfoTable.Cols.DESCRIPTION +
                    ")");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }





}
