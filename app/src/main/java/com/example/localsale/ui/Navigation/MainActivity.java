package com.example.localsale.ui.Navigation;

import android.content.Context;
import android.content.Intent;

import com.example.localsale.R;
import com.example.localsale.ui.orderPlesk.OrderActivity;
import com.example.localsale.ui.shoppingPlesk.ShoppingFragment;
import com.example.localsale.ui.userPlesk.UserPleskFragment;

import androidx.fragment.app.Fragment;

public class MainActivity extends MenuFragmentActivity {


    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, MainActivity.class);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        return ShoppingFragment.newInstance(this);
    }

    @Override
    protected Fragment createNavigationFragment() {
        return NavigationFragment.newInstance(this, new NavigationFragment.OnClickListener() {
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
                //Intent intent = LoginActivity.newIntent(getApplicationContext());
               // startActivity(intent);
                Fragment fragment = UserPleskFragment.newInstance(getApplicationContext());
                changFragment(R.id.fragment_container,fragment);

            }
        });
    }


}
