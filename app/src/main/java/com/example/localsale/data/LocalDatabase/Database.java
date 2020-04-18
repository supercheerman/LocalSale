package com.example.localsale.data.LocalDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.localsale.data.LocalDatabase.DbSchema.*;
import com.example.localsale.data.UserInfo.UserInfo;
import com.example.localsale.ui.TotalOrderPlesk.OrderList;
import com.example.localsale.ui.addressPlesk.AddressList;
import com.example.localsale.ui.orderPlesk.ItemInOrderList;
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




    /*
     * @param null
     * @return
     * @author hwh
     * @date 2020/4/14
     * @Description  将itemInOrderList对象中的订单对象存储到本地数据库中啊
     **/
    public void addOrder(ItemInOrderList itemInOrderList){
        ContentValues values= new ContentValues();
        values.put(OlderInfoTable.Cols.OrderID,ItemInOrderList.getOrderIdByTime());

        for(int j=0;j<OlderInfoTable.NUMBER;j++){
            values.put("Item"+j,0);
        }
        for(int i =0;i<itemInOrderList.getSize();i++){
            ItemCategories.Item item = itemInOrderList.getItemFromInteger(i);
            values.remove("Item"+item.getID());
            values.put("Item"+item.getID(),item.getNumber());
        }
        Log.i("TAG OrderInfo",itemInOrderList.toString()+" "+values.toString());
        mDatabase.insert(OlderInfoTable.NAME,null,values);

    }


    public void getOrdersInfoFromTables() {
        OrderInfoCursorWrapper cursor =OrderInfoCursorWrapper.queryOrders(mDatabase,null,null);
        try{
            cursor.moveToFirst();
            OrderList.getOrderList().clearList();
            while (!cursor.isAfterLast()){
                OrderList.getOrderList().addOrder( cursor.getLocalOrderList());
                cursor.moveToNext();
            }
        }finally {
            cursor.close();
        }

    }


    public void addAddress(String dormitory, String roomNumber,String name,String phoneNumber){
        ContentValues values= new ContentValues();
        values.put(addressInfotable.Cols.Dormitory,dormitory);
        values.put(addressInfotable.Cols.RoomNumber,roomNumber);
        values.put(addressInfotable.Cols.Name,name);
        values.put(addressInfotable.Cols.PhoneNumber,phoneNumber);
        mDatabase.insert(OlderInfoTable.NAME,null,values);
    }




    public void getAddressInfoFromTables() {
        AddressInfoCursorWrapper cursor =AddressInfoCursorWrapper.queryOrders(mDatabase,null,null);
        try{
            cursor.moveToFirst();
            AddressList.getAddressList().Clear();
            while (!cursor.isAfterLast()){
                AddressList.getAddressList().addAddressInfo( cursor.getLocalAddressList());
                cursor.moveToNext();
            }
        }finally {
            cursor.close();
        }

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
            db.execSQL(createOrderTableSql());
            db.execSQL("create table "+ addressInfotable.NAME + "(" +
                    "_id integer primary key autoincrement, " +
                    addressInfotable.Cols.Dormitory + ", " +
                    addressInfotable.Cols.RoomNumber +", "+
                    addressInfotable.Cols.Name +", "+
                    addressInfotable.Cols.PhoneNumber +
                    ")");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
        private String createOrderTableSql(){
            String  tmp = "create table "+ OlderInfoTable.NAME + "("+"_id integer primary key autoincrement, ";
            tmp = tmp + OlderInfoTable.Cols.OrderID+",";
            for(int i =0;i<OlderInfoTable.NUMBER-1;i++){
                tmp+="Item"+i+", ";
            }
            tmp+="Item"+(OlderInfoTable.NUMBER-1)+")";
            return tmp;
        }
    }





}
