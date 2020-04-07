package com.example.localsale.ui.TimePlesk;


import android.util.Log;

import com.example.localsale.data.ItemList;

import java.util.Calendar;

public class TimeList {


    private static TimeList sTimeList;


    private ItemList<TimeItem> mList = new ItemList<>();

    private TimeList(){
        initialTimeList();
    }
    public static TimeList getInstance() {
        if(sTimeList== null){
            sTimeList = new TimeList();
        }
        return sTimeList;
    }

    public ItemList<TimeItem> getList() {
        return mList;
    }

    public String getCurrentTimeInfo(){
        if(mList.getCurrentIndex()==-1){
            return  "请选择配送时间";
        }else{
            TimeItem info =mList.getCurrentItem();
            return info.getDay()+"  "+info.getTime();
        }
    }

    private void initialTimeList(){
        //???
        //the hour may not be 24-hours system
        //???
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        Log.i("TAG","@______@"+hour);
        if(hour<11){
            mList.addItem(new TimeItem("今天","11:00"));
        }else if(hour<17){
            mList.addItem(new TimeItem("今天","17:00"));
        }
        mList.addItem(new TimeItem("明天","11:00"));
        mList.addItem(new TimeItem("明天","16:00"));
    }

    public class TimeItem{
        private String mDay;
        private String mTime;

        public TimeItem(){

        }
        public TimeItem(String day,String time){
            mDay=day;
            mTime=time;
        }
        public String getDay() {
            return mDay;
        }

        public void setDay(String day) {
            mDay = day;
        }

        public String getTime() {
            return mTime;
        }

        public void setTime(String time) {
            mTime = time;
        }
    }
}
