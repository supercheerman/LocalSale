package com.example.localsale.ui.TimePlesk;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.localsale.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DeliverTimePickerActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private TimeList mTimeList;

    public static Intent newIntent(Context packageContext){
        Intent intent =new Intent(packageContext,DeliverTimePickerActivity.class);
        return intent;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_time_picker);
        mRecyclerView= findViewById(R.id.time_picker_recycler_view);
        mTimeList = TimeList.getTimeList();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new TimePickerHolderAdaptor());
        super.onCreate(savedInstanceState);
    }

    public class TimePickerHolder extends RecyclerView.ViewHolder{
        private TextView mDayTextView;
        private TextView mTimeTextView;
        private ImageView mImageView;

        public TimePickerHolder(LayoutInflater inflater, ViewGroup container) {
            super(inflater.inflate(R.layout.list_time_item,container,false));
            mDayTextView = itemView.findViewById(R.id.day_pick_text_view);
            mTimeTextView = itemView.findViewById(R.id.time_pick_text_view);
            mImageView = itemView.findViewById(R.id.item_chosen_image_view);
        }
        public void bindText(TimeList.TimeItem item){
            mDayTextView.setText(item.getDay());
            mTimeTextView.setText(item.getTime());

        }
    }
    public class TimePickerHolderAdaptor extends RecyclerView.Adapter<TimePickerHolder>{
        private TimeList mTimeList = TimeList.getTimeList();
        @NonNull
        @Override
        public TimePickerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getApplication());
            return new TimePickerHolder(inflater,parent);
        }

        @Override
        public void onBindViewHolder(@NonNull TimePickerHolder holder, int position) {

            holder.bindText(mTimeList.getList().getItem(position));
        }

        @Override
        public int getItemCount() {
            return mTimeList.getList().getSize();
        }
    }

}
