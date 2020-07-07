package com.example.localsale.ui.Navigation;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.localsale.R;

import androidx.fragment.app.Fragment;

public class NavigationFragment extends Fragment {
    private Button mShoppingListButton;
    private Button mShoppingTrolleyButton;
    private Button mUserInfoButton;
    private static OnClickListener mOnClickListener;

    public static NavigationFragment newInstance(Context context , OnClickListener listener){

        mOnClickListener = listener;
        return new NavigationFragment();

    }


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_navigation,container,false);
        mShoppingListButton = view.findViewById(R.id.shopping_list_button);
        mShoppingTrolleyButton = view.findViewById(R.id.shopping_trolley_button);
        mUserInfoButton = view.findViewById(R.id.user_info_button);

        mShoppingListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mOnClickListener.shoppingListButtonClick();
            }
        });
        mShoppingTrolleyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mOnClickListener.shoppingTrolleyButtonClick();
            }
        });
        mUserInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnClickListener.userInfoButtonClick();
            }
        });
        return view;

    }

    public interface OnClickListener {
        void shoppingListButtonClick();
        void shoppingTrolleyButtonClick();
        void userInfoButtonClick();
    }

}

