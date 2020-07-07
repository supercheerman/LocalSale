package com.example.localsale.ui.orderPlesk;

import android.util.Log;

import com.example.localsale.ui.TimePlesk.TimeList;
import com.example.localsale.ui.addressPlesk.AddressList;
import com.example.localsale.ui.shoppingPlesk.ItemCategories;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import androidx.annotation.NonNull;

public class ItemInOrderList {

    private static ItemInOrderList sItemInOrderList;

    /*储存Item项以及其在RecyclerView对应的项的序号*/
    HashMap<Integer,ItemCategories.Item> mItems = new HashMap<>();
    /*由于hash不能按顺序存取，所以备份其索引项*/
    List<Integer> mIntegers =new ArrayList<>();

    private String orderID;

    private String mDate;

    private AddressList.AddressInfo mAddressInfo;

    private String mDeliverTime;


    public AddressList.AddressInfo getAddressInfo() {
        return mAddressInfo;
    }

    public void setAddressInfo(AddressList.AddressInfo addressInfo) {
        mAddressInfo = addressInfo;
    }

    public String getDeliverTime() {
        return mDeliverTime;
    }

    public void setDeliverTime(String deliverTime) {
        mDeliverTime = deliverTime;
    }

    public void setDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");//可以方便地修改日期格式
        mDate = dateFormat.format(new Date());
    }
    public void setDate(String date) {
        mDate = date;
    }

    public String getDate() {
        return mDate;
    }

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
    public void DBToItemInOrderList(int ID,int number){
        if(number ==0)
            return;
        ItemCategories.Item item = ItemCategories.getItemCategories().getItemByID(ID);
        addItemInToOrderList(ID,item,number);
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

        mItems.remove(posit);
        mIntegers.remove(mIntegers.indexOf(posit));
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
        //Log.i("TAG",posit+" "+mItems+"  ");
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

    @NonNull
    @Override
    public String toString() {
        String s="";
        for(int i =0;i<getSize();i++){
            ItemCategories.Item item = getItemFromInteger(i);
            s+="itemID:"+item.getID()+"  itemNumber:"+item.getNumber();

        }
        return s;
    }
    public static String getOrderIdByTime() {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
        String newDate=sdf.format(new Date());
        String result="";
        Random random=new Random();
        for(int i=0;i<3;i++){
            result+=random.nextInt(10);
        }
        return newDate+result;
    }

}
