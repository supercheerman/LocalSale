package com.example.localsale.ui.login;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.example.localsale.ui.SingleFragmentActivity;
import com.example.localsale.ui.addressPlesk.AddAddressActivity;

public class LoginActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context packageContext){
        Intent intent =new Intent(packageContext, LoginActivity.class);
        return intent;
    }
    @Override
    protected Fragment createFragment() {
        return LoginFragment.newInstance(this);
    }
}
