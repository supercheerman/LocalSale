package com.example.localsale.ui.orderPlesk;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.localsale.R;
import com.example.localsale.data.ItemInOrderList;
import com.example.localsale.ui.addressPlesk.AddressPickerActivity;
import com.example.localsale.ui.shoppingPlesk.ItemCategories;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class OrderFragment extends Fragment {

    private ItemInOrderList mItemInOrderList;
    private RecyclerView mRecyclerView;
    private TextView mTotalPriceTextView;
    private ImageButton mMakeOrder;
    private ImageView mLocationPickerButton;
    private ImageView mTimePickerButton;


    public static OrderFragment newInstance(Context context){
        return new OrderFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mItemInOrderList = ItemInOrderList.getItemInOrderList();

    }
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_trolley,container,false);
        mRecyclerView = view.findViewById(R.id.order_list_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(new orderListViewAdaptor());
        mTotalPriceTextView = view.findViewById(R.id.total_price_text);
        mMakeOrder = view.findViewById(R.id.make_order_button);
        mTotalPriceTextView.setText("总价："+mItemInOrderList.getTotalPrice());
        mMakeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            JSONObject jsonArray = JsonOrder.ItemInOrderList2JsonArray(ItemInOrderList.getItemInOrderList());
                            Log.i("TAG",jsonArray.toString());
                            String  path="http://106.54.98.211/sql.php";
                            HttpURLConnection connection =(HttpURLConnection) new URL(path).openConnection();
                            connection.setRequestMethod("POST");
                            connection.connect();
                            OutputStream outputStream = connection.getOutputStream();
                            DataOutputStream dataOutputStream =new DataOutputStream(outputStream);
                            dataOutputStream.writeBytes(jsonArray.toString());
                            dataOutputStream.flush();
                            outputStream.close();
                            dataOutputStream.close();
                            InputStream inputStream = connection.getInputStream();
                            byte [] m= new byte[1000];
                            inputStream.read(m);
                            Log.i("TAG",m.toString());
                        }catch (MalformedURLException ex){
                            Log.i("TAG","@_____@",ex);
                        }catch (IOException ex){
                            Log.i("TAG","@_____@",ex);
                        }
                    }
                }).start();
            }
        });

        mLocationPickerButton = view.findViewById(R.id.location_choose_text_view);
        mLocationPickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = AddressPickerActivity.newIntent(getContext());
                startActivity(intent);
            }
        });
        return view;
    }

    public class orderListViewHolder extends RecyclerView.ViewHolder{

        private TextView mNameTextView;
        private TextView mNumberTextView;
        private TextView mPriceTextView;

        public orderListViewHolder(LayoutInflater inflater,ViewGroup container) {
            super(inflater.inflate(R.layout.list_order_item,container,false));
            mNameTextView = itemView.findViewById(R.id.order_list_name_text_view);
            mNumberTextView = itemView.findViewById(R.id.order_list_number_text_view);
            mPriceTextView = itemView.findViewById(R.id.order_list_price_text_view);
        }

        public void bindText(ItemCategories.Item item){
            mNumberTextView.setText("x"+item.getNumber());
            mNameTextView.setText(item.getName());
            mPriceTextView.setText(""+item.getPrice());
        }
    }

    public class orderListViewAdaptor extends RecyclerView.Adapter<orderListViewHolder>{

        private ItemInOrderList mItemInOrderList;

        public orderListViewAdaptor(){
            mItemInOrderList= ItemInOrderList.getItemInOrderList();
        }

        @NonNull
        @Override
        public orderListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new orderListViewHolder(inflater,parent);
        }

        @Override
        public void onBindViewHolder(@NonNull orderListViewHolder holder, int position) {
            holder.bindText(mItemInOrderList.getItemFromInteger(position));
        }

        @Override
        public int getItemCount() {
            if(mItemInOrderList!=null){
                return mItemInOrderList.getSize();
            }
           return 0;
        }
    }


}
