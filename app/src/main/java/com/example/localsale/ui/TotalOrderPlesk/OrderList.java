package com.example.localsale.ui.TotalOrderPlesk;

import com.example.localsale.data.ItemList;
import com.example.localsale.ui.orderPlesk.ItemInOrderList;

import java.util.ArrayList;
import java.util.List;

public class OrderList {

    private static OrderList sOrderList;
    private static List<ItemInOrderList> mList= new ArrayList<>();



    public static OrderList getOrderList(){
        if(sOrderList ==null){
            sOrderList= new OrderList();
            //mList = getDBCHelper().getTotalOrderList();
        }
        return sOrderList;
    }

    public void addOrder(ItemInOrderList item){
        mList.add(item);
    }
    public ItemInOrderList getOrder(int posit){
        return mList.get(posit);
    }
    public int getSize(){
        return mList.size();
    }
    public void clearList(){
        mList.clear();
    }

}
