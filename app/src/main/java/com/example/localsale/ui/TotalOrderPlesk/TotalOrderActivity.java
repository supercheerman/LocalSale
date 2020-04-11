package com.example.localsale.ui.TotalOrderPlesk;

import android.content.Context;
import android.content.Intent;

import com.example.localsale.ui.SingleFragmentActivity;
import com.example.localsale.ui.orderPlesk.OrderActivity;
import com.example.localsale.ui.orderPlesk.OrderFragment;

import androidx.fragment.app.Fragment;

public class TotalOrderActivity extends SingleFragmentActivity {
    public static Intent newIntent(Context packageContext){
        Intent intent =new Intent(packageContext, TotalOrderActivity.class);
        return intent;
    }
    @Override
    protected Fragment createFragment() {
        return TotalOrderFragment.newInstance(this);
    }
}
