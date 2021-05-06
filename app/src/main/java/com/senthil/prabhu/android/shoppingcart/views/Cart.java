package com.senthil.prabhu.android.shoppingcart.views;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.senthil.prabhu.android.shoppingcart.R;
import com.senthil.prabhu.android.shoppingcart.adapter.CartListAdapter;
import com.senthil.prabhu.android.shoppingcart.model.ProductModel;
import com.senthil.prabhu.android.shoppingcart.utils.Utils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("NonConstantResourceId")
public class Cart extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.my_toolbar)
    Toolbar myToolbar;

    @BindView(R.id.product_list)
    RecyclerView productList;

    @BindView(R.id.item_count)
    AppCompatTextView cartCount;

    @BindView(R.id.total_amt_val)
    AppCompatTextView totalAmt;

    @BindView(R.id.net_total_val)
    AppCompatTextView netTotalAmt;

    @BindView(R.id.total_layout)
    LinearLayout totalLayout;

    @BindView(R.id.radio_group)
    RadioGroup radioGroup;

    @BindView(R.id.check_out_rel)
    RelativeLayout check_out_rel;

    @BindView(R.id.cart_sub_heading)
    AppCompatTextView cartSubHeading;

    private RadioButton radioButton;
    private ArrayList<ProductModel> cartList = new ArrayList<>();
    private CartListAdapter adapter;

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        ButterKnife.bind(this);
        setSupportActionBar(myToolbar);
        myToolbar.setNavigationIcon(R.drawable.ic_back_arrow_white);
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(mMessageReceiver, new IntentFilter(getResources().getString(R.string.cart_details)));

        cartList.addAll(Utils.getCart(getApplicationContext()));
        cartCount.setText(cartList.size() + " " + getResources().getString(R.string.items));

        if (cartList.size() > 0) {
            totalLayout.setVisibility(View.VISIBLE);
            cartCount.setVisibility(View.VISIBLE);
            cartSubHeading.setText(getResources().getString(R.string.cart_count_message));
        } else {
            cartSubHeading.setText(getResources().getString(R.string.empty_cart_message));
            totalLayout.setVisibility(View.GONE);
            cartCount.setVisibility(View.GONE);
        }

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> radioButton = findViewById(checkedId));
        check_out_rel.setOnClickListener(this);

        adapter = new CartListAdapter(getApplicationContext());
        productList.setAdapter(adapter);

        adapter.setItems(cartList);
        adapter.notifyDataSetChanged();
        double totalPrice = Utils.getTotalPrice(getApplicationContext());
        totalAmt.setText(getResources().getString(R.string.currency) + String.format("%.2f", totalPrice));
        netTotalAmt.setText(getResources().getString(R.string.currency) + String.format("%.2f", totalPrice));

    }

    private final BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @SuppressLint({"SetTextI18n", "DefaultLocale"})
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            int count = intent.getIntExtra("count", 0);
            double totalPrice = intent.getDoubleExtra("total_price", 0.0);
            cartList = intent.getParcelableArrayListExtra("cart_list");
            cartCount.setText(count + " " + getResources().getString(R.string.items));
            if (count == 0) {
                totalLayout.setVisibility(View.GONE);
                cartCount.setVisibility(View.GONE);
            }

            totalAmt.setText(getResources().getString(R.string.currency) + String.format("%.2f", totalPrice));
            netTotalAmt.setText(getResources().getString(R.string.currency) + String.format("%.2f", totalPrice));
        }
    };

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

    private String checkSelectedPaymentMethod() {
        if (radioButton != null) {
            if (radioButton.getText().toString().equalsIgnoreCase(
                    getResources().getString(R.string.pay_with_cash))) {
                return "COD";
            }
        } else {
            Toast.makeText(this, "Please select the payment method", Toast.LENGTH_SHORT).show();
        }
        return "";
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.check_out_rel:
                if (checkSelectedPaymentMethod().equalsIgnoreCase("COD")) {
                    startActivityForResult(new Intent(this, ConfirmOrder.class)
                            .putParcelableArrayListExtra("cart_list", cartList), 101);
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == RESULT_OK) {
            finish();
        }

    }
}