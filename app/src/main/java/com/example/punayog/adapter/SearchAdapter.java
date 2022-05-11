package com.example.punayog.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.punayog.R;
import com.example.punayog.SearchDeal;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> {
    ArrayList<SearchDeal>list;
    public SearchAdapter(ArrayList<SearchDeal>list){
        this.list=list;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_holder,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.id.setText(list.get(position).getProductName());
        holder.desc.setText(list.get(position).getLongDesc());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView id,desc;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            id=itemView.findViewById(R.id.dealId);
            desc=itemView.findViewById(R.id.description);
        }
    }
}
