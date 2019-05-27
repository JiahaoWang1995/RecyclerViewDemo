package com.example.listview_demo;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> {
    private List<String> titleSet;
    private List<String> infoSet;
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView itemTitle;
        public TextView itemInfo;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemTitle = (TextView) itemView.findViewById(R.id.list_item);
            itemInfo = (TextView) itemView.findViewById(R.id.list_item_info);
        }
    }

    public MyRecyclerViewAdapter(List<String> myTitleSet, List<String> myInfoSet) {
        titleSet = myTitleSet;
        infoSet = myInfoSet;
    }

    @NonNull
    @Override
    public MyRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.my_adapt_view, viewGroup, false);
        MyViewHolder myViewHolder = new MyViewHolder(v);
        return myViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.itemTitle.setText(titleSet.get(i));
        myViewHolder.itemInfo.setText(infoSet.get(i));
    }

    @Override
    public int getItemCount() {
        return titleSet.size();
    }
}
