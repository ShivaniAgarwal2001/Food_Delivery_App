package com.example.fooddeliveryapp.models;

import android.os.Parcel;
import android.os.Parcelable;

public class MenuModel implements Parcelable {
    private String name, url;
    private float price;
    private int totalInCart;  //for items count in total  //todo --fixed from Integer to int

//    public MenuModel(String name, String url, float price, Integer totalInCart) {
//        this.name = name;
//        this.url = url;
//        this.price = price;
//        this.totalInCart = totalInCart;
//    }

    public static final Creator<MenuModel> CREATOR = new Creator<MenuModel>() {
        @Override
        public MenuModel createFromParcel(Parcel in) {
            return new MenuModel(in);
        }

        @Override
        public MenuModel[] newArray(int size) {
            return new MenuModel[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getTotalInCart() {
        return totalInCart;
    }

    public void setTotalInCart(int totalInCart) {
        this.totalInCart = totalInCart;
    }

    protected MenuModel(Parcel in){
        name = in.readString();
        url = in.readString();
        price = in.readFloat();
        totalInCart = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(url);
        parcel.writeFloat(price);
        parcel.writeInt(totalInCart);
//        if (totalInCart == null) {
//            parcel.writeByte((byte) 0);
//        } else {
//            parcel.writeByte((byte) 1);
//        }
    }
}
