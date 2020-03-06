package com.example.localsale.ui.register;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.util.Patterns;

import com.example.localsale.R;
import com.example.localsale.data.LoginRepository;
import com.example.localsale.data.database.Database;

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
    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            mRegisterFormState.setValue(new RegisterFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            mRegisterFormState.setValue(new RegisterFormState(null, R.string.invalid_password));
        } else {
            mRegisterFormState.setValue(new RegisterFormState(true));
        }
    }
    public void registerUser(Database database,String username, String password){

        database.addUser(username,password);
        Log.i("Register","successful");

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
