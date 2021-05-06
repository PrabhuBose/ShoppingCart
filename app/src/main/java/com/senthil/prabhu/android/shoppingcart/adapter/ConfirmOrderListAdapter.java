package com.senthil.prabhu.android.shoppingcart.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
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

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ConfirmOrderListAdapter extends RecyclerView.Adapter<ConfirmOrderListAdapter.ViewHolder> {


    private ArrayList<ProductModel> cartList;
    private Context context;

    public ConfirmOrderListAdapter(ArrayList<ProductModel> cartList, Context context) {
        this.cartList = cartList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.confirm_order_list_item, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        Glide.with(context).load(cartList.get(position).getImage()).into(holder.productImage);
        holder.titleTv.setText(cartList.get(position).getTitle());
        holder.priceTv.setText(context.getResources().getString(R.string.currency) + cartList.get(position).getPrice());
        holder.qtyTv.setText("Quantity : "+cartList.get(position).getCartQuantity());

    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.title)
        AppCompatTextView titleTv;

        @BindView(R.id.price_tv)
        AppCompatTextView priceTv;

        @BindView(R.id.img)
        AppCompatImageView productImage;

        @BindView(R.id.qty)
        AppCompatTextView qtyTv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
