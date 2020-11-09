package com.example.cst438_meditationapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

public class FeedActivity extends AppCompatActivity {
    List<String> objectArray = Arrays.asList("OBJ-1", "OBJ-2", "OBJ-3", "OBJ-4", "OBJ-5");
    String selectedObject;
    FeedActivity.Adapter adapter;

    TextView lastItem;
    boolean firstClick = true;
    int lastObjPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        //holds objects
        RecyclerView rvAssignment = findViewById(R.id.feed_recycler_view);
        rvAssignment.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FeedActivity.Adapter();
        rvAssignment.setAdapter(adapter);
    }

    private class Adapter  extends RecyclerView.Adapter<FeedActivity.ItemHolder> {

        @Override
        public FeedActivity.ItemHolder onCreateViewHolder(ViewGroup parent, int viewType){
            LayoutInflater layoutInflater = LayoutInflater.from(FeedActivity.this);
            return new FeedActivity.ItemHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(FeedActivity.ItemHolder holder, int position){
            holder.bind(objectArray.get(position));
        }

        @Override
        public int getItemCount() { return objectArray.size(); }
    }

    private class ItemHolder extends RecyclerView.ViewHolder {

        public ItemHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.feed_item, parent, false));
        }

        public void bind(String obj) {
            final TextView item = itemView.findViewById(R.id.postTv);
            item.setText(obj);
            //make item clickable
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //save selected object
                    selectedObject = objectArray.get(getAdapterPosition());
                    Toast.makeText(FeedActivity.this, selectedObject, Toast.LENGTH_SHORT).show();

                    //change text color of selected item
                    item.setTextColor(Color.parseColor("#FFFFFF"));
                    if(!firstClick) {
                        lastItem.setTextColor(Color.parseColor("#000000"));
                    }else { firstClick = false; }
                    if(lastObjPos == getAdapterPosition()){
                        item.setTextColor(Color.parseColor("#FFFFFF"));
                    }
                    lastItem = item;
                    lastObjPos = getAdapterPosition();
                }
            });
        }
    }



    // Intent factory
    public static Intent getIntent(Context context, String val){
        Intent intent = new Intent(context, FeedActivity.class);
        intent.putExtra("EXTRA", val);
        return intent;
    }
}