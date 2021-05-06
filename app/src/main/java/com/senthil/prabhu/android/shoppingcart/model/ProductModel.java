package com.senthil.prabhu.android.shoppingcart.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ProductModel implements Parcelable {

    @SerializedName("id")
    private final String id;
    @SerializedName("title")
    private final String title;
    @SerializedName("price")
    private final String price;
    @SerializedName("description")
    private final String description;
    @SerializedName("category")
    private final String category;
    @SerializedName("image")
    private final String image;
    @SerializedName("cart_quantity")
    private int cartQuantity;

    protected ProductModel(Parcel in) {
        id = in.readString();
        title = in.readString();
        price = in.readString();
        description = in.readString();
        category = in.readString();
        image = in.readString();
        cartQuantity = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(price);
        dest.writeString(description);
        dest.writeString(category);
        dest.writeString(image);
        dest.writeInt(cartQuantity);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ProductModel> CREATOR = new Creator<ProductModel>() {
        @Override
        public ProductModel createFromParcel(Parcel in) {
            return new ProductModel(in);
        }

        @Override
        public ProductModel[] newArray(int size) {
            return new ProductModel[size];
        }
    };

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public String getImage() {
        return image;
    }

    public int getCartQuantity() {
        return cartQuantity;
    }

    public void setCartQuantity(int cartQuantity) {
        this.cartQuantity = cartQuantity;
    }
}
