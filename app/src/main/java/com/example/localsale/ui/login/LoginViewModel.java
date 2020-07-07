package com.example.localsale.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.localsale.R;

/*
* 该类用来控制LoginFragment中的复杂逻辑运算
* */
public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();

    LoginViewModel() {

    }

    public LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    /*
    * 当输入框发生变化时
    *
    * */
    public void loginTextChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!isPasswordLengthValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
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
    private boolean isPasswordLengthValid(String password) {
        return password != null && password.trim().length() > 5;
    }
}
