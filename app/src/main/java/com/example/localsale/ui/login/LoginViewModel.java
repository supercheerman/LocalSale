package com.example.localsale.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.util.Patterns;

import com.example.localsale.data.UserInfo.UserInfo;
import com.example.localsale.R;
import com.example.localsale.data.UserInfo.UserInfoList;

import java.util.List;

/*
* 该类用来控制LoginFragment中的复杂逻辑运算
* */
public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();

    LoginViewModel() {

    }

    public LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    public LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    /*
    *
     * @param username 用户名
     * @param password  密码
     * @return boolean  用户名存在则为true
     * @author hwh
     * @date 2020/3/27
     * Method login
     **/
    public boolean login(String username, String password) {
        // can be launched in a separate asynchronous job
        boolean result = UserInfoList.getInstance().isUserValidation(username,password);
        if (result) {
            loginResult.setValue(new LoginResult(username));
        } else {
            loginResult.setValue(new LoginResult(R.string.login_failed));
        }
        return result;
    }

    /*
    * 当输入框发生变化时
    *
    * */
    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
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
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    // A placeholder password validation check
    //判断输入密码TextView的格式
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }
}
