package com.example.localsale.ui.addressPlesk;

import com.example.localsale.R;

import java.util.ArrayList;
import java.util.List;

public class AddressList {

    private static AddressList sAddressList;
    private List<AddressInfo> mAddressInfos = new ArrayList<>();
    private int currentIndex=-1;

    public int getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }


    public String getCurrentAddressInfoToString(){
        if(currentIndex==-1){
            return  "请选择配送地点";
        }else{
            AddressInfo info =getAddressInfoItem(currentIndex);
            return info.getName()+"  "+info.getPhoneNumber()+"  "+info.getDormitory()+" "+info.getRoomNumber();
        }
    }

    public AddressInfo getCurrentAddressInfo(){
        if(currentIndex==-1){
            return  null;
        }else{
            return getAddressInfoItem(currentIndex);
        }
    }
    public static AddressList getAddressList() {
        if(sAddressList==null){
            sAddressList  = new AddressList();


        }
        return sAddressList;
    }

    public static void setAddressList(AddressList addressList) {
        sAddressList = addressList;
    }

    public void setAddressInfo(List<AddressInfo> addressInfo) {
        mAddressInfos = addressInfo;
    }
    public int getSize(){
        return mAddressInfos.size();
    }
    public AddressInfo getAddressInfoItem(int posit){
        return mAddressInfos.get(posit);
    }


    public void Clear(){
        mAddressInfos.clear();
    }

    public void addAddressInfo(String dormitory, String roomNumber,String name,String phoneNumber){
        AddressInfo info = new AddressInfo();
        info.setDormitory(dormitory);
        info.setRoomNumber(roomNumber);
        info.setName(name);
        info.setPhoneNumber(phoneNumber);
        mAddressInfos.add(info);

    }
    public void addAddressInfo(AddressInfo item){
        mAddressInfos.add(item);

    }
    public void addAddressInfo(String dormitory, String roomNumber,String name,String phoneNumber,int posit){
        AddressInfo info = getAddressInfoItem(posit);
        info.setDormitory(dormitory);
        info.setRoomNumber(roomNumber);
        info.setName(name);
        info.setPhoneNumber(phoneNumber);
    }

    public static AddressInfo createAddressInfo(String dormitory, String roomNumber,String name,String phoneNumber){
        AddressInfo item = new AddressInfo();
        item.dormitory = dormitory;
        item.roomNumber = roomNumber;
        item.name = name;
        item.phoneNumber = phoneNumber;
        return item;
    }

    public static class AddressInfo{
        String dormitory;
        String roomNumber;
        String name;
        String phoneNumber;


        public String getDormitory() {
            return dormitory;
        }

        public void setDormitory(String dormitory) {
            this.dormitory = dormitory;
        }

        public String getRoomNumber() {
            return roomNumber;
        }

        public void setRoomNumber(String roomNumber) {
            this.roomNumber = roomNumber;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }
    }
}
