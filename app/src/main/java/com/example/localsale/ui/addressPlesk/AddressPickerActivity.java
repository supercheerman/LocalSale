package com.example.localsale.ui.addressPlesk;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.localsale.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AddressPickerActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;



    public static Intent newIntent(Context packageContext){
        Intent intent =new Intent(packageContext,AddressPickerActivity.class);
        return intent;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_picker);
        mRecyclerView =findViewById(R.id.address_picker_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new addressAdaptor());


    }
    public class addressItem extends RecyclerView.ViewHolder{
        private ImageButton mAddress_check_button;
        private TextView mUserInfoTextView;
        private TextView mAddressTextView;
        private Button mAddressEditButton;

        public addressItem(LayoutInflater inflater, ViewGroup container) {
            super(inflater.inflate(R.layout.list_address_item,container,false));
            mAddress_check_button = itemView.findViewById(R.id.address_check_box_button);
            mUserInfoTextView = itemView.findViewById(R.id.user_info_text_view);
            mAddressTextView = itemView.findViewById(R.id.address_text_view);
            mAddressEditButton = itemView.findViewById(R.id.address_edit_button);
        }
        public void bindText(final AddressList.AddressInfo item){
            mUserInfoTextView .setText(item.getName()+"  "+item.getPhoneNumber());
            mAddressTextView.setText(item.getDormitory()+item.getRoomNumber());
        }
        public void bindListener(){
            final int post = this.getAdapterPosition();
            mAddress_check_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AddressList mAddressList = AddressList.getAddressList();
                    int  previousPosit = mAddressList.getCurrentIndex();
                    if(previousPosit!=-1){
                        Drawable drawable=getDrawable(android.R.drawable.checkbox_off_background);
                        ImageButton button =mRecyclerView.getChildAt(previousPosit).findViewById(R.id.address_check_box_button);
                        button.setImageDrawable(drawable);

                    }
                    mAddressList.setCurrentIndex(post);
                    bindDrawable();
                }
            });
            mAddressEditButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = AddAddressActivity.newIntent(getApplicationContext(),post);
                    startActivity(intent);
                }
            });
        }
        public void bindDrawable(){
            Drawable drawable=getDrawable(android.R.drawable.checkbox_on_background);
            mAddress_check_button.setImageDrawable(drawable);
        }
    }
    public class addressAdaptor extends RecyclerView.Adapter<addressItem>{

        private AddressList mAddressList = AddressList.getAddressList();

        @NonNull
        @Override
        public addressItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getApplication());
            return new addressItem(inflater,parent);
        }

        @Override
        public void onBindViewHolder(@NonNull addressItem holder, int position) {
            int currentIndex  = mAddressList.getCurrentIndex();
            if(position==currentIndex){
                holder.bindDrawable();
            }
            AddressList.AddressInfo item = mAddressList.getAddressInfoItem(position);
            holder.bindText(item);
            holder.bindListener();
        }

        @Override
        public int getItemCount() {
            if(mAddressList!=null){
                return mAddressList.getSize();
            }
            return 0;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.address_picker_menu,menu);
        MenuItem toggleItem = menu.findItem(R.id.menu_item_add_address);
        return true;

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.menu_item_add_address:
                Intent intent = AddAddressActivity.newIntent(getApplicationContext());
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mRecyclerView.setAdapter(new addressAdaptor());
    }
}
