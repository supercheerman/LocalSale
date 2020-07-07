package com.example.localsale.API;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.example.localsale.ui.shoppingPlesk.ItemCategories;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class GetItemInfoAPI {

    public static ItemCategories getItemCategoriesFromBD(){


        String  path="http://106.54.98.211:88/getItemInfo.php";
        String returnInfo = getItemInfo(path);
        Log.i("TAG:php:return info",returnInfo);
        List<ItemCategories.Item> jsonArray = JSON.parseArray(returnInfo, ItemCategories.Item.class);
        Log.i("TAG:php",jsonArray.size()+"");
        ItemCategories itemCategories = new ItemCategories();
        for(int i =0;i<jsonArray.size();i++){
            Log.i("TAG:php",jsonArray.get(i).getName()+" "+jsonArray.get(i).getType());
            itemCategories.InsetIntoCategory(jsonArray.get(i).getType(),jsonArray.get(i));
        }

        if(returnInfo.contains("包含") ){
            Log.i("TAG:php","成功写入");
        }else{
            Log.i("TAG:php","密令不对");
        }

        return itemCategories;

    }
    public static HttpURLConnection initialConnection(String url) throws IOException {

        HttpURLConnection connection =(HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("POST");
        connection.connect();
        return connection;
    }

    public static String getItemInfo(String url){
        try{
            HttpURLConnection connection = initialConnection(url);
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
        }
        return null;

    }
}
