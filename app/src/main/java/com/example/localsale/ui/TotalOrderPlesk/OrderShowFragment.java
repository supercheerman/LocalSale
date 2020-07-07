package com.example.localsale.ui.TotalOrderPlesk;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.localsale.R;
import com.example.localsale.ui.orderPlesk.ItemInOrderList;
import com.example.localsale.ui.shoppingPlesk.ItemCategories;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class OrderShowFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private TextView mTotalFreeTextView;
    private TextView mWrapOrderFreeTextView;
    private TextView mDeliverOrderFreeTextView;
    private TextView mDiscountFreeTextView;
    private TextView mOrderShowOrderIdTextView;
    private TextView mOrderShowOrderTimeTextView;
    private ItemInOrderList mItemInOrderList;

    private static final String INDEX="LOCALSALE_ORDER_SHOW_FRAGMENT_INDEX";
    public static OrderShowFragment newInstance(Context context,int index){
        Bundle bundle = new Bundle();
        bundle.putInt(INDEX,index);
        OrderShowFragment fragment = new OrderShowFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_order_show,container,false);

        mItemInOrderList = OrderList.getOrderList().getOrder(getArguments().getInt(INDEX));
        mRecyclerView = view.findViewById(R.id.order_show_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(new OrderListViewAdaptor(mItemInOrderList));
        mTotalFreeTextView = view.findViewById(R.id.total_price_each_order_text_view);
        mWrapOrderFreeTextView = view.findViewById(R.id.wrap_order_free_text_view);
        mDeliverOrderFreeTextView = view.findViewById(R.id.deliver_order_free_text_view);
        mDiscountFreeTextView = view.findViewById(R.id.discount_free_text_view);
        mOrderShowOrderIdTextView = view.findViewById(R.id.order_show_order_id_text_view);
        mOrderShowOrderTimeTextView = view.findViewById(R.id.order_show_order_time_text_view);
        blindText();
        return view;
    }
    private void blindText(){
        mTotalFreeTextView.setText("合计："+mItemInOrderList.getTotalPrice());
        mDeliverOrderFreeTextView.setText("1.0");
        mWrapOrderFreeTextView.setText("0.5");
        mDiscountFreeTextView.setText("0.0");
        mOrderShowOrderIdTextView.setText(mItemInOrderList.getOrderID());
        mOrderShowOrderTimeTextView.setText(mItemInOrderList.getDate());
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

    public class OrderListViewAdaptor extends RecyclerView.Adapter<OrderListViewHolder> {

        private ItemInOrderList mItemInOrderList;

        public OrderListViewAdaptor(ItemInOrderList itemInOrderList){
            mItemInOrderList= itemInOrderList;
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
}
