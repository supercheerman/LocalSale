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
import android.widget.Toast;

import com.example.localsale.API.SendOrderAPI;
import com.example.localsale.R;
import com.example.localsale.data.JsonHelper.JsonOrderHelper;
import com.example.localsale.data.LocalDatabase.Database;
import com.example.localsale.data.UserInfo.LoginUser;
import com.example.localsale.ui.TimePlesk.DeliverTimePickerActivity;
import com.example.localsale.ui.TimePlesk.TimeList;
import com.example.localsale.ui.addressPlesk.AddressList;
import com.example.localsale.ui.addressPlesk.AddressPickerActivity;
import com.example.localsale.ui.shoppingPlesk.ItemCategories;

import org.json.JSONObject;

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
    private TextView mAddressTextView;
    private TextView mTimeTextView;


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

                if(AddressList.getAddressList().getCurrentAddressInfo()==null&&TimeList.getInstance().getCurrentTimeItemToString()=="请选择配送时间"){
                    Toast.makeText(getContext(), "需要选择配送地点和时间", Toast.LENGTH_LONG).show();
                    return;
                }
                if(AddressList.getAddressList().getCurrentAddressInfo()==null){
                    Toast.makeText(getContext(), "需要选择配送地点", Toast.LENGTH_LONG).show();
                    return;
                }
                if(TimeList.getInstance().getCurrentTimeItemToString()=="请选择配送时间"){
                    Toast.makeText(getContext(), "需要选择配送时间", Toast.LENGTH_LONG).show();
                    return;
                }
                addItemInOrderListIntoDB();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject jsonArray = JsonOrderHelper.ItemInOrderList2JsonObject(ItemInOrderList.getItemInOrderList(), LoginUser.getUseInfo().getUserName());
                        Log.i("TAG",jsonArray.toString());
                        SendOrderAPI.SendOrder(jsonArray);

                    }
                }).start();
                Toast.makeText(getContext(), "下单成功", Toast.LENGTH_LONG).show();
                getActivity().finish();



            }
        });

        mLocationPickerButton = view.findViewById(R.id.location_choose_image_view);
        mLocationPickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = AddressPickerActivity.newIntent(getContext());
                startActivity(intent);
            }
        });
        mAddressTextView = view.findViewById(R.id.location_text_view);
        mTimeTextView = view.findViewById(R.id.time_text_view);
        bindTextBeforeResume();
        mTimePickerButton = view.findViewById(R.id.time_choose_image_view);
        mTimePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = DeliverTimePickerActivity.newIntent(getContext());
                startActivity(intent);
            }
        });
        return view;
    }
    private void addItemInOrderListIntoDB(){
        ItemInOrderList.getItemInOrderList().setAddressInfo(AddressList.getAddressList().getCurrentAddressInfo());
        ItemInOrderList.getItemInOrderList().setDate();
        ItemInOrderList.getItemInOrderList().setDeliverTime(TimeList.getInstance().getCurrentTimeItemToString());
        Database.getDatabase(getContext()).addOrder(ItemInOrderList.getItemInOrderList());
    }

    public class OrderListViewHolder extends RecyclerView.ViewHolder{

        private TextView mNameTextView;
        private TextView mNumberTextView;
        private TextView mPriceTextView;

        public OrderListViewHolder(LayoutInflater inflater, ViewGroup container) {
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

    public class  orderListViewAdaptor extends RecyclerView.Adapter<OrderListViewHolder>{

        private ItemInOrderList mItemInOrderList;

        public orderListViewAdaptor(){
            mItemInOrderList= ItemInOrderList.getItemInOrderList();
        }

        @NonNull
        @Override
        public OrderListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new OrderListViewHolder(inflater,parent);
        }

        @Override
        public void onBindViewHolder(@NonNull OrderListViewHolder holder, int position) {
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
    @Override
    public  void onResume() {
        super.onResume();
        bindTextBeforeResume();
    }
    /*
    * 用于在页面刚开始加载和启动另一个页面时返回调用，用来绑定选择的地址和配送时间
    * */
    private void bindTextBeforeResume(){

        mAddressTextView.setText(AddressList.getAddressList().getCurrentAddressInfoToString());
        mTimeTextView.setText(TimeList.getInstance().getCurrentTimeItemToString());
    }


}
