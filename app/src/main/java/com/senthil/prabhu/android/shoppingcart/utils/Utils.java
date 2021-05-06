package com.senthil.prabhu.android.shoppingcart.utils;

import android.content.Context;
import android.content.Intent;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.senthil.prabhu.android.shoppingcart.R;
import com.senthil.prabhu.android.shoppingcart.model.ProductModel;
import com.senthil.prabhu.android.shoppingcart.preferences.Preferences;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Utils {

    public static void addToCart(Context context, ProductModel productModel, String action) {
        ArrayList<ProductModel> cartList = new ArrayList<>();
        boolean qtnAdded = false;
        Preferences preferences = new Preferences(context);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<ProductModel>>() {
        }.getType();

        if (preferences.getCart() != null) {
            cartList = gson.fromJson(preferences.getCart(), type);
            for (int i = 0; i < cartList.size(); i++) {
                if (cartList.get(i).getId().equalsIgnoreCase(productModel.getId())) {
                    if (action.equalsIgnoreCase("add"))
                        cartList.get(i).setCartQuantity(cartList.get(i).getCartQuantity() + 1);
                    else if (action.equalsIgnoreCase("remove"))
                        cartList.get(i).setCartQuantity(cartList.get(i).getCartQuantity() - 1);

                    qtnAdded = true;
                    break;
                }
            }
            if (!qtnAdded) {
                productModel.setCartQuantity(1);
                cartList.add(productModel);
            }
        } else {
            productModel.setCartQuantity(1);
            cartList.add(productModel);
        }

        String json = gson.toJson(cartList);
        preferences.setCart(json);

        if (cartList.size() > 0) {
            sendCartCount(context, cartList.size(), getTotalPrice(context), cartList);
        }
    }

    public static ArrayList<ProductModel> getCart(Context context) {
        Gson gson = new Gson();
        ArrayList<ProductModel> cartList = new ArrayList<>();
        Preferences preferences = new Preferences(context);
        Type type = new TypeToken<ArrayList<ProductModel>>() {
        }.getType();
        String json = preferences.getCart();

        if (json != null) {
            cartList.addAll(gson.fromJson(json, type));
        }
        return cartList;
    }

    public static double getTotalPrice(Context context) {
        double totalPrice = 0;
        Gson gson = new Gson();
        Preferences preferences = new Preferences(context);
        Type type = new TypeToken<ArrayList<ProductModel>>() {
        }.getType();
        String json = preferences.getCart();

        if (json != null) {
            ArrayList<ProductModel> cartList = new ArrayList<>(gson.fromJson(json, type));
            for (ProductModel product : cartList) {
                double productPrice = product.getCartQuantity() * Double.parseDouble(product.getPrice());
                totalPrice = totalPrice + productPrice;
            }
        }
        return totalPrice;
    }

    public static ArrayList<ProductModel> deleteSingleProductFromCart(Context context, ProductModel productModel) {
        Gson gson = new Gson();
        ArrayList<ProductModel> cartList;
        Preferences preferences = new Preferences(context);
        Type type = new TypeToken<ArrayList<ProductModel>>() {
        }.getType();
        String json = preferences.getCart();

        if (json != null) {
            cartList = new ArrayList<>(gson.fromJson(json, type));
            for (int i = 0; i < cartList.size(); i++) {
                if (cartList.get(i).getId().equalsIgnoreCase(productModel.getId())) {
                    cartList.remove(i);
                    break;
                }
            }
            json = gson.toJson(cartList);
            preferences.setCart(json);
        }

        cartList = new ArrayList<>(gson.fromJson(json, type));

        sendCartCount(context, cartList.size(), getTotalPrice(context), cartList);

        return cartList;

    }

    public static void deleteCart(Context context){
        Preferences preferences = new Preferences(context);
        preferences.deleteCart();
        sendCartCount(context, 0, 0.0, null);
    }

    private static void sendCartCount(Context context, int cartCount, double totalPrice, ArrayList<ProductModel> cartList) {
        Intent intent = new Intent(context.getResources().getString(R.string.cart_details));
        intent.putExtra("count", cartCount);
        intent.putExtra("total_price", totalPrice);
        intent.putParcelableArrayListExtra("cart_list", cartList);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }


}
