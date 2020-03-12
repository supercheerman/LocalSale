package com.example.localsale.ui.shoppingPlesk;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.localsale.R;
import com.example.localsale.data.CloudDatabase.DBConnector;

import java.sql.Connection;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ShoppingFragment extends Fragment  {

    private RecyclerView mSection;
    private RecyclerView mListItem;
    private ShoppingViewModel mViewModel;



    public static ShoppingFragment newInstance(Context context){
        return new ShoppingFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_shopping,container,false);
        mSection =view.findViewById(R.id.list_category);
        mListItem = view.findViewById(R.id.list_item);
        mViewModel = ViewModelProviders.of(getActivity()).get(ShoppingViewModel.class);
        mViewModel.setInterface(new ShoppingViewModel.ShoppingModelInterface() {
            @Override
            public void ChangeSectionColor(int position, String color) {

                mSection.getChildAt(mViewModel.getItemCategories().getSectionForPosition(position)).findViewById(R.id.section_text_view).setBackgroundColor(Color.parseColor(color));

            }
        });
        mSection.setLayoutManager(new LinearLayoutManager(getActivity()));
        mSection.setAdapter(new SectionAdaptor(mViewModel.getItemCategories()));
        mListItem.setLayoutManager(new LinearLayoutManager(getActivity()));
        mListItem.setAdapter(new ItemAdaptor(mViewModel.getItemCategories()));
        mListItem.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (recyclerView != null && recyclerView.getChildCount() > 0) {
                    try {
                        int currentPosition = ((RecyclerView.LayoutParams) recyclerView.getChildAt(0).getLayoutParams()).getViewAdapterPosition();//获取当前显示的第一项的index
                        mViewModel.addNumberInTop(currentPosition);
                    } catch (Exception e) {
                    }
                }

            }
        });

        mViewModel.getTopElementMutableLiveDatae().observe(this, new Observer<ShoppingViewModel.topElementState>() {
            @Override
            public void onChanged(ShoppingViewModel.topElementState topElement) {


                Log.i("TAG","@______@:observeChange"+topElement.getTop());

            }
        });


        /*
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i("TAH","@______@ run start");
                Connection connection = DBConnector.getConnection("loacalSales","");
                DBConnector.getSqlResultSet(connection,"select * from foodtable");
                Log.i("TAH","@______@ run after");

            }
        }).start();*/

        return view;
    }



    public class SectionHolder extends RecyclerView.ViewHolder{

        private TextView mTextView;
        private OnClickInterface mClickInterface;

        public SectionHolder(LayoutInflater inflater,ViewGroup container) {
           super(inflater.inflate(R.layout.list_shopping_section,container,false));
           mTextView = itemView.findViewById(R.id.section_text_view);

        }
        public void bindText(String s){
            mTextView.setText(s);
        }

        public void setBackGroundColor(){
            mTextView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }

        public void setOnClickListener(final int posit){
            mTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mListItem.scrollToPosition(mViewModel.getItemCategories().getCountTillSectionPosit(posit));

                }
            });
        }



    }

    public interface OnClickInterface{
        void OnClick();
    }
    public class SectionAdaptor extends RecyclerView.Adapter<SectionHolder>{

        private ItemCategories mItemCategories;
        private boolean create_first =true ;

        public SectionAdaptor(ItemCategories list){
            mItemCategories =list;
        }

        @NonNull
        @Override
        public SectionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new SectionHolder(inflater,parent);
        }

        @Override
        public void onBindViewHolder(@NonNull SectionHolder holder, int position) {
            if(create_first){
                holder.setBackGroundColor();
                create_first=!create_first;
            }
            holder.bindText(mItemCategories.getSectionInCategoryListString(position));
            holder.setOnClickListener(position);

        }

        @Override
        public int getItemCount() {
            return mItemCategories.getSectionCounts();
        }
    }

    public class ItemHolder extends RecyclerView.ViewHolder{
        private TextView mTextView;

        public ItemHolder(LayoutInflater inflater,ViewGroup container) {
            super(inflater.inflate(R.layout.list_shopping_item,container,false));
            mTextView = itemView.findViewById(R.id.item_text_view);
        }
        public void bindText(String s){
            mTextView.setText(s);
        }
    }
    public class ItemAdaptor extends RecyclerView.Adapter<ItemHolder>{

        private ItemCategories mItemCategories;

        public ItemAdaptor(ItemCategories list){
            mItemCategories =list;
        }

        @NonNull
        @Override
        public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new ItemHolder(inflater,parent);
        }

        @Override
        public void onBindViewHolder(@NonNull ItemHolder holder, int position) {

            holder.bindText(mItemCategories.getItemString(position));

        }

        @Override
        public int getItemCount() {

            return mItemCategories.getItemCounts();
        }

    }
 }
