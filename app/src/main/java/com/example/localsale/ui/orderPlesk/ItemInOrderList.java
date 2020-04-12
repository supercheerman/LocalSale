package com.example.localsale.ui.orderPlesk;

import android.util.Log;

import com.example.localsale.ui.shoppingPlesk.ItemCategories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ItemInOrderList {

    private static ItemInOrderList sItemInOrderList;

    /*储存Item项以及其在RecyclerView对应的项的序号*/
    HashMap<Integer,ItemCategories.Item> mItems = new HashMap<>();
    /*由于hash不能按顺序存取，所以备份其索引项*/
    List<Integer> mIntegers =new ArrayList<>();

    private String orderID;

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    /*
 * @param null
 * @return
 * @author hwh
 * @date 2020/4/12
 * @Description 为了将从数据库中得到的位置数量对转化为订单对象中的项
 **/
    public void DBToItemInOrderList(int position,int number){
        ItemCategories.Item item = ItemCategories.getItemCategories().getItem(position);
        addItemInToOrderList(position,item,number);
    }

    public static ItemInOrderList getItemInOrderList(){
        if(sItemInOrderList==null){
            sItemInOrderList =new ItemInOrderList();
        }
        return sItemInOrderList;
    }

    /*
     * @param posit Item项在RecyclerView对应的项的序号
     * @return void
     * @author hwh
     * @date 2020/4/7
     * @Description 将该Item项加入到hash表中
     **/
    public void addItemInToOrderList(int posit,ItemCategories.Item item,int number){
        ItemCategories.Item  tmp = new ItemCategories.Item(item);
        tmp.setNumber(number);
        mIntegers.add(posit);
        mItems.put(posit,tmp);
    }

    public void addItemInToOrderList(int posit,ItemCategories.Item item){
       addItemInToOrderList(posit,item,1);
    }

    /*
     * @param null
     * @return true表示有该项
     * @author hwh
     * @date 2020/4/7
     * @Description 确认hash表中是否有该项
     **/
    public boolean HasItem(int posit){
        return  mItems.containsKey(posit);
    }

    /*
     * @param null
     * @return void
     * @author hwh
     * @date 2020/4/7
     * @Description 将存在该索引所在的商品数量属性加一
     **/
    public void IncreaseItemNumber(int posit){
        ItemCategories.Item item = mItems.get(posit);
        item.setNumber(item.getNumber()+1);
    }
    /*
     * @param null
     * @return void
     * @author hwh
     * @date 2020/4/7
     * @Description 将存在该索引所在的商品数量属性减一
     **/
    public void DecreaseItemNumber(int posit){
        ItemCategories.Item item = mItems.get(posit);
        item.setNumber(item.getNumber()-1);
    }
    /*
     * @param null
     * @return void
     * @author hwh
     * @date 2020/4/7
     * @Description 将存在该索引所在的商品移除例表
     **/
    public void DeleteItemFromList(int posit){
        mIntegers.remove(posit);
        mItems.remove(posit);
    }
    /*
     * @param null
     * @return void
     * @author hwh
     * @date 2020/4/7
     * @Description 判断该项的数量是否等于1
     **/
    public boolean ItemNumberEqual1(int posit){

        int tmp =  mItems.get(posit).getNumber();

        return tmp==1;
    }
    /*
     * @param null
     * @return void
     * @author hwh
     * @date 2020/4/7
     * @Description 获取该项的数量
     **/
    public int getItemNumber(int posit){
        Log.i("TAG",posit+" "+mItems+"  ");
        return mItems.get(posit).getNumber();
    }
    /*
     * @param null
     * @return void
     * @author hwh
     * @date 2020/4/7
     * @Description 获取该索引所在的项
     **/

    public ItemCategories.Item getItemFromInteger(int posit) {
        return mItems.get(mIntegers.get(posit));
    }

    /*
     * @param null
     * @return void
     * @author hwh
     * @date 2020/4/7
     * @Description 如果没有则将该商品加入到hash中，有则将数量加一
     **/
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
     * @param null
     * @return void
     * @author hwh
     * @date 2020/4/7
     * @Description 如果列表没有该商品则报错，有如果只剩一则删除，否则数量减一
     **/
    public boolean subNumberInItem(int position){

        try{
            if(!sItemInOrderList.HasItem(position)){
                throw new Exception();
            }
            if(sItemInOrderList.ItemNumberEqual1(position)){
                sItemInOrderList.DeleteItemFromList(position);
                return true;
            } else{
                sItemInOrderList.DecreaseItemNumber(position);
                return false;
            }
        }
        catch (Exception e){
            Log.e("WWW","",e);
        }
        return false;
    }
    /*
     * @param null
     * @return int
     * @author hwh
     * @date 2020/4/7
     * @Description 获取商品总量
     **/
    public int getSize(){
        return mItems.size();
    }

    /*
     * @param null
     * @return flaot
     * @author hwh
     * @date 2020/4/7
     * @Description 获取所有商品的总价
     **/
    public float getTotalPrice(){
        float sum =0 ;
        for(int i=0;i<getSize();i++){
            ItemCategories.Item item =getItemFromInteger(i);
            sum +=item.getNumber()*item.getPrice();

        }
        return sum;
    }

}
