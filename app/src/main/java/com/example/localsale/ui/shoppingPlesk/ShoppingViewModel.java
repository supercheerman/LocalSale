package com.example.localsale.ui.shoppingPlesk;


import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ShoppingViewModel extends ViewModel {

    private MutableLiveData<topElementState> mTopElementMutableLiveData = new MutableLiveData<>();
    private ItemCategories mItemCategories;
    private ShoppingModelInterface mInterface;

    public void setInterface(ShoppingModelInterface anInterface) {
        mInterface = anInterface;

    }



    public ItemCategories getItemCategories() {
        return mItemCategories;
    }

    public MutableLiveData<topElementState> getTopElementMutableLiveDatae() {
        return mTopElementMutableLiveData;
    }
    public ShoppingViewModel(){
        mItemCategories = new ItemCategories();
    }
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
    public interface ShoppingModelInterface{
        public abstract void ChangeSectionColor(int position,String color);
    }
}
