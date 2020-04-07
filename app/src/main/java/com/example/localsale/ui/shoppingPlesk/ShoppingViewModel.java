package com.example.localsale.ui.shoppingPlesk;


import android.util.Log;

import com.example.localsale.data.CloudDatabase.DBCHelper;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ShoppingViewModel extends ViewModel {

    /*
    *用来保存上一个被点击的
     */
    private MutableLiveData<topElementState> mTopElementMutableLiveData = new MutableLiveData<>();
    private ItemCategories mItemCategories;
    private ShoppingModelInterface mInterface;

    public void setInterface(ShoppingModelInterface anInterface) {
        mInterface = anInterface;

    }

    public void setItemCategories(ItemCategories itemCategories) {
        mItemCategories = itemCategories;
    }

    public ItemCategories getItemCategories() {
        return mItemCategories;
    }

    public MutableLiveData<topElementState> getTopElementMutableLiveDatae() {
        return mTopElementMutableLiveData;
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
    public void addNumberInTop(int tmp){

        if(mTopElementMutableLiveData.getValue()==null){
            mTopElementMutableLiveData.setValue(new topElementState(tmp));
            mInterface.ChangeSectionColor(tmp,"#FFFFFF");
            return;
        }
        int top =mTopElementMutableLiveData.getValue().getTop();
        Log.i("TAG","@______@:addNumber"+tmp+"  "+top);
        mTopElementMutableLiveData.setValue(new topElementState(tmp));
        mInterface.ChangeSectionColor(top,"#707070");
        mInterface.ChangeSectionColor(tmp,"#FFFFFF");
    }


    public class topElementState{

        private int top = 0;

        public topElementState(int tmp){
            top=tmp;
        }

        public int getTop() {
            return top;
        }

        public void setTop(int top) {
            this.top = top;
        }


    }

    /*
     * @author hwh
     * @date 2020/4/6
     * @Description 用来在改变指定Section的颜色
     **/
    public interface ShoppingModelInterface{
        public void ChangeSectionColor(int position,String color);
    }
}
