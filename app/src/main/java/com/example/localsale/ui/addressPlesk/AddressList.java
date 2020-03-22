package com.example.localsale.ui.addressPlesk;

import java.util.ArrayList;
import java.util.List;

public class AddressList {

    private static AddressList sAddressList;
    private List<AddressInfo> mAddressInfos = new ArrayList<>();


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
    public void addAddressInfo(String dormitory, String roomNumber,String name,String phoneNumber){
        AddressInfo info = new AddressInfo();
        info.setDormitory(dormitory);
        info.setRoomNumber(roomNumber);
        info.setName(name);
        info.setPhoneNumber(phoneNumber);
        mAddressInfos.add(info);

    }

    public class AddressInfo{
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
