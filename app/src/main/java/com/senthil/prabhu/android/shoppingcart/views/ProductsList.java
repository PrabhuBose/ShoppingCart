package com.senthil.prabhu.android.shoppingcart.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.senthil.prabhu.android.shoppingcart.R;
import com.senthil.prabhu.android.shoppingcart.adapter.ProductListAdapter;
import com.senthil.prabhu.android.shoppingcart.api.GetProductList;
import com.senthil.prabhu.android.shoppingcart.api.RetrofitClientInstance;
import com.senthil.prabhu.android.shoppingcart.model.ProductModel;
import com.senthil.prabhu.android.shoppingcart.utils.SpacesItemDecoration;
import com.senthil.prabhu.android.shoppingcart.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressLint("NonConstantResourceId")
public class ProductsList extends AppCompatActivity {


    @BindView(R.id.my_toolbar)
    Toolbar myToolbar;

    @BindView(R.id.progress_circular)
    ProgressBar progressBar;

    @BindView(R.id.product_list)
    RecyclerView productList;

    @BindView(R.id.cart)
    AppCompatImageView cart;


    @BindView(R.id.cart_count)
    AppCompatTextView cartCount;

    private GetProductList service;
    private final ArrayList<ProductModel> productModelArrayList = new ArrayList<>();
    private ProductListAdapter productListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(myToolbar);

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter(getResources().getString(R.string.cart_details)));

        cart.setOnClickListener(view -> {
            startActivity(new Intent(this, Cart.class));
            Utils.getCart(getApplicationContext());
        });

        if (Utils.getCart(getApplicationContext()).size() > 0) {
            cartCount.setText(String.valueOf(Utils.getCart(getApplicationContext()).size()));
            cartCount.setVisibility(View.VISIBLE);
        } else {
            cartCount.setVisibility(View.GONE);
        }

        GridLayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        service = RetrofitClientInstance.getRetrofitInstance().create(GetProductList.class);


        productListAdapter = new ProductListAdapter(productModelArrayList, this);
        productList.setAdapter(productListAdapter);
        productList.setLayoutManager(mLayoutManager);

        getProductsList();
    }

    private final BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            int count = intent.getIntExtra("count", 0);

            if (count > 0) {
                cartCount.setText(String.valueOf(count));
                cartCount.setVisibility(View.VISIBLE);
            } else {
                cartCount.setVisibility(View.GONE);
            }
        }
    };


    private void getProductsList() {
        progressBar.setVisibility(View.VISIBLE);
        Call<List<ProductModel>> call = service.getProductsList();
        call.enqueue(new Callback<List<ProductModel>>() {
            @Override
            public void onResponse(Call<List<ProductModel>> call, Response<List<ProductModel>> response) {
                progressBar.setVisibility(View.GONE);
                productModelArrayList.addAll(response.body());
                productListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<ProductModel>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(ProductsList.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}