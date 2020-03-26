package com.example.localsale.ui;

import android.content.Intent;
import android.util.Log;

import com.example.localsale.R;
import com.example.localsale.ui.MenuFragmentActivity;
import com.example.localsale.ui.Navigation.NavigationFragment;
import com.example.localsale.ui.SingleFragmentActivity;
import com.example.localsale.ui.login.LoginActivity;
import com.example.localsale.ui.login.LoginFragment;
import com.example.localsale.ui.orderPlesk.OrderActivity;
import com.example.localsale.ui.orderPlesk.OrderFragment;
import com.example.localsale.ui.register.RegisterFragment;
import com.example.localsale.ui.shoppingPlesk.ShoppingFragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends MenuFragmentActivity {


    @Override
    protected Fragment createFragment() {
        return ShoppingFragment.newInstance(this);
    }

    @Override
    protected Fragment createNavigationFragment() {
        return NavigationFragment.newInstance(this, new onClickListener() {
            @Override
            public void shoppingListButtonClick() {
                Fragment fragment = ShoppingFragment.newInstance(getApplicationContext());
                changFragment(R.id.fragment_container,fragment);
            }

            @Override
            public void shoppingTrolleyButtonClick() {
                Intent intent = OrderActivity.newIntent(getApplicationContext());
                startActivity(intent);
            }

            @Override
            public void userInfoButtonClick() {
                Intent intent = LoginActivity.newIntent(getApplicationContext());
                startActivity(intent);
            }
        });
    }

    public interface onClickListener{
        void shoppingListButtonClick();
        void shoppingTrolleyButtonClick();
        void userInfoButtonClick();
    }
}
