package com.example.localsale.ui.orderPlesk;

import android.util.Log;

import com.example.localsale.data.ItemInOrderList;
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
                tmp.put("Number",item.getID());
                jsonArray.put(tmp);
            }
            object.put("order",jsonArray);
        }catch (JSONException ex){
            Log.e("TAG","@_____@",ex);
        }

        return object;

    }
}
