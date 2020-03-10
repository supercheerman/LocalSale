package com.example.localsale.ui.shoppingPlesk;

import com.example.localsale.ui.SingleFragmentActivity;

import androidx.fragment.app.Fragment;

public class ShoppingActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return ShoppingFragment.newInstance(this);
    }
}
