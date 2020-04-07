package com.example.localsale.data.UserInfo;

public class UserInfo {
    private String mUserName;
    private String mUserPassword;
    public UserInfo(String userName,String userPassword){
        mUserName =userName;
        mUserPassword=userPassword;
    }

    public String getUserName() {
        return mUserName;
    }

    public String getUserPassword() {
        return mUserPassword;
    }
}
