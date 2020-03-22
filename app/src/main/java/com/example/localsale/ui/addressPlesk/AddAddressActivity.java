package com.example.localsale.ui.addressPlesk;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import com.example.localsale.R;

import androidx.appcompat.app.AppCompatActivity;

public class AddAddressActivity extends AppCompatActivity {

    private TextView mRoomNumber;
    private TextView mUserName;
    private TextView mPhoneNumber;
    private Button mButton;
    private AutoCompleteTextView mAutoCompleteTextView;
    private static final String[] autoText = new String[]{"c3","c4","信部13舍"};

    public static Intent newIntent(Context packageContext){
        Intent intent =new Intent(packageContext,AddAddressActivity.class);
        return intent;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_add);
        mRoomNumber= findViewById(R.id.room_number);
        mUserName =findViewById(R.id.address_add_user_name);
        mPhoneNumber =findViewById(R.id.address_add_phone_number);
        mButton=findViewById(R.id.address_add_button);
        mAutoCompleteTextView =findViewById(R.id.multiAutoCompleteTextView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,autoText);
        mAutoCompleteTextView.setAdapter(adapter);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mPhoneNumber.getText()!=null&&mUserName.getText()!=null&&mPhoneNumber.getText()!=null&&mAutoCompleteTextView.getText()!=null){
                    AddressList.getAddressList().addAddressInfo(mAutoCompleteTextView.getText().toString()
                            ,mRoomNumber.getText().toString(),mUserName.getText().toString(),mPhoneNumber.getText().toString());
                    finish();
                }
            }
        });
    }


}
