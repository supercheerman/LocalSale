package com.example.localsale.ui.TotalOrderPlesk;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.localsale.R;
import com.example.localsale.ui.orderPlesk.OrderFragment;

import androidx.fragment.app.Fragment;

public class TotalOrderFragment extends Fragment {
    public static TotalOrderFragment newInstance(Context context){
        return new TotalOrderFragment();
    }
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_total_order,container,false);


        return view;
    }


}
