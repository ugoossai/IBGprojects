package com.example.ugo.androidfirebasedbrealtimenew;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.TextView;

public class MyRecyclerViewHolder extends ViewHolder implements View.OnClickListener {

    TextView txt_title,txt_comment;

    ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public MyRecyclerViewHolder(@NonNull View itemView) {
        super(itemView);

        txt_comment = (TextView)itemView.findViewById(txt_comment);
        txt_title   = (TextView)itemView.findViewById(txt_title);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(view,getAdapterPosition());
    }
}
