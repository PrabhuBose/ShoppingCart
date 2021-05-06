package com.senthil.prabhu.android.shoppingcart.views;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.senthil.prabhu.android.shoppingcart.R;
import com.senthil.prabhu.android.shoppingcart.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("NonConstantResourceId")
public class OrderSuccess extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.continue_shopping)
    RelativeLayout continue_shopping;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_success);
        ButterKnife.bind(this);
        continue_shopping.setOnClickListener(this);
        Utils.deleteCart(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.continue_shopping) {
            setResult(RESULT_OK);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        finish();
        super.onBackPressed();
    }
}