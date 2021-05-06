package com.senthil.prabhu.android.shoppingcart.views;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.senthil.prabhu.android.shoppingcart.R;
import com.senthil.prabhu.android.shoppingcart.adapter.ConfirmOrderListAdapter;
import com.senthil.prabhu.android.shoppingcart.model.ProductModel;
import com.senthil.prabhu.android.shoppingcart.utils.Utils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("NonConstantResourceId")
public class ConfirmOrder extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.my_toolbar)
    Toolbar myToolbar;

    @BindView(R.id.net_total_value)
    AppCompatTextView orderTotal;

    @BindView(R.id.product_list)
    RecyclerView productList;

    @BindView(R.id.confirm_button)
    RelativeLayout confirm;


    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);
        ButterKnife.bind(this);
        setSupportActionBar(myToolbar);
        myToolbar.setNavigationIcon(R.drawable.ic_back_arrow_white);

        ArrayList<ProductModel> cartList = getIntent().getParcelableArrayListExtra("cart_list");
        confirm.setOnClickListener(this);
        orderTotal.setText(getResources().getString(R.string.currency)
                + String.format("%.2f", Utils.getTotalPrice(this)));

        ConfirmOrderListAdapter adapter = new ConfirmOrderListAdapter(cartList, getApplicationContext());
        productList.setAdapter(adapter);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.confirm_button:
                startActivityForResult(new Intent(this,OrderSuccess.class),101);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 101 && resultCode == RESULT_OK){
            setResult(RESULT_OK);
            finish();
        }
    }
}