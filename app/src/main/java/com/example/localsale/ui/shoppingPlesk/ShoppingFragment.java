package com.example.localsale.ui.shoppingPlesk;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.localsale.R;
import com.example.localsale.data.CloudDatabase.DBCHelper;
import com.example.localsale.ui.orderPlesk.ItemInOrderList;
import com.example.localsale.data.LocalDatabase.Database;
import com.example.localsale.data.UserInfo.UserInfoList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
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
        Log.i("TAG"," "+getActivity());
        mViewModel = ViewModelProviders.of(getActivity()).get(ShoppingViewModel.class);
        Log.i("TAG"," finished");
        mViewModel.setInterface(new ShoppingViewModel.ShoppingModelInterface() {
            @Override
            public void ChangeSectionColor(int position, String color) {

                mSection.getChildAt(mViewModel.getItemCategories().getSectionForPosition(position)).findViewById(R.id.section_text_view).setBackgroundColor(Color.parseColor(color));

            }
        });
        mSection.setLayoutManager(new LinearLayoutManager(getActivity()));
        mSection.setAdapter(new SectionAdaptor(null));
        mListItem.setLayoutManager(new LinearLayoutManager(getActivity()));
        mListItem.setAdapter(new ItemAdaptor(null));
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


        new readDBAsyncTask().execute();

        return view;
    }



    public class SectionHolder extends RecyclerView.ViewHolder{

        private TextView mTextView;


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

        /*
        * 为指定位置item添加onClickListener
        * */
        public void setOnClickListener(final int posit){
            mTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    int position =mViewModel.getItemCategories().getCountTillSectionPosit(posit);
                    mViewModel.addNumberInTop(position);
                    final LinearLayoutManager linearLayoutManager =(LinearLayoutManager) mListItem.getLayoutManager();
                    final int firstVisualElement =  linearLayoutManager.findFirstCompletelyVisibleItemPosition();
                    final int lastVisualElement =  linearLayoutManager.findLastCompletelyVisibleItemPosition();

                    if(position<firstVisualElement){
                        mListItem.scrollToPosition(position);

                    }else if(position<lastVisualElement){
                        int offSet=mListItem.getChildAt(position-firstVisualElement).getTop();
                        mListItem.smoothScrollBy(0,offSet);

                    }else{
                        mListItem.scrollToPosition(position);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                int firstVisualElement2 =  linearLayoutManager.findFirstCompletelyVisibleItemPosition();
                                int lastVisualElement2 =  linearLayoutManager.findLastCompletelyVisibleItemPosition();
                                if(mListItem.getChildAt(lastVisualElement-firstVisualElement)==null){

                                }else{
                                    int offSet=mListItem.getChildAt(lastVisualElement-firstVisualElement+1).getTop();
                                    mListItem.smoothScrollBy(0,offSet);
                                }

                            }
                        },50);
                    }


                }
            });
        }



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
            if(mItemCategories==null){
                return 0;
            }
            else{
                return mItemCategories.getSectionCounts();
            }
        }
    }

    public class ItemHolder extends RecyclerView.ViewHolder{
        private TextView mNameTextView;
        private Button mAddButton;
        private Button mSubButton;
        private TextView mPriceTextView;
        private TextView mDescriptionTextView;
        private TextView mNumberTextView;

        public ItemHolder(LayoutInflater inflater,ViewGroup container) {
            super(inflater.inflate(R.layout.list_shopping_item,container,false));
            mNameTextView = itemView.findViewById(R.id.item_text_view);
            mPriceTextView =itemView.findViewById(R.id.item_text_price);
            mDescriptionTextView = itemView.findViewById(R.id.item_text_description);
            mNumberTextView = itemView.findViewById(R.id.product_number);
            mAddButton = itemView.findViewById(R.id.add_button);
            mSubButton = itemView.findViewById(R.id.sub_button);
        }
        /*
        *
        * 绑定名字，价格和描述
        * */
        public void bindText(ItemCategories.Item item){

            mNameTextView.setText(item.getName());
            mPriceTextView.setText("￥"+item.getPrice());
            mDescriptionTextView.setText(item.getDescription());
        }
        public void bindOnClickListener(final int position){

            if(ItemInOrderList.getItemInOrderList().HasItem(position)){
                /*
                 * 当页面重新加载时，有的商品已经有数量需要绑定文本，显示减号图标
                 * */
                mNumberTextView.setText(""+ItemInOrderList.getItemInOrderList().getItemNumber(position));
                mAddButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ItemInOrderList.getItemInOrderList().IncreaseItemNumber(position);
                        mNumberTextView.setText(""+ItemInOrderList.getItemInOrderList().getItemNumber(position));
                    }
                });
                mSubButton.setVisibility(View.VISIBLE);
                mSubButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(ItemInOrderList.getItemInOrderList().subNumberInItem(position)){
                            mSubButton.setAnimation(ButtonAnimation.getHiddenAnimation());
                            mSubButton.setVisibility(View.INVISIBLE);
                            mNumberTextView.setText(null);
                            mSubButton.setOnClickListener(null);
                        }else{
                            mNumberTextView.setText(""+ItemInOrderList.getItemInOrderList().getItemNumber(position));
                        }
                    }
                });
            }else{
                /*
                 * 当页面重新加载时，商品没有数量只需隐藏减号，为加号绑定动画
                 * */
                mNumberTextView.setText(null);
                mSubButton.setOnClickListener(null);
                mSubButton.setVisibility(View.INVISIBLE);
                mAddButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.i("TAG","onClick!");
                        ItemInOrderList.getItemInOrderList().addNumberInItem(position);
                        mSubButton.setAnimation(ButtonAnimation.getShowAnimation());
                        mSubButton.setVisibility(View.VISIBLE);
                        mSubButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(ItemInOrderList.getItemInOrderList().subNumberInItem(position)){
                                    mSubButton.setAnimation(ButtonAnimation.getHiddenAnimation());
                                    mSubButton.setVisibility(View.INVISIBLE);
                                    mNumberTextView.setText(null);
                                    mSubButton.setOnClickListener(null);
                                }else{
                                    mNumberTextView.setText(""+ItemInOrderList.getItemInOrderList().getItemNumber(position));
                                }
                            }
                        });
                        mNumberTextView.setText(""+ItemInOrderList.getItemInOrderList().getItemNumber(position));


                    }
                });

            }

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

            holder.bindText(mItemCategories.getItem(position));
            holder.bindOnClickListener(position);

        }

        @Override
        public int getItemCount() {

            if(mItemCategories==null){
                return 0;
            }
            else{
                return mItemCategories.getItemCounts();
            }

        }

    }
    public class readDBAsyncTask extends AsyncTask<Integer, Integer, ItemCategories>{


        /*
        * 子线程中读取数据库中的数据
        * */
        @Override
        protected ItemCategories doInBackground(Integer... voids) {
            Log.i("TAG","before sql");
            DBCHelper helper = new DBCHelper("select * from foodtable");
            return  helper.readResult();
        }

        /*
        * 该方法在子线程执行完毕后执行，在主线程中执行，用来设置数据显示
        * */
        @Override
        protected void onPostExecute(ItemCategories result) {

            //Database database  = Database.getDatabase(getActivity());
            //database.addCategories(result);
            UserInfoList.getInstance().setList(Database.getDatabase(getActivity()).getUsers());
            mViewModel.setItemCategories(result);
            ItemCategories.setItemCategories(result);
            mSection.setAdapter(new SectionAdaptor(result));
            mListItem.setAdapter(new ItemAdaptor(result));
            Log.i("TAG",""+ result.getItemCounts()+" "+result.getSectionCounts());
        }
    }


 }
