package com.example.localsale.data.CloudDatabase;

import android.content.ClipData;
import android.content.Intent;
import android.util.Log;

import com.example.localsale.ui.orderPlesk.ItemInOrderList;
import com.example.localsale.ui.shoppingPlesk.ItemCategories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Result;

public class DBConnector {
    private static final String TAG ="DBConnector";
    public static Connection getConnection(String dbName,String password){
        Connection connection=null;
        try{
            Log.i(TAG,"before connect ");
            Class.forName("com.mysql.jdbc.Driver");
            String ip ="106.54.98.211";
            connection = DriverManager.getConnection(
                    "jdbc:mysql://" + ip +":3306/"+dbName,
                    dbName,password
            );
            Log.i(TAG,"connect successful");
            return connection;
        }catch (SQLException ex){
            Log.e(TAG,"SQLConnect",ex );
        }catch (ClassNotFoundException ex){
            Log.e(TAG,"ClassNotFound",ex );
        }
        return null;
    }

    public static ItemCategories getItemCategoriesFromDB(String dbName , String password , String sql) throws Exception {

        Connection connection = DBConnector.getConnection(dbName,password);
        if(connection ==null){
            throw new Exception("connection failed");
        }
        try{
            Statement st=(Statement)connection.createStatement();
            ResultSet mResultSet=st.executeQuery(sql);
            ItemCategories itemCategories = new ItemCategories();
            while(mResultSet .next()){//读表mytable中的每一列
                int ID = mResultSet.getInt(1);
                String name=mResultSet .getString(2);
                String kind = mResultSet.getString(3);
                float price = mResultSet.getFloat(4);
                String description = mResultSet.getString(5);
                Log.i(TAG,kind+name+price+description);
                itemCategories.InsetIntoCategory(kind,ItemCategories.Item.createItem(ID,name,price,description));
            }
            /*mResultSet=st.executeQuery("select * from orderlist;");
            List<ItemInOrderList> mList = new ArrayList<>();
            while(mResultSet .next()){//读表mytable中的每一列

                ItemInOrderList item = new ItemInOrderList();
                item.setOrderID(mResultSet.getString(1));
                Log.i("TAG",mResultSet.getString(1));
            }*/
            connection.close();
            st.close();
            mResultSet.close();
            return itemCategories;


        }catch (SQLException ex){
            Log.e(TAG,"@_______@",ex);
        }
        finally {

        }
        return null;

    }
    public static List getOrderFromDB(String dbName , String password , String sql) throws Exception {

        Connection connection = DBConnector.getConnection(dbName,password);
        if(connection ==null){
            throw new Exception("connection failed");
        }
        try{
            Statement st=(Statement)connection.createStatement();
            ResultSet mResultSet=st.executeQuery(sql);
            List<ItemInOrderList> mList = new ArrayList<>();
            while(mResultSet .next()){//读表mytable中的每一列

                ItemInOrderList item = new ItemInOrderList();
                item.setOrderID(mResultSet.getString(1));
                for(int i=1;i<mResultSet.getMetaData().getColumnCount();i++){
                    item.DBToItemInOrderList(i-1,mResultSet.getInt(i+1));
                }
                mList.add(item);
            }
            connection.close();
            st.close();
            mResultSet.close();
            return mList;


        }catch (SQLException ex){
            Log.e(TAG,"@_______@",ex);
        }
        finally {

        }
        return null;

    }

}
