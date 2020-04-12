package com.example.localsale.data.CloudDatabase;

import android.util.Log;

import com.example.localsale.ui.shoppingPlesk.ItemCategories;

import java.util.List;

public class DBCHelper {

    private static final String TAG ="DBCHelper";
    private  String dbName= "loacalSales";
    private  String password = "h1587966";

    public ItemCategories getItemCategoriesFromDB(){
        try{
            return DBConnector.getItemCategoriesFromDB(dbName,password, "select * from foodtable");
        }catch (Exception ex){
            Log.e(TAG,"SQLConnect",ex );
        }
        return null;
    }
    public List getTotalOrderList(){
        try{
            return DBConnector.getOrderFromDB(dbName,password, "select * from orderlist");
        }catch (Exception ex){
            Log.e(TAG,"SQLConnect",ex );
        }
        return null;
    }




}
