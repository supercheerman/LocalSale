package com.example.localsale.ui.shoppingPlesk;

import android.util.SparseArray;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.ArrayList;
import java.util.List;

public class ItemCategories{


    private static ItemCategories sItemCategories;


    public static ItemCategories getItemCategories() {
        return sItemCategories;
    }

    public static void setItemCategories(ItemCategories itemCategories) {
        sItemCategories = itemCategories;
    }

    /**
     * Holds the calculated values of @{link getPositionInSectionForPosition}
     * 获取item在section中的位置并存储到内存中
     */
    private SparseArray<Integer> mSectionPositionCache;
    /**
     * Holds the calculated values of @{link getSectionForPosition}
     * 获取一个item所在的section在section中的位置并储存到内存
     */
    private SparseArray<Integer> mSectionCache;



    /**
     * Holds the calculated values of @{link getCountForSection}
     * 将每一个访问过的section的item数量存储到内存中
     */
    /**
     * Caches the item count
     */
    private int mCount;
    /**
     * Caches the section count
     */
    private int mSectionCount;

    private List<ItemCategory> mItemCategoryList = new ArrayList<>();

    public List<ItemCategory> getItemCategoryList() {
        return mItemCategoryList;
    }

    public ItemCategories(){

        mSectionCache = new SparseArray<Integer>();
        mSectionPositionCache = new SparseArray<Integer>();
        mCount = -1;
        mSectionCount = -1;
    }
    /*
    * 获取所有商品的数目，并且实现缓存功能
    * */
    public int getItemCounts(){
        if (mCount >= 0) {
            return mCount;
        }
        int count=0;
        for(int i =0;i<getSectionCounts();i++){
            count+=mItemCategoryList.get(i).getCounts();
        }
        mCount =count;
        return count;
    }
    public int getSectionCounts(){
        if (mSectionCount >= 0) {
            return mSectionCount;
        }
        mSectionCount = mItemCategoryList.size();
        return mSectionCount;
    }/*
     * @param position
     * @return int
     * @author hwh
     * @date 2020/4/6
     * @Description 获取所给位置的Item所在种类目录在种类中的位置
     **/
    public final int getSectionForPosition(int position) {
        // first try to retrieve values from cache
        Integer cachedSection = mSectionCache.get(position);
        if (cachedSection != null) {
            return cachedSection;
        }
        int sectionStart = 0;
        for (int i = 0; i < getSectionCounts(); i++) {
            int sectionCount = mItemCategoryList.get(i).getCounts();
            int sectionEnd = sectionStart + sectionCount;
            if (position >= sectionStart && position < sectionEnd) {
                mSectionCache.put(position, i);
                return i;
            }
            sectionStart = sectionEnd;
        }
        return 0;
    }

    /*
     * @param position
     * @return
     * @author hwh
     * @date 2020/4/6
     * @Description 获取Item在所在目录中的位置
     **/
    public int getPositionInSectionForPosition(int position) {
        // first try to retrieve values from cache
        Integer cachedPosition = mSectionPositionCache.get(position);
        if (cachedPosition != null) {
            return cachedPosition;
        }
        int sectionStart = 0;
        for (int i = 0; i < getSectionCounts(); i++) {
            int sectionCount = mItemCategoryList.get(i).getCounts();
            int sectionEnd = sectionStart + sectionCount ;
            if (position >= sectionStart && position < sectionEnd) {
                int positionInSection = position - sectionStart ;
                mSectionPositionCache.put(position, positionInSection);
                return positionInSection;
            }
            sectionStart = sectionEnd;
        }
        return 0;
    }

   /*
    * @param position
    * @return ItemCategory
    * @author hwh
    * @date 2020/4/6
    * @Description 获取在种类目录中给定位置的种类项
    **/
    public ItemCategory getSectionInCategoryList(int position){

        return mItemCategoryList.get(position);
    }

    /*
     * @param position
     * @return String
     * @author hwh
     * @date 2020/4/6
     * @Description 获取在种类目录中给定位置的种类的种类名
     **/
    public String getSectionInCategoryListString(int position){
        return getSectionInCategoryList(position).getCategory();
    }

    /*
     * @param position
     * @return Item
     * @author hwh
     * @date 2020/4/6
     * @Description 获取给定位置的商品项
     **/
    public Item getItem(int position){

        return mItemCategoryList.get(getSectionForPosition(position)).getItems().get(getPositionInSectionForPosition(position));

    }
    /*
     * @param position
     * @return Item
     * @author hwh
     * @date 2020/4/6
     * @Description 获取给定位置的商品项
     **/
    public ItemCategories.Item getItemByID(int ID){


        for(int i =0;i<getItemCounts();i++){
            if(getItem(i).getID()==ID){
                return getItem(i);
            }
        }
        return null;

    }

    public String getItemString(int position){
        return getItem(position).getName();
    }

    /*
     * @param null
     * @return int
     * @author hwh
     * @date 2020/4/6
     * @Description 获取到指定种类以前包括该种类一共所含商品数目
     **/
    public int getCountTillSectionPosit(int posit){
        int count=0;
        for(int i=0;i<posit;i++){
            count+=mItemCategoryList.get(i).getCounts();
        }
        return count;
    }

    /*
     * @param null
     * @return void
     * @author hwh
     * @date 2020/4/6
     * @Description 将该商品项插入到指定的目录的最后一位,只能在初始时使用，否者该类中缓存数据可能出错
     **/

    public void InsetIntoCategory(String category, Item item){

        for(int i=0;i<mItemCategoryList.size();i++){
            if(category.equals(mItemCategoryList.get(i).getCategory())){
                mItemCategoryList.get(i).getItems().add(item);
                return;
            }
        }
        //mItemCategories为空或者不存在某个分类
        ItemCategory tmp = new ItemCategory(category,item);
        mItemCategoryList.add(tmp);
        return;




    }



    public static class ItemCategory {

        private String mCategory;
        private List<Item> mItems = new ArrayList<>();
        public ItemCategory(String category,Item item){
            mCategory =category;
            mItems.add(item);
        }
        public ItemCategory(){

        }

        public String getCategory() {
            return mCategory;
        }

        public void setCategory(String category) {
            mCategory = category;
        }

        public List<Item> getItems() {
            return mItems;
        }

        public void setItems(List<Item> items) {
            mItems = items;
        }

        public int getCounts(){
           return mItems.size();
        }
    }
    public static class Item{

        public static Item createItem(int ID,String name,float price,String description){
            Item item = new ItemCategories.Item();
            item.setID(ID);
            item.setName(name);
            item.setDescription(description);
            item.setPrice(price);
            return item;
        }

        @JSONField(name = "id")
        private int mID;

        @JSONField(name = "name")
        private String mName ;

        @JSONField(name = "price")
        private float mPrice;

        @JSONField(name = "type")
        private String mType;

        private int mNumber =0;

        @JSONField(name = "description")
        private String mDescription;

        public Item (){

        }
        public Item(Item item){
            mID =item.getID();
            mName =item.getName();
            mPrice=item.getPrice();
            mDescription =item.getDescription();
        }

        public String getType() {
            return mType;
        }

        public void setType(String type) {
            mType = type;
        }

        public void setID(int ID) {
            mID = ID;
        }

        public int getID() {
            return mID;
        }

        public float getPrice() {
            return mPrice;
        }

        public void setPrice(float price) {
            mPrice = price;
        }

        public int getNumber() {
            return mNumber;
        }

        public void setNumber(int number) {
            mNumber = number;
        }

        public String getDescription() {
            return mDescription;
        }

        public void setDescription(String description) {
            mDescription = description;
        }

        public String getName() {
            return mName;
        }

        public void setName(String name) {
            mName = name;
        }
    }
}
