package com.example.localsale.ui.shoppingPlesk;

import android.util.Log;
import android.util.SparseArray;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class ItemCategories{

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
    private SparseArray<Integer> mShoppingList;
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
        mShoppingList = new SparseArray<Integer>();
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
    }
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
    public ItemCategory getSectionInCategoryList(int position){

        return mItemCategoryList.get(position);
    }
    public String getSectionInCategoryListString(int position){
        return getSectionInCategoryList(position).getCategory();
    }
    public Item getItem(int position){

        return mItemCategoryList.get(getSectionForPosition(position)).getItems().get(getPositionInSectionForPosition(position));

    }
    public String getItemString(int position){
        return getItem(position).getName();
    }
    /*
    * 获得该item所在section在section例表中位置
    * */
    public int getCountTillSectionPosit(int posit){
        int count=0;
        for(int i=0;i<posit;i++){
            count+=mItemCategoryList.get(i).getCounts();
        }
        return count;
    }

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
    /*
    * 当商品数目为0时返回true
    *
    * */
    public boolean addNumberInItem(int position){
        if(mShoppingList.get(position)==null){
            mShoppingList.put(position,1);
            Log.i("TAG",position+"  "+mShoppingList.get(position));
            return true;
        }
        else {
            int tmp =mShoppingList.get(position)+1;
            mShoppingList.remove(position);
            mShoppingList.put(position,tmp);
            Log.i("TAG",position+" @ "+mShoppingList.get(position)+" @ "+tmp);
            return false;
        }

    }
    /*
    * 当商品数量只剩1时返回true
    * */
    public boolean subNumberInItem(int position){
        if(mShoppingList.get(position)==1){
            mShoppingList.remove(position);
            return true;
        } else{
            int tmp =mShoppingList.get(position)-1;
            mShoppingList.remove(position);
            mShoppingList.put(position,tmp);
            return false;
        }
    }
    public int getNumber(int posit){

        Log.i("TAG","@@@"+mShoppingList.toString());
        return mShoppingList.get(posit);
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

        private String mName ;
        private float mPrice;
        private int mNumber =0;
        private String mDescription;

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

    public void createForTest(){
        mItemCategoryList.add(forTest("鸡"));
        mItemCategoryList.add(forTest("鸭"));
        mItemCategoryList.add(forTest("鱼"));
        mItemCategoryList.add(forTest("猪"));
        mItemCategoryList.add(forTest("牛"));
    }
    public ItemCategory forTest(String s){
        ItemCategory itemCategory  = new ItemCategory();
        itemCategory.setCategory(s);
        List<Item> itemList = new ArrayList<>();
        for(int i =0;i< new Random().nextInt(3)+5;i++){
            Item tmp =new Item();
            tmp.setName(s+i);
            //Log.i("TAH","@-----@"+tmp.getName());
            itemList.add(tmp);
        }
        itemCategory.setItems(itemList);
        return itemCategory;
    }
}
