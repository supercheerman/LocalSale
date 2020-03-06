package com.example.localsale.data.database;

import android.text.LoginFilter;

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
