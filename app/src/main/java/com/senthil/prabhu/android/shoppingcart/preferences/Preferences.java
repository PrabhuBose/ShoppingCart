package com.senthil.prabhu.android.shoppingcart.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.senthil.prabhu.android.shoppingcart.R;


public class Preferences {

    private static SharedPreferences cartDetails;
    private static final String KEY_USER_CART_LIST = "user_cart_list";


    public Preferences(Context context) {
        if (null != context) {
            String preferenceName = context.getResources().getString(R.string.app_shared_pref);
            cartDetails = context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
        }
    }


    public void setCart(String cartListGSON) {
        SharedPreferences.Editor editor = cartDetails.edit();
        editor.putString(KEY_USER_CART_LIST, cartListGSON);
        editor.apply();
    }

    public String getCart() {
        return cartDetails.getString(KEY_USER_CART_LIST, null);
    }


    public void deleteCart() {
        SharedPreferences.Editor editor = cartDetails.edit();
        editor.clear();
        editor.apply();
    }


}
