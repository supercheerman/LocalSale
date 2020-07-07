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

    public String getCurrentTimeItemToString(){
        if(mList.getCurrentIndex()==-1){
            return  "请选择配送时间";
        }else{
            TimeItem info =mList.getCurrentItem();
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH)+1;
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            if(info.getDay()=="今天"){
                return year+"-"+month+"-"+day+" "+info.getTime()+":00";
            }
            else{
                return year+"-"+month+"-"+day+1+" "+info.getTime()+":00";
            }

        }
    }

    private void initialTimeList(){
        //???
        //the hour may not be 24-hours system
        //???
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int minute = Calendar.getInstance().get(Calendar.MINUTE);
        Log.i("TAG","@______@"+hour);
        if(hour<7||(hour==7&&minute<=20)){
            mList.addItem(new TimeItem("今天","7:20"));
        }
        if(hour<11||(hour==11&&minute<=40)){
            mList.addItem(new TimeItem("今天","11:40"));
        }
        if(hour<12||(hour==12&&minute<=30)){
            mList.addItem(new TimeItem("今天","12:30"));
        }
        if(hour<16||(hour==16&&minute<=45)){
            mList.addItem(new TimeItem("今天","16:45"));
        }
        if(hour<17||(hour==17&&minute<=35)){
            mList.addItem(new TimeItem("今天","17:35"));
        }
        if(hour<20||(hour==20&&minute<=20)){
            mList.addItem(new TimeItem("今天","20:20"));
        }
        if(hour<21||(hour==21&&minute<=10)){
            mList.addItem(new TimeItem("今天","21:10"));
        }
        mList.addItem(new TimeItem("明天","7:20"));
        mList.addItem(new TimeItem("明天","11:40"));
        mList.addItem(new TimeItem("明天","12:30"));
        mList.addItem(new TimeItem("明天","16:45"));
        mList.addItem(new TimeItem("明天","17:35"));
        mList.addItem(new TimeItem("明天","20:20"));
        mList.addItem(new TimeItem("明天","21:10"));

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
