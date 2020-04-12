package com.example.localsale.ui.TotalOrderPlesk;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.localsale.R;
import com.example.localsale.ui.orderPlesk.OrderFragment;

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
        mRecyclerView.setAdapter(new TotalOrderAdaptor());
        return view;
    }

    public class TotalOrderHolder extends RecyclerView.ViewHolder{

        public TotalOrderHolder(LayoutInflater inflater, ViewGroup container){
            super(inflater.inflate(R.layout.list_total_order_item,container,false));

        }
        public void bindText(){

        }
    }

    public class TotalOrderAdaptor extends RecyclerView.Adapter<TotalOrderHolder>{

        private OrderList mOrderList;

        public TotalOrderAdaptor(){
            //mOrderList = OrderList.getOrderList();
        }
        public TotalOrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(@NonNull TotalOrderHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }
    }


}
