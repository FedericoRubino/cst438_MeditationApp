package com.example.cst438_meditationapp;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

public class RecyclerViewActivity extends AppCompatActivity {
    List<String> objectArray = Arrays.asList("OBJ-1", "OBJ-2", "OBJ-3", "OBJ-4", "OBJ-5",
                                                "OBJ-6", "OBJ-7", "OBJ-8", "OBJ-9", "OBJ-10");;
    String selectedObject;
    Adapter adapter;

    TextView lastItem;
    boolean firstClick = true;
    int lastObjPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);

        //holds objects
        RecyclerView rvAssignment = findViewById(R.id.example_recycler_view);
        rvAssignment.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter();
        rvAssignment.setAdapter(adapter);
    }

    private class Adapter  extends RecyclerView.Adapter<ItemHolder> {

        @Override
        public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType){
            LayoutInflater layoutInflater = LayoutInflater.from(RecyclerViewActivity.this);
            return new  ItemHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(ItemHolder holder, int position){
            holder.bind(objectArray.get(position));
        }

        @Override
        public int getItemCount() { return objectArray.size(); }
    }

    private class ItemHolder extends RecyclerView.ViewHolder {

        public ItemHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item, parent, false));
        }

        public void bind(String obj) {
            final TextView item = itemView.findViewById(R.id.item_id);
            item.setText(obj);
            //make item clickable
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //save selected object
                    selectedObject = objectArray.get(getAdapterPosition());
                    Toast.makeText(RecyclerViewActivity.this, selectedObject, Toast.LENGTH_SHORT).show();

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

}
