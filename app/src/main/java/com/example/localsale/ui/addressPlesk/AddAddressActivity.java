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

import java.io.Serializable;

import androidx.appcompat.app.AppCompatActivity;

public class AddAddressActivity extends AppCompatActivity {

    private TextView mRoomNumber;
    private TextView mUserName;
    private TextView mPhoneNumber;
    private Button mButton;
    private AutoCompleteTextView mAutoCompleteTextView;
    private static final String[] autoText = new String[]{"c3","c4","信部13舍"};
    private int  edit_item_index=-1;

    public static Intent newIntent(Context packageContext){
        Intent intent =new Intent(packageContext,AddAddressActivity.class);
        return intent;
    }
    public static Intent newIntent(Context packageContext, int index){
        Intent intent =new Intent(packageContext,AddAddressActivity.class);
        intent.putExtra("addressInfoIndex",index);
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
                if(mPhoneNumber.getText().length()>0&&mUserName.getText().length()>0&&!mPhoneNumber.getText().equals("")&&!mAutoCompleteTextView.getText().equals("")){
                    if(edit_item_index==-1){
                        AddressList.getAddressList().addAddressInfo(mAutoCompleteTextView.getText().toString()
                                ,mRoomNumber.getText().toString(),mUserName.getText().toString(),mPhoneNumber.getText().toString());
                    }else{
                        AddressList.getAddressList().addAddressInfo(mAutoCompleteTextView.getText().toString()
                                ,mRoomNumber.getText().toString(),mUserName.getText().toString(),mPhoneNumber.getText().toString(),edit_item_index);
                    }

                    finish();
                }
            }
        });
        bindText();


    }
    /*
    * 如果创建时intent含有addressInfo则将其绑定到文本框中
    *
    * */
    private void bindText(){
        Intent intent = getIntent();
        edit_item_index =intent.getIntExtra("addressInfoIndex",-1);
        if(edit_item_index==-1){
            return ;
        }else{
            AddressList.AddressInfo info = AddressList.getAddressList().getAddressInfoItem(edit_item_index);
            mAutoCompleteTextView.setText(info.getDormitory());
            mRoomNumber.setText(info.getRoomNumber());
            mPhoneNumber.setText(info.getPhoneNumber());
            mUserName.setText(info.getName());
        }


    }


}
