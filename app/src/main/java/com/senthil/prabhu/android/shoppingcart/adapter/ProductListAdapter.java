package com.senthil.prabhu.android.shoppingcart.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.senthil.prabhu.android.shoppingcart.R;
import com.senthil.prabhu.android.shoppingcart.model.ProductModel;
import com.senthil.prabhu.android.shoppingcart.utils.Utils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder> {

    private ArrayList<ProductModel> dataList;
    private Context context;


    public ProductListAdapter(ArrayList<ProductModel> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.produt_list_item, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(context).load(dataList.get(position).getImage()).into(holder.productImage);
        holder.productTitle.setText(dataList.get(position).getTitle());
        holder.price.setText(context.getResources().getString(R.string.currency) + dataList.get(position).getPrice());

        holder.addCart.setOnClickListener(view -> {
            Utils.addToCart(context, dataList.get(position), "add");
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @SuppressLint("NonConstantResourceId")
    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img)
        AppCompatImageView productImage;

        @BindView(R.id.title)
        AppCompatTextView productTitle;

        @BindView(R.id.price)
        AppCompatTextView price;

        @BindView(R.id.add_cart)
        AppCompatImageView addCart;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
