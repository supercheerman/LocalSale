package com.example.localsale.data.CloudDatabase;

import android.util.Log;

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
            Class.forName("com.mysql.jdbc.Driver");
            String ip ="103.133.177.158";
            connection = DriverManager.getConnection(
                    "jdbc:mysql://" + ip +":3306/"+dbName,
                    "root",password
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

    public static void getSqlResultSet(Connection connection, String sql){
        try{
            Statement st=(Statement)connection.createStatement();
            ResultSet rs=st.executeQuery(sql);
            while(rs.next()){//读表mytable中的每一列
                String mybook=rs.getString("名称");//读取的是B_Name这一列，传给mybook
                Log.i(TAG,mybook);
            }
            connection.close();//一定要关闭
            st.close();
            rs.close();
        }catch (SQLException ex){
            Log.e(TAG,"@_______@",ex);
        }

    }
}
