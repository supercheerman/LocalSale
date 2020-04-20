package com.example.localsale.ui.TotalOrderPlesk;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.localsale.ui.SingleFragmentActivity;

import androidx.fragment.app.Fragment;

public class OrderShowActivity extends SingleFragmentActivity {

    private static final String INDEX="LOCALSALE_ORDER_SHOW_ACTIVITY_INDEX";

    public static Intent newIntent(Context packageContext,int posit){
        Intent intent =new Intent(packageContext, OrderShowActivity.class);
        intent.putExtra(INDEX,posit);
        return intent;
    }
    @Override
    protected Fragment createFragment() {

        int index = getIntent().getIntExtra(INDEX,-1);
        return OrderShowFragment.newInstance(this,index);
    }
}
