package com.example.localsale.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.util.Patterns;

import com.example.localsale.data.LoginRepository;
import com.example.localsale.data.LocalDatabase.Database;
import com.example.localsale.data.LocalDatabase.UserInfo;
import com.example.localsale.R;

import java.util.List;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private LoginRepository loginRepository;

    LoginViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    public LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    public LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    public void login(Database database, String username, String password) {
        // can be launched in a separate asynchronous job
        //用户密码输入符合格式后点击登录按钮
        //Result<LoggedInUser> result = loginRepository.login(username, password);

        boolean result = isUserValidation(database,username ,password);

        if (result) {
            loginResult.setValue(new LoginResult(username));
        } else {
            loginResult.setValue(new LoginResult(R.string.login_failed));
        }
    }

    private boolean isUserValidation(Database database,String username, String password) {
        List<UserInfo> list = database.getUsers();
        for (UserInfo userInfo :list) {
            if((userInfo.getUserName().equals(username)) && (userInfo.getUserPassword().equals(password)))
                return true;
        }
        return false;
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
