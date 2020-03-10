package com.example.localsale.ui.shoppingPlesk;

import android.util.Log;
import android.util.SparseArray;

import java.util.ArrayList;
import java.util.List;
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
    private SparseArray<Integer> mSectionCountCache;
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
        mItemCategoryList.add(forTest("鸡"));
        mItemCategoryList.add(forTest("鸭"));
        mItemCategoryList.add(forTest("鱼"));
        mItemCategoryList.add(forTest("猪"));
        mItemCategoryList.add(forTest("牛"));
        mSectionCache = new SparseArray<Integer>();
        mSectionPositionCache = new SparseArray<Integer>();
        mSectionCountCache = new SparseArray<Integer>();
        mCount = -1;
        mSectionCount = -1;
    }
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
    public int getCountTillSectionPosit(int posit){
        int count=0;
        for(int i=0;i<posit;i++){
            count+=mItemCategoryList.get(i).getCounts();
        }
        return count;
    }

    public static class ItemCategory {

        private String mCategory;
        private List<Item> mItems;

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

        public String getName() {
            return mName;
        }

        public void setName(String name) {
            mName = name;
        }
    }
    public ItemCategory forTest(String s){
        ItemCategory itemCategory  = new ItemCategory();
        itemCategory.setCategory(s);
        List<Item> itemList = new ArrayList<>();
        for(int i =0;i< new Random().nextInt(3)+5;i++){
            Item tmp =new Item();
            tmp.setName(s+i);
            Log.i("TAH","@-----@"+tmp.getName());
            itemList.add(tmp);
        }
        itemCategory.setItems(itemList);
        return itemCategory;
    }
}
