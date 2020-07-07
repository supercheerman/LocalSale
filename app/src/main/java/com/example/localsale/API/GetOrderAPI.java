package com.example.localsale.API;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.example.localsale.ui.TotalOrderPlesk.OrderList;
import com.example.localsale.ui.orderPlesk.ItemInOrderList;
import com.example.localsale.ui.shoppingPlesk.ItemCategories;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Random;


/**
 * getOrder发送信息的格式
 *
 * 接收信息的格式
 *
 */
public class GetOrderAPI {

    static final  String Password ="235huangWeiHao1265";

    public static OrderList getOrderFromBD(JSONObject jsonObject){

        Log.i("TAG:GetOrder:info",jsonObject.toString());
        String  path="http://106.54.98.211:88/getOrder.php";
        String returnInfo = getOrder(path,jsonObject);
        OrderList orderList = new OrderList();
        try{
            JSONArray jsonArray = new JSONArray(returnInfo);
            for(int i=0;i<jsonArray.length();i++){
                JSONObject object = (JSONObject) jsonArray.get(i);
                ItemInOrderList itemInOrderList = new ItemInOrderList();
                itemInOrderList.setDeliverTime(object.getString("expectedtime"));
                itemInOrderList.setOrderID(object.getString("id"));
                JSONArray itemArray =new JSONArray(object.getString("commodity"));
                for(int j=0;j<itemArray.length();j++){
                    JSONObject item = (JSONObject) itemArray.get(j);
                     itemInOrderList.DBToItemInOrderList(item.getInt("id"),item.getInt("quantity"));

                }
                Log.i("TAG:GetOrder:info",itemInOrderList.toString());
                orderList.addOrder(itemInOrderList);
        }
        }catch (JSONException ex){
            Log.e("TAG","@_____@",ex);
        }

        return orderList;

    }


    public static HttpURLConnection initialConnection(String url) throws IOException {

        HttpURLConnection connection =(HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("POST");
        connection.connect();
        return connection;
    }

    public static String getOrder(String url, JSONObject jsonObject){
        try{
            HttpURLConnection connection = initialConnection(url);
            OutputStream outputStream = connection.getOutputStream();
            DataOutputStream dataOutputStream =new DataOutputStream(outputStream);
            dataOutputStream.writeBytes(addAuthInfo(jsonObject).toString());
            outputStream.close();
            dataOutputStream.close();
            InputStream inputStream = connection.getInputStream();
            StringBuffer ItemInfo = new StringBuffer();
            byte [] m= new byte[1000];
            int number = inputStream.read(m);
            ItemInfo.append(new String(m,0,number));
            while(!ItemInfo.toString().contains("&endpoint&")){
                Log.i("TAG:php available",inputStream.available()+"");
                number = inputStream.read(m);
                ItemInfo = ItemInfo.append(new String(m,0,number));
            }
            return ItemInfo.toString().replace("&endpoint&","");
        } catch (IOException e){
            Log.e("TAG","@_____@",e);
        }catch (JSONException e){
            Log.e("TAG","@_____@",e);
        }
        return null;

    }
    private static JSONObject addAuthInfo(JSONObject jsonObject) throws JSONException {
        int rmd = new Random().nextInt(Integer.MAX_VALUE);
        Log.i("TAG:random",rmd+"");
        Long Ts = System.currentTimeMillis();
        Log.i("TAG:ts",Ts+"");
        String AuthString = rmd+Password+Ts;
        Log.i("TAG:auth",AuthString);
        String fragment = AuthString+"?"+AuthString.indexOf(Password);
        jsonObject.put("authInfo",fragment);
        return jsonObject;
    }
}
