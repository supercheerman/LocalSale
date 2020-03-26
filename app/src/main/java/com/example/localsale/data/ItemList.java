package com.example.localsale.data;

import java.util.ArrayList;
import java.util.List;

public class ItemList<T> {

    private List<T> mList =new ArrayList<>();

    public int getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }

    private int currentIndex=-1;
    /**
     * 获取该例表中当前指向的项
     */
   public T getCurrentItem(){
       if(currentIndex==-1){
           return null;
       }else{
           return mList.get(currentIndex);
       }
   }
   public T getItem(int posit){
       if(posit>getSize()){
           return null;
       }
       return mList.get(posit);
   }
    /**
     * 获取该例表中元素个数
     */
   public int getSize(){
       return mList.size();
   }
    /**
     * 将该例表储存的项增加一项
     */
   public void addItem(T item){
       mList.add(item);
   }

}
