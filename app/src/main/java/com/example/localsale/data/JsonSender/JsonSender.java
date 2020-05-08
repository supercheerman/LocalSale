package com.example.localsale.data.JsonSender;

import android.util.Log;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class JsonSender {

    public static void sendToPHP(JSONObject jsonObject){
        try{
            String  path="http://106.54.98.211/sql.php";
            HttpURLConnection connection =(HttpURLConnection) new URL(path).openConnection();
            connection.setRequestMethod("POST");
            connection.connect();
            OutputStream outputStream = connection.getOutputStream();
            DataOutputStream dataOutputStream =new DataOutputStream(outputStream);
            dataOutputStream.writeBytes(jsonObject.toString());
            dataOutputStream.flush();
            outputStream.close();
            dataOutputStream.close();
            InputStream inputStream = connection.getInputStream();
            byte [] m= new byte[1000];
            int number = inputStream.read(m);
            Log.i("TAG:php number",number+"");
            Log.i("TAG:php",new String(m,0,number));
        }catch (MalformedURLException ex){
            Log.i("TAG","@_____@",ex);
        }catch (IOException ex){
            Log.i("TAG","@_____@",ex);
        }

    }
}
