package com.senthil.prabhu.android.shoppingcart.api;

import com.senthil.prabhu.android.shoppingcart.model.ProductModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetProductList {

    @GET("/products")
    Call<List<ProductModel>> getProductsList();

}
