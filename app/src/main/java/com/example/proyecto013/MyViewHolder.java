package com.example.proyecto013;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {
    ImageView IconView;
    TextView nameView, ownerView;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        IconView = itemView.findViewById(R.id.myIcon);
       nameView = itemView.findViewById(R.id.tvName);
       ownerView = itemView.findViewById(R.id.tvOwner);
    }
}
