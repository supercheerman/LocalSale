package com.example.localsale.data.UserInfo;

import android.content.Context;

import com.example.localsale.data.LocalDatabase.Database;

public class LoginUser {
    private static UserInfo sUseInfo;

    public static UserInfo getUseInfo(){
        if(sUseInfo==null){
            sUseInfo = new UserInfo();
        }
        return sUseInfo;
    }
}
