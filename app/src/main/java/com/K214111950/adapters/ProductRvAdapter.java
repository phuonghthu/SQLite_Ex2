package com.K214111950.adapters;

import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.K214111950.model.Product;
import com.K214111950.sqlite_ex2.R;

import java.util.List;

public class ProductRvAdapter extends RecyclerView.Adapter<ProductRvAdapter.ViewHolder> {

    Context context;
    List<Product> products;

    public ProductRvAdapter(Context context, List<Product> products) {
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_recyclerview, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtInfo.setText(products.get(position).getProductName() + " - " +
                products.get(position).getProductPrice() + "VNĐ");

    }

    @Override
    public int getItemCount() { return products.size(); }

    // class dùng chung
    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtInfo;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtInfo = itemView.findViewById(R.id.txtInfo);

        }
    }
}
