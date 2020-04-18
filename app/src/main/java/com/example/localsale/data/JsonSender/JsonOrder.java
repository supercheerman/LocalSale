package com.example.localsale.data.JsonSender;

import android.content.ClipData;
import android.util.Log;

import com.example.localsale.ui.orderPlesk.ItemInOrderList;
import com.example.localsale.ui.shoppingPlesk.ItemCategories;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonOrder {


    public static JSONObject ItemInOrderList2JsonArray(ItemInOrderList itemInOrderList) {

        JSONObject object = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        try{
            int size =itemInOrderList.getSize();
            for(int i =0;i<size;i++){
                ItemCategories.Item item =itemInOrderList.getItemFromInteger(i);
                JSONObject tmp = new JSONObject();
                tmp.put("ItemID",item.getID());
                tmp.put("Number",item.getNumber());
                jsonArray.put(tmp);
            }
            object.put("order",jsonArray);
        }catch (JSONException ex){
            Log.e("TAG","@_____@",ex);
        }

        return object;

    }
    public static ItemInOrderList JsonArray2ItemInOrderList(JSONObject object){
        ItemInOrderList itemInOrderList = new ItemInOrderList();
        try{
            object.getJSONArray("order");
        }catch (JSONException ex){
            Log.e("TAG","@_____@",ex);
        }
        return itemInOrderList;
    }
}
