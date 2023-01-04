package com.example.app_ban_hang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.app_ban_hang.R;
import com.example.app_ban_hang.model.ProductType;

import java.util.List;

public class TypeProduct_adapter extends BaseAdapter  {
    public TypeProduct_adapter(Context context, List<ProductType> array) {
        this.array = array;
        this.context = context;
    }

    List<ProductType> array;
    Context context;
    @Override
    public int getCount() {
        return array.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    public class ViewHolder{
        TextView namePro;
        ImageView imgPic;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
            convertView = layoutInflater.inflate(R.layout.item_product, null);
            viewHolder.namePro = convertView.findViewById(R.id.item_NameProduct);
            viewHolder.imgPic = convertView.findViewById(R.id.item_image);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
            /*
            viewHolder.namePro.setText(array.get(position).getNameProduct());
            Glide.with(context).load(array.get(position).getPicture()).into(viewHolder.imgPic);
*/

        }

        viewHolder.namePro.setText(array.get(position).getNameProduct());
        Glide.with(context).load(array.get(position).getPicture()).into(viewHolder.imgPic);





        return  convertView;


    }
}
