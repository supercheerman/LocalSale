package com.example.localsale.data.CloudDatabase;

import android.content.ClipData;
import android.util.Log;

import com.example.localsale.ui.shoppingPlesk.ItemCategories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.xml.transform.Result;

public class DBConnector {
    private static final String TAG ="DBConnector";
    public static Connection getConnection(String dbName,String password){
        Connection connection;
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

    public static ItemCategories getSqlResultSet(String dbName , String password , String sql) throws Exception {

        Connection connection = DBConnector.getConnection(dbName,password);
        if(connection ==null){
            throw new Exception("connection failed");
        }
        ItemCategories itemCategories = new ItemCategories();
        try{
            Statement st=(Statement)connection.createStatement();
            ResultSet mResultSet=st.executeQuery(sql);

            while(mResultSet .next()){//读表mytable中的每一列
                String kind = mResultSet.getString(1);
                String name=mResultSet .getString(2);
                float price = mResultSet.getFloat(3);
                String description = mResultSet.getString(4);
                Log.i(TAG,kind+name+price+description);
                itemCategories.InsetIntoCategory(kind,DBCHelper.createItem(name,price,description));
            }
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
}
