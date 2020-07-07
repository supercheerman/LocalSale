package com.example.localsale.ui.TotalOrderPlesk;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.localsale.API.GetOrderAPI;
import com.example.localsale.R;
import com.example.localsale.data.LocalDatabase.Database;
import com.example.localsale.data.UserInfo.LoginUser;
import com.example.localsale.data.UserInfo.UserInfo;
import com.example.localsale.ui.orderPlesk.ItemInOrderList;
import com.example.localsale.ui.shoppingPlesk.ItemCategories;
import com.example.localsale.ui.shoppingPlesk.ShoppingFragment;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TotalOrderFragment extends Fragment {

    private RecyclerView mRecyclerView;
    public static TotalOrderFragment newInstance(Context context){
        return new TotalOrderFragment();
    }
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_total_order,container,false);

        mRecyclerView = view.findViewById(R.id.total_order_recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //Database database = Database.getDatabase(getContext());
        //database.getOrdersInfoFromTables();//将订单信息从数据库中读取到静态orderList对象中
        mRecyclerView.setAdapter(new TotalOrderAdaptor(OrderList.getOrderList()));
        new ReadDBAsyncTask().execute();


        return view;
    }

    public class TotalOrderHolder extends RecyclerView.ViewHolder{

        private TextView mOrderIdTextView;
        private TextView item1;
        private TextView mItem1Number;
        private TextView item2;
        private TextView mItem2Number;
        private TextView item3;
        private TextView mItem3Number;
        private TextView mOtherItem;
        private TextView mTotalPrice;

        public TotalOrderHolder(LayoutInflater inflater, ViewGroup container){
            super(inflater.inflate(R.layout.list_total_order_item,container,false));
            item1 = itemView.findViewById(R.id.item1);
            mItem1Number = itemView.findViewById(R.id.item1_number);
            item2 = itemView.findViewById(R.id.item2);
            mItem2Number = itemView.findViewById(R.id.item2_number);
            item3 = itemView.findViewById(R.id.item3);
            mItem3Number = itemView.findViewById(R.id.item3_number);
            mOtherItem = itemView.findViewById(R.id.item4);
            mTotalPrice = itemView.findViewById(R.id.item4_number);
            mOrderIdTextView =  itemView.findViewById(R.id.order_id_text_view);

        }
        public void bindText(ItemInOrderList itemInOrderList){

            int number = itemInOrderList.getSize();
            mOrderIdTextView.setText("订单号："+itemInOrderList.getOrderID());
            if(number>0){
                ItemCategories.Item item = itemInOrderList.getItemFromInteger(0);
                item1.setText(item.getName());
                mItem1Number.setText("x"+item.getNumber());
            }
            if(number>1){
                ItemCategories.Item item = itemInOrderList.getItemFromInteger(1);
                item2.setText(item.getName());
                mItem2Number.setText("x"+item.getNumber());
            }else{
                item2.setText("");
                mItem2Number.setText("");
            }
            if(number>2){
                ItemCategories.Item item = itemInOrderList.getItemFromInteger(2);
                item3.setText(item.getName());
                mItem3Number.setText("x"+item.getNumber());
            }else{
                item3.setText("");
                mItem3Number.setText("");
            }
            if(number>3){
                mOtherItem.setText("还有其他"+(number-3)+"商品");
            }else{
                mOtherItem.setText("");
            }
            mTotalPrice.setText("总价:"+itemInOrderList.getTotalPrice());

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

        public TotalOrderAdaptor(OrderList orderList){
            mOrderList = orderList;

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

    public class ReadDBAsyncTask extends AsyncTask<Integer, Integer, OrderList> {



        /*
         * 子线程中读取数据库中的数据
         * */
        @Override
        protected OrderList doInBackground(Integer... voids) {
            Log.i("TAG","before sql");
            UserInfo user = LoginUser.getUseInfo();
            JSONObject object = new JSONObject();
            try{
                object.put("UserName",user.getUserName());
            }catch (JSONException ex){
                Log.e("TAG","@_____@",ex);
            }
            return GetOrderAPI.getOrderFromBD(object);
        }

        /*
         * 该方法在子线程执行完毕后执行，在主线程中执行，用来设置数据显示
         * */
        @Override
        protected void onPostExecute(OrderList orderList) {

            mRecyclerView.setAdapter(new TotalOrderAdaptor( OrderList.getOrderList()));
        }
    }


}
