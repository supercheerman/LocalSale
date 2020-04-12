package com.example.localsale.ui.TotalOrderPlesk;

import com.example.localsale.data.CloudDatabase.DBCHelper;
import com.example.localsale.data.ItemList;
import com.example.localsale.ui.orderPlesk.ItemInOrderList;

import java.util.ArrayList;
import java.util.List;

public class OrderList {

    private static OrderList sOrderList;
    static List<ItemInOrderList> mList;

    public static OrderList getOrderList(){
        if(sOrderList ==null){
            sOrderList= new OrderList();
            mList = new DBCHelper().getTotalOrderList();
        }
        return sOrderList;
    }

}
