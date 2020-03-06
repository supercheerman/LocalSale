package com.example.localsale.ui.login;

import androidx.fragment.app.Fragment;

import com.example.localsale.ui.SingleFragmentActivity;

public class LoginActivity extends SingleFragmentActivity {


    @Override
    protected Fragment createFragment() {
        return LoginFragment.newInstance(this);
    }
}
