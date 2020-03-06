package com.example.localsale.ui.register;

import android.content.Context;
import android.content.Intent;

import com.example.localsale.ui.SingleFragmentActivity;
import com.example.localsale.ui.login.LoginFragment;

import java.util.UUID;

import androidx.fragment.app.Fragment;

public class RegisterActivity extends SingleFragmentActivity {


    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, RegisterActivity.class);
        return intent;
    }
    @Override
    protected Fragment createFragment() {
        return RegisterFragment.newInstance(this);
    }
}
