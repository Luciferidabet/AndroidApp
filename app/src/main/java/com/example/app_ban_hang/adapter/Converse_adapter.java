package com.example.app_ban_hang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.app_ban_hang.R;
import com.example.app_ban_hang.model.NewProduct;

import java.text.DecimalFormat;
import java.util.List;

public class Converse_adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<NewProduct> array;
    private static final int VIEW_TYPE_DATA = 0;
    private static final int VIEW_TYPE_LOADING = 1;

    public Converse_adapter(Context context, List<NewProduct> array) {
        this.context = context;
        this.array = array;
    }





    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_DATA) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product, parent, false);
            return new MyViewHolder(view);
        }
        else
        {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.loadmore, parent, false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof MyViewHolder) {
            MyViewHolder myViewHolder = (MyViewHolder) holder;

            NewProduct newProduct = array.get(position);
            myViewHolder.NameProduct.setText(newProduct.getNameProduct());
            DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
            myViewHolder.Price.setText("Gia: "+decimalFormat.format(Double.parseDouble(newProduct.getPrice()))+ "Dong");
            myViewHolder.mota.setText(newProduct.getMota());
            Glide.with(context).load(newProduct.getPicture()).into(myViewHolder.Picture);
        }else{
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);

        }
    }

    @Override
    public int getItemViewType(int position) {
        return array.get(position) == null ? VIEW_TYPE_LOADING:VIEW_TYPE_DATA;
    }

    @Override
    public int getItemCount() {
        return array.size();
    }


    public class LoadingViewHolder extends RecyclerView.ViewHolder
    {
        ProgressBar progressBar;

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressbar);
        }
    }



    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView NameProduct, Price, mota;
        ImageView Picture;

        public MyViewHolder(@NonNull View itemView) {

            super(itemView);
            NameProduct = itemView.findViewById(R.id.tensp);
            Price = itemView.findViewById(R.id.gia);
            mota = itemView.findViewById(R.id.mota);
            Picture = itemView.findViewById(R.id.hinhanh);

        }
    }
}
