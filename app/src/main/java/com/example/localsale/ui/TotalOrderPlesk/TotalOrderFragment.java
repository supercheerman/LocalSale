package com.example.localsale.ui.TotalOrderPlesk;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.localsale.R;
import com.example.localsale.data.CloudDatabase.DBCHelper;
import com.example.localsale.data.LocalDatabase.Database;
import com.example.localsale.ui.orderPlesk.ItemInOrderList;
import com.example.localsale.ui.orderPlesk.OrderFragment;
import com.example.localsale.ui.shoppingPlesk.ItemCategories;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.example.localsale.data.CloudDatabase.DBCHelper.getDBCHelper;

public class TotalOrderFragment extends Fragment {

    private RecyclerView mRecyclerView;
    public static TotalOrderFragment newInstance(Context context){
        return new TotalOrderFragment();
    }
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_total_order,container,false);

        mRecyclerView = view.findViewById(R.id.total_order_recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(new TotalOrderAdaptor());
        Database database = Database.getDatabase(getContext());
        database.getOrdersInfoFromTables();//将订单信息从数据库中读取到静态orderList对象中
        //Log.i("TAG OrderInfo",OrderList.getOrderList().getSize()+"");
        //Log.i("TAG OrderInfo",OrderList.getOrderList().getOrder(0).toString());
        return view;
    }

    public class TotalOrderHolder extends RecyclerView.ViewHolder{

        private TextView order_id_text_view;
        private TextView item1;
        private TextView item1_number;
        private TextView item2;
        private TextView item2_number;
        private TextView item3;
        private TextView item3_number;
        private TextView item4;
        private TextView item4_number;

        public TotalOrderHolder(LayoutInflater inflater, ViewGroup container){
            super(inflater.inflate(R.layout.list_total_order_item,container,false));
            item1 = itemView.findViewById(R.id.item1);
            item1_number = itemView.findViewById(R.id.item1_number);
            item2 = itemView.findViewById(R.id.item2);
            item2_number = itemView.findViewById(R.id.item2_number);
            item3 = itemView.findViewById(R.id.item3);
            item3_number = itemView.findViewById(R.id.item3_number);
            item4 = itemView.findViewById(R.id.item4);
            item4_number = itemView.findViewById(R.id.item4_number);
            order_id_text_view =  itemView.findViewById(R.id.order_id_text_view);

        }
        public void bindText(ItemInOrderList itemInOrderList){

            int number = itemInOrderList.getSize();
            order_id_text_view.setText("订单号："+itemInOrderList.getOrderID());
            if(number>0){
                ItemCategories.Item item = itemInOrderList.getItemFromInteger(0);
                item1.setText(item.getName());
                item1_number.setText("x"+item.getNumber());
            }
            if(number>1){
                ItemCategories.Item item = itemInOrderList.getItemFromInteger(1);
                item2.setText(item.getName());
                item2_number.setText("x"+item.getNumber());
            }else{
                item2.setText("");
                item2_number.setText("");
            }
            if(number>2){
                ItemCategories.Item item = itemInOrderList.getItemFromInteger(2);
                item3.setText(item.getName());
                item3_number.setText("x"+item.getNumber());
            }else{
                item3.setText("");
                item3_number.setText("");
            }
            if(number>3){
                item4.setText("还有其他"+(number-3)+"商品");
            }else{
                item4.setText("");
            }
            item4_number.setText("总价:"+itemInOrderList.getTotalPrice());

        }
        public void bindListener(final int index){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = OrderShowActivity.newIntent(getContext(),index);
                    startActivity(intent);
                }
            });
        }
    }

    public class TotalOrderAdaptor extends RecyclerView.Adapter<TotalOrderHolder>{

        private OrderList mOrderList;

        public TotalOrderAdaptor(){
            mOrderList = OrderList.getOrderList();
            for(int i =0;i<mOrderList.getSize();i++){

                Log.i("TAG OrderInfo",mOrderList.getOrder(i).toString());
            }
        }
        public TotalOrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new TotalOrderHolder(inflater,parent);
        }

        @Override
        public void onBindViewHolder(@NonNull TotalOrderHolder holder, int position) {
            holder.bindText(mOrderList.getOrder(position));
            holder.bindListener(position);
        }

        @Override
        public int getItemCount() {
            return mOrderList.getSize();
        }
    }


}
