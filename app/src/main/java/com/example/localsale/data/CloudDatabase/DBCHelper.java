package com.example.localsale.data.CloudDatabase;

import android.util.Log;

import com.example.localsale.ui.shoppingPlesk.ItemCategories;

import java.util.List;

public class DBCHelper {

    private static DBCHelper sDBCHelper;
    private static final String TAG ="DBCHelper";
    private  String dbName= "loacalSales";
    private  String password = "h1587966";


    private DBCHelper(){}
    public static DBCHelper getDBCHelper(){
        if(sDBCHelper==null){
            sDBCHelper= new DBCHelper();
        }
        return sDBCHelper;
    }

    public ItemCategories getItemCategoriesFromDB(){
        try{
            return DBConnector.getItemCategoriesFromDB(dbName,password, "select * from foodtable;");
        }catch (Exception ex){
            Log.e(TAG,"SQLConnect",ex );
        }
        return null;
    }
    public List getTotalOrderList(){
        try{
            return DBConnector.getOrderFromDB(dbName,password, "SELECT * FROM `orderlist`");
        }catch (Exception ex){
            Log.e(TAG,"SQLConnect",ex );
        }
        return null;
    }




}
