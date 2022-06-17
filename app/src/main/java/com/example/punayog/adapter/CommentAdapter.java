package com.example.punayog.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.punayog.R;
import com.example.punayog.model.Comment;

import java.util.ArrayList;


public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.commentViewHolder> {
    private Context context;
    private ArrayList<Comment> comments;

    public CommentAdapter(Context context, ArrayList<Comment> comments) {
        this.context = context;
        this.comments = comments;
    }

    @NonNull
    @Override
    public CommentAdapter.commentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(context).inflate(R.layout.row_comment, parent, false);
        return new commentViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.commentViewHolder holder, int position) {
        holder.name.setText(comments.get(position).getUid());
        holder.content.setText(comments.get(position).getContent());
        holder.productId.setText(comments.get(position).getProductId());

    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public class commentViewHolder extends RecyclerView.ViewHolder {
        TextView name, content,productId;

        public commentViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.comment_userid);
            content = itemView.findViewById(R.id.comment_content);
            productId = itemView.findViewById(R.id.comment_productId);
        }
    }

}
