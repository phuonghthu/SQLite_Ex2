package com.K214111950.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.K214111950.model.Product;
import com.K214111950.sqlite_ex2.MainActivity;
import com.K214111950.sqlite_ex2.R;

import java.util.List;

public class ProductAdapter extends BaseAdapter {

    MainActivity activity;
    int item_layout;
    List<Product> products;

    // Constructor

    public ProductAdapter(MainActivity activity, int item_layout, List<Product> products) {
        this.activity = activity;
        this.item_layout = item_layout;
        this.products = products;
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Object getItem(int position) {
        return products.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(item_layout, null);
            holder.txtInfo = view.findViewById(R.id.txtInfo);
            holder.imvEdit = view.findViewById(R.id.imvEdit);
            holder.imvDelete = view.findViewById(R.id.imvDelete);

            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }

        // binding data
        Product p = products.get(position);
        holder.txtInfo.setText(p.getProductName() + " - " + p.getProductPrice());
        holder.imvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // gọi phương thức chỉnh sửa
                activity.openDialogEdit(p);
            }
        });

        holder.imvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // gọi phương thức xóa
                activity.openDialogDelete(p);
            }
        });

        return view;
    }

    public static class ViewHolder{
        TextView txtInfo;
        ImageView imvEdit, imvDelete;
    }
}
