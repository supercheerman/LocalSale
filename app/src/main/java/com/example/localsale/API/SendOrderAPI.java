package com.example.localsale.API;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

public class SendOrderAPI {

    static final  String Password ="235huangWeiHao1265";

    public static void SendOrder(JSONObject jsonObject){


        String  path="http://106.54.98.211:88/addOrder.php";
        String returnInfo = WritePacket(path,jsonObject);
        Log.i("TAG:php",returnInfo);
        /*if(returnInfo.contains("包含") ){
            Log.i("TAG:php","成功写入");
        }else{
            Log.i("TAG:php","密令不对");
        }*/



    }
    private static HttpURLConnection initialConnection(String url) throws IOException{

        HttpURLConnection connection =(HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("POST");
        connection.connect();
        return connection;
    }

    public static String WritePacket(String url,JSONObject jsonObject){
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
