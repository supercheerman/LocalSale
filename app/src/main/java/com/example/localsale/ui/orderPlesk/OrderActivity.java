package com.example.localsale.ui.orderPlesk;

import android.content.Context;
import android.content.Intent;

import com.example.localsale.ui.SingleFragmentActivity;
import com.example.localsale.ui.shoppingPlesk.ItemCategories;

import java.util.List;
import java.util.UUID;

import androidx.fragment.app.Fragment;

public class OrderActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context packageContext){
        Intent intent =new Intent(packageContext,OrderActivity.class);
        return intent;
    }
    @Override
    protected Fragment createFragment() {
        return OrderFragment.newInstance(this);
    }


}
