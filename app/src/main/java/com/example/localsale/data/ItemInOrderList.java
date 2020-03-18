package com.example.localsale.data;

import android.util.Log;

import com.example.localsale.ui.shoppingPlesk.ItemCategories;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class ItemInOrderList {

    private static ItemInOrderList sItemInOrderList;

    HashMap<Integer,ItemCategories.Item> mItems = new HashMap<>();
    List<Integer> mIntegers =new ArrayList<>();

    private ItemInOrderList(){
    }

    public static ItemInOrderList getItemInOrderList(){
        if(sItemInOrderList==null){
            sItemInOrderList =new ItemInOrderList();
        }
        return sItemInOrderList;
    }
    public void addItemInToOrderList(int posit,ItemCategories.Item item){
        ItemCategories.Item  tmp = new ItemCategories.Item(item);
        tmp.setNumber(1);
        mIntegers.add(posit);
        mItems.put(posit,tmp);
    }
    public boolean HasItem(int posit){
        return  mItems.containsKey(posit);
    }

    public void IncreaseItemNumber(int posit){
        ItemCategories.Item item = mItems.get(posit);
        item.setNumber(item.getNumber()+1);
    }
    public void DecreaseItemNumber(int posit){
        ItemCategories.Item item = mItems.get(posit);
        item.setNumber(item.getNumber()-1);
    }
    public void DeleteItemFromList(int posit){
        mIntegers.remove(posit);
        mItems.remove(posit);
    }
    public boolean ItemNumberEqual1(int posit){

        int tmp =  mItems.get(posit).getNumber();

        return tmp==1;
    }
    public int getItemNumber(int posit){
        Log.i("TAG",posit+" "+mItems+"  ");
        return mItems.get(posit).getNumber();
    }


    public ItemCategories.Item getItemFromInteger(int posit) {
        return mItems.get(mIntegers.get(posit));
    }

    /*
     * 当商品数目为0时返回true
     *
     * */
    public boolean addNumberInItem(int position){

        Log.i("TAG",position+"  ");
        if(!sItemInOrderList.HasItem(position)){
            ItemCategories.Item item = ItemCategories.getItemCategories().getItem(position);
            sItemInOrderList.addItemInToOrderList(position,item);
            //
            return true;
        }
        else {
            sItemInOrderList.IncreaseItemNumber(position);
            return false;
        }

    }
    /*
     * 当商品数量只剩1时返回true
     * */
    public boolean subNumberInItem(int position){

        if(sItemInOrderList.ItemNumberEqual1(position)){
            sItemInOrderList.DeleteItemFromList(position);
            return true;
        } else{
            sItemInOrderList.DecreaseItemNumber(position);
            return false;
        }
    }

    public int getSize(){
        return mItems.size();
    }

    public float getTotalPrice(){
        float sum =0 ;
        for(int i=0;i<getSize();i++){
            ItemCategories.Item item =getItemFromInteger(i);
            sum +=item.getNumber()*item.getPrice();

        }
        return sum;
    }

}
