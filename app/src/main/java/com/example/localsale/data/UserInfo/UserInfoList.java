package com.example.localsale.data.UserInfo;

import com.example.localsale.data.ItemList;
import com.example.localsale.data.LocalDatabase.Database;

import java.util.List;

public class UserInfoList {

    private static UserInfoList sUserInfoList;

    private UserInfoList(){

    }
    public static UserInfoList getInstance(){
        if(sUserInfoList==null){
            sUserInfoList = new UserInfoList();
        }
        return sUserInfoList;
    }

    private ItemList<UserInfo> mList = new ItemList<>();

    public ItemList<UserInfo> getList() {
        return mList;
    }

    public void setList(List<UserInfo> list) {
        mList.setList(list);
    }
    /*
     *
     * @param username
     * @param password
     * @return boolean
     * @author hwh
     * @date 2020/3/27
     * @Description 用来判断用户名和密码是否存在
     **/
    public boolean isUserValidation(String username, String password) {
        List<UserInfo> list = mList.getList();
        for (UserInfo userInfo :list) {
            if((userInfo.getUserName().equals(username)) && (userInfo.getUserPassword().equals(password)))
                return true;
        }
        return false;
    }
}
