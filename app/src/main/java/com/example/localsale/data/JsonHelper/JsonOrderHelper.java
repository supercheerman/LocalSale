package com.example.localsale.data.JsonHelper;

import android.util.Log;

import com.example.localsale.ui.orderPlesk.ItemInOrderList;
import com.example.localsale.ui.shoppingPlesk.ItemCategories;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonOrderHelper {

    public static JSONObject ItemInOrderList2JsonObject(ItemInOrderList itemInOrderList, String username) {

        JSONObject object = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        try{
            int size =itemInOrderList.getSize();
            for(int i =0;i<size;i++){
                ItemCategories.Item item =itemInOrderList.getItemFromInteger(i);
                JSONObject tmp = new JSONObject();
                tmp.put("id",item.getID());
                tmp.put("quantity",item.getNumber());
                jsonArray.put(tmp);
            }

            object.put("time",itemInOrderList.getDeliverTime());
            object.put("order",jsonArray);
            object.put("username",username);
            object.put("userAddress",itemInOrderList.getAddressInfo().getDormitory());
            object.put("userAddress2",itemInOrderList.getAddressInfo().getRoomNumber());
            object.put("price",itemInOrderList.getTotalPrice());
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
