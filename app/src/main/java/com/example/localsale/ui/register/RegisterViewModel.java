package com.example.localsale.ui.register;

import android.util.Log;

import com.example.localsale.API.UserInfoAPI;
import com.example.localsale.R;
import com.example.localsale.data.LocalDatabase.Database;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RegisterViewModel extends ViewModel {

    private MutableLiveData<RegisterFormState> mRegisterFormState = new MutableLiveData<>();


    public LiveData<RegisterFormState> getRegisterFormState() {
        return mRegisterFormState;
    }


    /*
    * 当输入框发生变化时
    *
    * */
    public void registerTextChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            mRegisterFormState.setValue(new RegisterFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            mRegisterFormState.setValue(new RegisterFormState(null, R.string.invalid_password));
        } else {
            mRegisterFormState.setValue(new RegisterFormState(true));
        }
    }
    public void registerUser(Database database,String username, String password){
        Log.i("TAG:Register","before");
        database.addUser(username,password);
        UserInfoAPI.sendUserInfo(username,password);
        Log.i("Register","successful");

    }

    // A placeholder username validation check
    //判断输入用户名TextView的格式
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.length()==11) {
            return username.matches("^[1](([3|5|8][\\d])|([4][5,6,7,8,9])|([6][5,6])|([7][3,4,5,6,7,8])|([9][8,9]))[\\d]{8}$");
        } else {
            return false;
        }
    }

    // A placeholder password validation check
    //判断输入密码TextView的格式
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }
}
