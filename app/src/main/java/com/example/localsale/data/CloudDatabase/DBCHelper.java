package com.example.localsale.data.CloudDatabase;

import android.content.ClipData;
import android.util.Log;

import com.example.localsale.ui.shoppingPlesk.ItemCategories;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBCHelper {
    private static final String TAG ="DBCHelper";
    private String dbName= "loacalSales";
    private String password = "h1587966";
    private String mSql;

    public  DBCHelper (String sql){
       mSql= sql;
    }

    public ItemCategories readResult(){


        try{
            ItemCategories  itemCategories = DBConnector.getSqlResultSet(dbName,password,mSql);
            return itemCategories ;
        }catch (Exception ex){
            Log.e(TAG,"TAG"+ex);
        }
        return null;


    }

    public static ItemCategories.Item createItem(String name,float price,String description){
        ItemCategories.Item item = new ItemCategories.Item();
        item.setName(name);
        item.setDescription(description);
        item.setPrice(price);
        return item;
    }



}
