package com.example.localsale.data.UserInfo;

import com.alibaba.fastjson.annotation.JSONField;

public class UserInfo {
    private String mUserName;
    private String mUserPassword;
    public UserInfo(String userName,String userPassword){
        mUserName =userName;
        mUserPassword=userPassword;
    }
    public UserInfo(){

    }

    public void setUserName(String userName) {
        mUserName = userName;
    }

    public void setUserPassword(String userPassword) {
        mUserPassword = userPassword;
    }

    public String getUserName() {
        return mUserName;
    }

    public String getUserPassword() {
        return mUserPassword;
    }
}
