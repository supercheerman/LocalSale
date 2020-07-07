package com.example.localsale.ui.shoppingPlesk;


import android.util.Log;

import androidx.lifecycle.ViewModel;

public class ShoppingViewModel extends ViewModel {

    /*
    *用来保存上一个被点击的
     */
    private int mTop = -1 ;
    private ItemCategories mItemCategories;
    private ShoppingModelInterface mInterface;

    public void setInterface(ShoppingModelInterface Interface) {
        mInterface = Interface;

    }

    public void setItemCategories(ItemCategories itemCategories) {
        mItemCategories = itemCategories;
    }

    public ItemCategories getItemCategories() {
        return mItemCategories;
    }

    public ShoppingViewModel(){

        mItemCategories =new ItemCategories();
    }

    /*
     * @param null
     * @return
     * @author hwh
     * @date 2020/4/6
     * @Description
     **/
    public void addNumberInTop(int posit){

        if(mTop==-1){
            mTop =posit;
            mInterface.ChangeSectionColor(posit,"#FFFFFF");
            return;
        }
        mInterface.ChangeSectionColor(mTop ,"#707070");
        mInterface.ChangeSectionColor(posit,"#FFFFFF");
        mTop= posit;

    }
    /*
     * @author hwh
     * @date 2020/4/6
     * @Description 用来在改变指定Section的颜色
     **/
    public interface ShoppingModelInterface{
        void ChangeSectionColor(int position,String color);
    }
}
