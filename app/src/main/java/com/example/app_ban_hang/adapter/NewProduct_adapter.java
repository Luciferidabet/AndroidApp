package com.example.app_ban_hang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.app_ban_hang.R;
import com.example.app_ban_hang.model.NewProduct;

import java.util.List;

public class NewProduct_adapter extends RecyclerView.Adapter<NewProduct_adapter.MyViewholder> {
    Context context;
    List<NewProduct> array;

    public NewProduct_adapter(Context context, List<NewProduct> array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public MyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View item = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_newprocduct, parent, false );

        return new MyViewholder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewholder holder, int position) {
            NewProduct newProduct = array.get(position);
            holder.txtName.setText(newProduct.getNameProduct());
            holder.txtPrice.setText(newProduct.getPrice());
            Glide.with(context).load(newProduct.getPicture()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return array.size();
    }


    public class MyViewholder extends RecyclerView.ViewHolder {
        TextView txtPrice, txtName;
        ImageView image;

        public MyViewholder(@NonNull View itemView) {
            super(itemView);
            txtPrice = itemView.findViewById(R.id.Price_Product);
            txtName = itemView.findViewById(R.id.Name_Product);
            image = itemView.findViewById(R.id.itemNewProduct);

        }

    }
}
